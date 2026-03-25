package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Ridicare.Lift;


@TeleOp
@Config
public class CalibrareRidicare extends LinearOpMode {

    public static double power;
    Lift ridicare;
    @Override
    public void runOpMode() throws InterruptedException {

        ridicare = new Lift(hardwareMap,telemetry);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        waitForStart();
        while (opModeIsActive())
        {
            ridicare.set_Power(power);
            telemetry.addData("Power set",power);
            telemetry.addData("Actual power",ridicare.get_Power());
            telemetry.update();
        }

    }
}
