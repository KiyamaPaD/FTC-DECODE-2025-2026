package org.firstinspires.ftc.teamcode.Robot.Teste;

import android.annotation.SuppressLint;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RTPAxon {
    private final AnalogInput servoEncoder;
    private final CRServo servo;
    private boolean rtp;
    private double power;
    private double maxPower;
    private Direction direction;
    private double previousAngle;
    private double totalRotation;
    private double targetRotation;

    private double kP;
    private double kI;
    private double kD;
    private double integralSum;
    private double lastError;
    private double maxIntegralSum;
    private ElapsedTime pidTimer;

    public double STARTPOS;
    public int ntry = 0;
    public int cliffs = 0;
    public double homeAngle;

    public enum Direction {
        FORWARD,
        REVERSE
    }

    public RTPAxon(CRServo servo, AnalogInput encoder) {
        rtp = true;
        this.servo = servo;
        servoEncoder = encoder;
        direction = Direction.FORWARD;
        initialize();
    }
    public RTPAxon(CRServo servo, AnalogInput encoder, Direction direction) {
        this(servo, encoder);
        this.direction = direction;
        initialize();
    }
    private void initialize() {

        try {
            Thread.sleep(50);
        } catch (InterruptedException ignored) {
        }

        do {
            STARTPOS = getCurrentAngle();
            if (Math.abs(STARTPOS) > 1) {
                previousAngle = getCurrentAngle();
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
            }
            ntry++;
        } while (Math.abs(previousAngle) < 0.2 && (ntry < 50));

        totalRotation = 0;
        homeAngle = previousAngle;

        integralSum = 0.0;
        lastError = 0.0;
        maxIntegralSum = 100.0;
        pidTimer = new ElapsedTime();
        pidTimer.reset();

        maxPower = 0.25;
        cliffs = 0;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPower(double power) {
        this.power = Math.max(-maxPower, Math.min(maxPower, power));
        servo.setPower(this.power * (direction == Direction.REVERSE ? -1 : 1));
    }

    public double getPower() {
        return power;
    }

    public void setMaxPower(double maxPower) {
        this.maxPower = maxPower;
    }

    public double getMaxPower() {
        return maxPower;
    }

    public void setRtp(boolean rtp) {
        this.rtp = rtp;
        if (rtp) {
            resetPID();
        }
    }

    public boolean getRtp() {
        return rtp;
    }

    public void setKP(double kP) {
        this.kP = kP;
    }

    public void setKI(double kI) {
        this.kI = kI;
        resetIntegral();
    }

    public void setKD(double kD) {
        this.kD = kD;
    }

    public void setPidCoeffs(double kP, double kI, double kD){
        setKP(kP);
        setKI(kI);
        setKD(kD);
    }

    public double getKP() {
        return kP;
    }

    public double getKI() {
        return kI;
    }

    public double getKD() {
        return kD;
    }

    public void setK(double k) {
        setKP(k);
    }

    public double getK() {
        return getKP();
    }

    public void setMaxIntegralSum(double maxIntegralSum) {
        this.maxIntegralSum = maxIntegralSum;
    }

    public double getMaxIntegralSum() {
        return maxIntegralSum;
    }

    public double getTotalRotation() {
        return totalRotation;
    }

    public double getTargetRotation() {
        return targetRotation;
    }

    public void changeTargetRotation(double change) {
        targetRotation += change;
        resetPID();
    }

    public void setTargetRotation(double target) {
        targetRotation = target;
        resetPID();
    }

    public double getCurrentAngle() {
        if (servoEncoder == null) return 0;
        double angle  = (servoEncoder.getVoltage() / 3.3) * 360;
        return angle;
    }

    public boolean isAtTarget() {
        return isAtTarget(1);
    }

    public boolean isAtTarget(double tolerance) {
        return Math.abs(targetRotation - totalRotation) < tolerance;
    }

    public void forceResetTotalRotation() {
        totalRotation = 0;
        previousAngle = getCurrentAngle();
        resetPID();
    }

    public void resetPID() {
        resetIntegral();
        lastError = 0;
        pidTimer.reset();
    }

    public void resetIntegral() {
        integralSum = 0;
    }

    public void update(Telemetry tele) {
        tele.addLine("######pid######");
        double currentAngle = getCurrentAngle();
        double angleDifference = currentAngle - previousAngle;
        tele.addData("dif", angleDifference);

        double delta = currentAngle - previousAngle;
        if (delta < -180) {
            delta += 360;
        }
        else if (delta > 180) {
            delta -= 360;
        }
        if (Math.abs(delta) < 0.5) delta = 0;
        totalRotation += delta;
        previousAngle = currentAngle;

        if (!rtp) return;

        double dt = pidTimer.seconds();
        pidTimer.reset();

        dt = Math.max(0.05, Math.min(dt, 1));

        double error = targetRotation - totalRotation;
        tele.addData("error", error);

        integralSum += error * dt;
        integralSum = Math.max(-maxIntegralSum, Math.min(maxIntegralSum, integralSum));

        final double INTEGRAL_DEADZONE = 2.0;
        if (Math.abs(error) < INTEGRAL_DEADZONE) integralSum = 0;

        double derivative = (error - lastError) / dt;
        lastError = error;

        double pTerm = kP * error;
        double iTerm = kI * integralSum;
        double dTerm = kD * derivative;

        double output = pTerm + iTerm + dTerm;

        if (Math.abs(output) >= maxPower) integralSum = 0;

        final double DEADZONE = 0.5;
        if (Math.abs(error) > DEADZONE) {
            double power = Math.min(maxPower, Math.abs(output)) * Math.signum(output);
            setPower(power);
        } else {
            setPower(0);
        }
        tele.addLine("updating...");
    }

    public void update() {

        double currentAngle = getCurrentAngle();
        double angleDifference = currentAngle - previousAngle;

        double delta = currentAngle - previousAngle;
        if (delta < -180) {
            delta += 360;
        }
        else if (delta > 180) {
            delta -= 360;
        }
        totalRotation += delta;
        previousAngle = currentAngle;

        if (!rtp) return;

        double dt = pidTimer.seconds();
        pidTimer.reset();

        dt = Math.max(0.05, Math.min(dt, 1));

        double error = targetRotation - totalRotation;

        integralSum += error * dt;
        integralSum = Math.max(-maxIntegralSum, Math.min(maxIntegralSum, integralSum));

        final double INTEGRAL_DEADZONE = 2.0;
        if (Math.abs(error) < INTEGRAL_DEADZONE) integralSum = 0;

        double derivative = (error - lastError) / dt;
        lastError = error;

        double pTerm = kP * error;
        double iTerm = kI * integralSum;
        double dTerm = kD * derivative;

        double output = pTerm + iTerm + dTerm;

        if (Math.abs(output) >= maxPower) integralSum = 0;

        final double DEADZONE = 0.5;
        if (Math.abs(error) > DEADZONE) {
            double power = Math.min(maxPower, Math.abs(output)) * Math.signum(output);
            setPower(power);
        } else {
            setPower(0);
        }
    }
    @SuppressLint("DefaultLocale")
    public String log() {
        return String.format(
                "Current Volts: %.3f\n" +
                        "Current Angle: %.2f\n" +
                        "Total Rotation: %.2f\n" +
                        "Target Rotation: %.2f\n" +
                        "Current Power: %.3f\n" +
                        "updatePID Values: P=%.3f I=%.3f D=%.3f\n" +
                        "updatePID Terms: Error=%.2f Integral=%.2f",
                servoEncoder.getVoltage(),
                getCurrentAngle(),
                totalRotation,
                targetRotation,
                power,
                kP, kI, kD,
                targetRotation - totalRotation,
                integralSum
        );
    }

    @Config
    @TeleOp(name = "RTP Axon Test", group = "test")
    public static class CRAxonTest extends LinearOpMode {

        public static double test_Kp=0.015, test_Ki=0.0005, test_Kd=0.025, servo_pos1, servo_pos2, ManualTarget=180, pos, AngleOffset;
        double pre_Kp, pre_Ki, pre_Kd, pre_setPoint;
        public static boolean ManualSetPoint = true;
        public static String TestingAxon;

        @Override
        public void runOpMode() throws InterruptedException {
            telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

            CRServo crservo;
            AnalogInput encoder;
            RTPAxon axon;

            if(TestingAxon != null)
            {
                crservo = hardwareMap.crservo.get(TestingAxon);
                encoder = hardwareMap.get(AnalogInput.class, TestingAxon + " encoder");
                axon = new RTPAxon(crservo, encoder);
            }
            else
            {
                crservo = hardwareMap.crservo.get("testAxon");
                encoder = hardwareMap.get(AnalogInput.class, "testAxon encoder");
                axon = new RTPAxon(crservo, encoder);
            }
            axon.setRtp(true);
            axon.forceResetTotalRotation();


            waitForStart();

            while (opModeIsActive()) {

                /*
                if (gamepad1.dpadUpWasPressed()) {
                    servo.changeTargetRotation(15);
                }
                if (gamepad1.dpadDownWasPressed()) {
                    servo.changeTargetRotation(-15);
                }
                if (gamepad1.xWasPressed()) {
                    servo.setTargetRotation(0);
                }

                if (gamepad1.triangleWasPressed()) {
                    servo.forceResetTotalRotation();
                }

                if (gamepad1.rightBumperWasPressed()) {
                    servo.changeTargetRotation(servo_pos1);
                }
                if (gamepad1.leftBumperWasPressed()) {
                    servo.changeTargetRotation(servo_pos2);
                }

                if (gamepad1.touchpadWasPressed()) {

                }*/

                if(ManualSetPoint)
                {
                    if(pre_Kp != test_Kp)
                    {
                        axon.setKP(test_Kp);
                        pre_Kp = test_Kp;
                    }
                    if(pre_Ki != test_Ki)
                    {
                        axon.setKI(test_Ki);
                        pre_Ki = test_Ki;
                    }
                    if(pre_Kd != test_Kd)
                    {
                        axon.setKD(test_Kd);
                        pre_Kd = test_Kd;
                    }
                    if(pre_setPoint!=ManualTarget + AngleOffset)
                    {
                        axon.setTargetRotation(ManualTarget + AngleOffset);
                        pre_setPoint = ManualTarget + AngleOffset;
                    }
                    axon.update(telemetry);
                }
                if(encoder == null) telemetry.addLine("no encoder");

                telemetry.addData("Starting angle", axon.STARTPOS);
                telemetry.addLine(axon.log());
                telemetry.addData("NTRY", axon.ntry);
                telemetry.addData("current angle", axon.getCurrentAngle());
                telemetry.addData("target", ManualTarget);
                telemetry.addData("totalRotation", axon.totalRotation);
                telemetry.update();
            }
        }
    }
}
