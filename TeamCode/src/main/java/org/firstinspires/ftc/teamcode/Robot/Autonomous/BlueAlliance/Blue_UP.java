package org.firstinspires.ftc.teamcode.Robot.Autonomous.BlueAlliance;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.Robot.Autonomous.Traiectorii_Raw.Traiectorii;

@Disabled
@Config
@Configurable
@Autonomous(name="\uD83D\uDD35 Up111111")
public class Blue_UP extends LinearOpMode {

    Thread thread_sortare;

    public static Pose pose = new Pose(0,0,0);
    private Follower follower;
    Traiectorii trajectori;


    IntakeCUHusky intake;
    Sortare sortare;

    @Override
    public void runOpMode() throws InterruptedException {

        Init();

        waitForStart();

        if(opModeIsActive())
        {
            //Up9
            trajectori.pathUp9();
            runPath(trajectori.LeftArtifacts9);

        }

    }

    private void Init() {

        thread_sortare = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });


        follower = Constants.createFollower(hardwareMap);

        trajectori = new Traiectorii(follower);

        intake = new IntakeCUHusky(hardwareMap,telemetry,gamepad1);

        sortare = new Sortare(hardwareMap,telemetry);
    }

    private void runPath(PathChain p) {
        follower.followPath(p);
        while (opModeIsActive() && follower.isBusy()) {
            follower.update();
        }
    }

}
