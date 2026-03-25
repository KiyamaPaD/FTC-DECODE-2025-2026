package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp (group = "tests")
public class Axon extends LinearOpMode {

    Servo servo;
    AnalogInput analog;

    public static String name = "sortare";
    public static String name2 = "analog";

    public static double pos;

    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(Servo.class, name);
        analog = hardwareMap.get(AnalogInput.class, name2);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        waitForStart();
        while (opModeIsActive())
        {
            servo.setPosition(pos);
            telemetry.addData("analog", analog.getVoltage());
            telemetry.addData("position", getServoPosition(analog.getVoltage()));
            telemetry.addData("at position", Math.abs(servo.getPosition()-getServoPosition(analog.getVoltage())) < 0.01);
            telemetry.update();
        }
    }

    public double getServoPosition(double currentVoltage) {
        double minV = 0.195;
        double maxV = 3.05;

        double position = (currentVoltage - minV) / (maxV - minV);

        if (position > 1.0) position = 1.0;
        if (position < 0.0) position = 0.0;

        return position;
    }
}
