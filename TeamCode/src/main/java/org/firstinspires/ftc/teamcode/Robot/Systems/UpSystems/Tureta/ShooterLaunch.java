package org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class ShooterLaunch {
    public DcMotorEx fly1;
    public DcMotorEx fly2;
    public Servo hood;
    public Telemetry telemetry;

    public static double target = 0;
    public static double output = 0;
    public volatile boolean isRunning = false;
    public static int LOOP_MS = 20;
    private double syncFiltered = 0.0;
    private double syncBias = 0.0;

    public void updatePIDF(double p, double i, double d, double f)
    {
        fly1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(p, i, d, f));
        fly2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(p, i, d, f));
    }

    public ShooterLaunch(HardwareMap hw, Telemetry tel, Boolean auto) {
        telemetry = new MultipleTelemetry(tel, FtcDashboard.getInstance().getTelemetry());

        fly1 = hw.get(DcMotorEx.class, "fly1");
        fly1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fly1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fly2 = hw.get(DcMotorEx.class, "fly2");
        fly2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fly2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        fly1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(50, 0.05, 0.45, 12));
        fly2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(50, 0.05, 0.45, 12));

        hood = hw.get(Servo.class, "hood");

    }

    public void setShooterVelocity(double velocity) {
        fly1.setVelocity(velocity);
    }

    public void adjustAngle(double unghi) {
        hood.setPosition(unghi);
    }


    private double physVel(double measuredVel, double commandedVel) {
        double s = Math.signum(commandedVel);
        if (s == 0) s = 1.0;
        return measuredVel * s;
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
        return -0.00168943 * x + 0.841476;
    }

    public double autoVelocity(double distance) {
        double x = distance;
        return (.0000530199 * Math.pow(x, 3))
                - (0.0328552 * Math.pow(x, 2))
                + (7.92014 * x)
                + 608.34623;
    }
}
