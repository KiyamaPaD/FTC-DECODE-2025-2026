package org.firstinspires.ftc.teamcode.Robot.Autonomous.RedAlliance;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.Robot.Autonomous.Traiectorii_Raw.Traiectorii;

@Disabled
@Autonomous(name="ala de sus Rosu")
public class RedUp extends LinearOpMode {

    private Follower follower;
    Traiectorii trajectori;

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

        follower = Constants.createFollower(hardwareMap);

        trajectori = new Traiectorii(follower);
    }

    private void runPath(PathChain p) {
        follower.followPath(p);
        while (opModeIsActive() && follower.isBusy()) {
            follower.update();
        }
    }

}
