package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;

@TeleOp
public class test extends LinearOpMode {

    public static int posST, posDR, tolerance;
    public static double speed;
    IntakeCUHusky intakeCUHusky;

    int error;
    boolean ok;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();
        while(opModeIsActive()) {

            telemetry.addData("Right",intakeCUHusky.isRight());
//            telemetry.addData("unghi", tracker.getCurrentPosition() * 360 / 384);
            telemetry.update();
        }
    }

    void initialize()
    {
        intakeCUHusky = new IntakeCUHusky(hardwareMap,telemetry,gamepad1);
        intakeCUHusky.huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addLine("init cu succes");
        telemetry.update();
    }

}
