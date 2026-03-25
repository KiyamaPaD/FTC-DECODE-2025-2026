package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Calibrare MAX_VELOCITY", group="Test")
public class CalibrareShooter extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotorEx fly1 = hardwareMap.get(DcMotorEx.class, "fly1");
        DcMotorEx fly2 = hardwareMap.get(DcMotorEx.class, "fly2");

        fly1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fly2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            fly1.setPower(1.0);
            fly2.setPower(-1.0);

            double v1 = Math.abs(fly1.getVelocity());
            double v2 = Math.abs(fly2.getVelocity());
            double vAvg = (v1 + v2) / 2.0;

            telemetry.addData("Viteza Fly1", v1);
            telemetry.addData("Viteza Fly2", v2);
            telemetry.addData("--- VALOAREA RECOMANDATA ---", "");
            telemetry.addData("Media vAvg", vAvg);
            telemetry.addLine("Adauga 10% la vAvg pentru MAX_VELOCITY_TICKS");
            telemetry.update();
        }
    }
}