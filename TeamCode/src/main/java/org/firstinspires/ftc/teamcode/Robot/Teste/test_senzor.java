package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.DetectareMovVerde;

@TeleOp
public class test_senzor extends LinearOpMode {

    DetectareMovVerde senzor;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        while(opModeIsActive())
        {
            telemetry.addData("vede bila", senzor.vedeBila());
            telemetry.addData("culoare", senzor.culoareBila());
            telemetry.update();
        }
    }

    void Init()
    {
        senzor = new DetectareMovVerde(hardwareMap, telemetry);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addLine("init succes");
        telemetry.update();
    }
}
