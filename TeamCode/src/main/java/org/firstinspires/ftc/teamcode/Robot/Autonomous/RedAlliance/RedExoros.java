package org.firstinspires.ftc.teamcode.Robot.Autonomous.RedAlliance;

import static org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare.MOTIF_MEMORAT;
import static org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare.motif;
import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.alianta;
import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.localizerConstants;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Autonomous.Traiectorii_Raw.Traiectorii;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Localizare.Localizare_In_Timp_Real;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.DcMotorTrack;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


@Configurable
@Autonomous(name="\uD83D\uDD34 Jos Cu Exoros")
public class RedExoros extends LinearOpMode {

    public static double offset;

    boolean telemtrie = true;
    boolean can_shoot = true;
    boolean battery_saver = false;
    Thread thread_sortare;

    public static Pose pose = new Pose(0, 0, 0);

    private Follower follower;

    Traiectorii trajectori;

    Localizare_In_Timp_Real localizare;
    PinpointLocalizer pinpoint;

    IntakeCUHusky intake;
    Sortare sortare;
    ShooterLaunch shooter;

    DcMotorTrack track;

    private TelemetryManager panelsTelemetry;
    private int pathState;
    private Paths paths;


    @Override
    public void runOpMode() throws InterruptedException {

        Init();

        while (!opModeIsActive() && opModeInInit()) {
            boolean found = sortare.SetMotif();
            if(found) {
                telemetry.addLine("✅ MOTIF DETECTED!");
            } else {
                telemetry.addLine("❌ Searching for AprilTag...");
            }
            telemetry.addData("Current ID", sortare.GetID());
            telemetry.addLine("Motif: " + motif[0] + " " + motif[1] + " " + motif[2]);
            telemetry.update();
        }
        waitForStart();
        MOTIF_MEMORAT = true;

        if (opModeIsActive()) {
            sortare.startTransferCommand = true;
            sortare.sorteazaOrdinea();
            thread_sortare.start();
            // preload
            while (sortare.nrBileInSort>0)
            {
                if(telemtrie )
                {
                    telemetry.addData("Cu intake",sortare.Intaking);
                    telemetry.addData("Nr bile sort",sortare.nrBileInSort);
                    telemetry.update();
                }
                idle();
            }
            if(telemtrie)
            {
                panelsTelemetry.addData("Merge",true);
                panelsTelemetry.update();
                telemetry.addData("O mers",true);
                telemetry.update();
            }
            sortare.readyToShoot = false;

            sortare.openIntake();

            // prep get balls
            follower.followPath(paths.PrepBall);
            while (follower.isBusy())
                followLoop();

            sortare.openIntake();

            // get balls
            intake.intake.setPower(1);
            follower.setMaxPower(0.23);
            follower.followPath(paths.GetBall);
            while (follower.isBusy())
                followLoop();
            sortare.sorteazaOrdinea();
            intake.intake.setPower(0);

            // back to shoot
            follower.setMaxPower(1);
            follower.followPath(paths.GoShoot);
            while (follower.isBusy())
                followLoop();
            sortare.readyToShoot = true;
            while (sortare.nrBileInSort!=0)
                idle();

            sortare.readyToShoot = false;

            sortare.openIntake();

            // balls Loading zone
            follower.followPath(paths.Prep1);
            while (follower.isBusy())
                followLoop();

            follower.followPath(paths.Prep2);
            while (follower.isBusy())
                followLoop();follower.followPath(paths.Prep3Curve);
            while (follower.isBusy())
                followLoop();

            intake.intake.setPower(1);
            follower.setMaxPower(0.23);
            follower.followPath(paths.Prep1);
            while (follower.isBusy())
                followLoop();

            follower.setMaxPower(1);
            intake.intake.setPower(0);

            // back top shoot
            follower.followPath(paths.GoShoot2);
            while (follower.isBusy())
                followLoop();


            sortare.readyToShoot = true;
            sortare.Shoot();
            while (sortare.nrBileInSort!=0)
                idle();

            sortare.readyToShoot = false;
            // prep husky
//            follower.followPath(paths.PrepHusky);
//            while (follower.isBusy())
//                followLoop();
//
//            if(intake.isRight()) // merge dreapta
//            {
//                follower.followPath(paths.HuskyRight);
//                while (follower.isBusy())
//                    followLoop();
//                intake.intake.setPower(1);
//
//            }
//            else
//            {
//                follower.followPath(paths.HuskyLeft);
//                while (follower.isBusy())
//                    followLoop();
//                intake.intake.setPower(1);
//            }
//            sortare.sorteazaOrdinea();
//            intake.intake.setPower(0);
//            sleep(5000);
        }

    }

    void followLoop() {
        follower.update();

        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update();
    }

    private void Init() {

        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        alianta = LimeLight.Goal.RED;

        thread_sortare = new Thread(new Runnable() {
            @Override
            public void run() {
                while (opModeIsActive()) {
                    pinpoint.update();

                    track.PredictTarget(LimeLight.Goal.RED, offset);
                    track.Track(LimeLight.Goal.RED);


                    sortare.Update();

                    if (battery_saver) {
                        shooter.setShooterVelocity(0);

                    } else {
                        shooter.setShooterVelocity(shooter.autoVelocity(localizare.distanceFromBasket(alianta)));
                        shooter.adjustAngle(shooter.autoAngle(localizare.distanceFromBasket(alianta)));
                    }

                    if(!MOTIF_MEMORAT) sortare.SetMotif();

                }
            }
        });


        follower = Constants.createFollower(hardwareMap);
        follower.setPose(new Pose(87.58, 9.28, Math.toRadians(90)));
        trajectori = new Traiectorii(follower);

        paths = new Paths(follower);

        pinpoint = new PinpointLocalizer(hardwareMap, localizerConstants);
        pinpoint.setStartPose(new Pose(87.58, 9.28, Math.toRadians(90)));
        localizare = new Localizare_In_Timp_Real(pinpoint);

        intake = new IntakeCUHusky(hardwareMap, telemetry, gamepad1);

        shooter = new ShooterLaunch(hardwareMap, telemetry, false);

        sortare = new Sortare(hardwareMap, telemetry);

        track = new DcMotorTrack(hardwareMap,telemetry,pinpoint);

        sortare.readyToShoot = can_shoot;

        sortare.nrBileInSort = 3;
        sortare.BileInSort = new Sortare.Slot[]{Sortare.Slot.VERDE, Sortare.Slot.MOV, Sortare.Slot.MOV};

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    public static class Paths {
        public PathChain PrepBall;
        public PathChain GetBall;
        public PathChain GoShoot;
        public PathChain PrepHusky;
        public PathChain HuskyLeft;
        public PathChain HuskyRight;
        public PathChain Prep1;
        public PathChain Prep2;
        public PathChain Prep3Curve;
        public PathChain GetBallsLZone;
        public PathChain GoShoot2;

        public Paths(Follower follower) {

            PrepBall = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(87.58, 9.28),
                                    new Pose(98, 33.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                    .build();

            GetBall = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(98, 33.000),
                                    new Pose(120, 35.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                    .build();

            GoShoot = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(130.000, 35.000),
                                    new Pose(85.862, 13.061)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                    .build();

            PrepHusky = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(85.862, 13.061),
                                    new Pose(104.4004767580453, 13.51609058402861)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                    .build();

            HuskyLeft = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(104.4004767580453, 13.51609058402861),
                                    new Pose(132.5005959475566, 16.82002383790227)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(40))
                    .build();

            HuskyRight = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(104.4004767580453, 13.51609058402861),
                                    new Pose(132, 8.75327771156138)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(40))
                    .build();

            Prep1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(85.862, 13.061),
                                    new Pose(88.610, 28.963)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                    .build();

            Prep2 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(88.610, 28.963),
                                    new Pose(120.530, 32.047)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(270))
                    .build();

            Prep3Curve = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(120.530, 32.047),
                                    new Pose(136.865, 47.742),
                                    new Pose(135.279, 21.903)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(270))
                    .build();

            GetBallsLZone = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(135.279, 21.903),
                                    new Pose(135.532, 5.899)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            GoShoot2 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(135.532, 5.899),
                                    new Pose(85.862, 13.061)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(45))
                    .build();

        }
    }
}

