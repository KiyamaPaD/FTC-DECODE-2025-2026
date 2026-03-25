package org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class ShooterLaunch2 {
    public DcMotorEx fly1;
    public DcMotorEx fly2;
    public Servo hood;
    public Telemetry telemetry;

    public PIDFController pidfController;

    public static double target = 0;
    public static double output = 0;
    public volatile boolean isRunning = false;

    public static int LOOP_MS = 20;
    public static double SYNC_TAU_SEC = 0.25;
    public static double BIAS_TAU_SEC = 2.0;
    public static double MAX_SYNC_FRAC = 0.15;
    public static double MIN_MAX_SYNC = 50.0;

    private double syncFiltered = 0.0;
    private double syncBias = 0.0;
    private VoltageSensor batteryVoltageSensor;

    private static final double S1 = +1.0;
    private static final double S2 = -1.0;

    private Thread pidThread;

    public ShooterLaunch2(HardwareMap hw, Telemetry tel, Boolean auto) {
        telemetry = new MultipleTelemetry(tel, FtcDashboard.getInstance().getTelemetry());

        fly1 = hw.get(DcMotorEx.class, "fly1");
        fly1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fly1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        fly2 = hw.get(DcMotorEx.class, "fly2");
        fly2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fly2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        hood = hw.get(Servo.class, "hood");

        pidfController = new PIDFController(0.091, 0.00029, 0.092, 0);
        pidfController.setTolerance(2);
        pidfController.reset();
        pidfController.setSetPoint(0);

        batteryVoltageSensor = hw.voltageSensor.iterator().next();

        if (auto != null && auto) StartPID();
    }

    public void setShooterVelocity(double velocity) {
        target = velocity;
        pidfController.setSetPoint(velocity);
    }

    public void adjustAngle(double unghi) {
        hood.setPosition(unghi);
    }

    private double physVel(double measuredVel, double commandedVel) {
        double s = Math.signum(commandedVel);
        if (s == 0) s = 1.0;
        return measuredVel * s;
    }

    public void update_pid(double p, double i, double d, double f) {
        pidfController.setPIDF(p, i, d, f);
        update_pid();
    }

    public static double NOMINAL_VOLTAGE = 12.0;
    public static double MAX_VELOCITY_TICKS = 1500.0;

    public void update_pid() {
        if (Math.abs(target) < 10) {
            target = 0;
            pidfController.reset();
            fly1.setPower(0);
            fly2.setPower(0);
            return;
        }

        double currentVoltage = batteryVoltageSensor.getVoltage();
        double vComp = NOMINAL_VOLTAGE / (currentVoltage > 1 ? currentVoltage : NOMINAL_VOLTAGE);

        double v1 = physVel(fly1.getVelocity(), S1 * target);
        double v2 = physVel(fly2.getVelocity(), S2 * (-target));
        double vAvg = 0.5 * (v1 + v2);

        output = pidfController.calculate(vAvg, target);

        double powerBase = output;

        double syncPower = syncFiltered / MAX_VELOCITY_TICKS;

        double p1 = (powerBase - syncPower) * vComp;
        double p2 = (powerBase + syncPower) * vComp;

        fly1.setPower(Range.clip(p1 * S1, -1.0, 1.0));
        fly2.setPower(Range.clip(p2 * S2, -1.0, 1.0));
    }

    public void StartPID() {
        isRunning = true;
        if (pidThread == null || !pidThread.isAlive()) {
            pidThread = new Thread(() -> {
                while (isRunning && !Thread.currentThread().isInterrupted()) {
                    update_pid();
                    try { Thread.sleep(LOOP_MS); }
                    catch (InterruptedException e) { return; }
                }
            });
            pidThread.start();
        }
    }

    public void StopPID() {
        isRunning = false;
        fly1.setVelocity(0);
        fly2.setVelocity(0);
        pidfController.reset();
        syncFiltered = 0;
        syncBias = 0;
    }

    public void debugTelemetry() {
        double v1 = fly1.getVelocity();
        double v2 = fly2.getVelocity();
        telemetry.addData("target", target);
        telemetry.addData("fly1 vel", v1);
        telemetry.addData("fly2 vel", v2);
        telemetry.addData("pid output", output);
        telemetry.addData("syncFiltered", syncFiltered);
        telemetry.addData("syncBias", syncBias);
    }


    public double autoAngle(double distance) {
        double x = distance;
        return (3.09606e-9 * Math.pow(x, 4))
                - (0.00000193682 * Math.pow(x, 3))
                + (0.000422114 * Math.pow(x, 2))
                - (0.034884 * x)
                + 1.25184;
    }

    public double autoVelocity(double distance) {
        double x = distance;
        return (7.92099e-7 * Math.pow(x, 4))
                - (0.000389621 * Math.pow(x, 3))
                + (0.0623077 * Math.pow(x, 2))
                - (1.22166 * x)
                + 887.07262;
    }
}
