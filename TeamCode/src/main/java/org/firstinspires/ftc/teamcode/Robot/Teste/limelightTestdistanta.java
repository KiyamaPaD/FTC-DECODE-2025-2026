package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;


public class limelightTestdistanta extends LinearOpMode {

    LimeLight llcam;

    @Override
    public void runOpMode() throws InterruptedException {
        initializare();
        waitForStart();
        while(opModeIsActive())
        {
            telemetry.addData("distanta blue", llcam.AprilTagGoalDistance(LimeLight.Goal.BLUE));
            telemetry.update();
        }
    }

    public void initializare()
    {
        llcam = new LimeLight(hardwareMap, telemetry);

        telemetry.addLine("init succes");
        telemetry.update();
    }
}
