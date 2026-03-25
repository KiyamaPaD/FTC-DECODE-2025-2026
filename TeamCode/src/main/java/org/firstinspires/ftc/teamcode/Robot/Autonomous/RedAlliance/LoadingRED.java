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
@Autonomous(name="\uD83D\uDD34 Jos LOADING ZONE")
public class LoadingRED extends LinearOpMode {

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
            sortare.readyToShoot = true;
            sortare.sorteazaOrdinea();
            thread_sortare.start();
            sortare.Shoot();

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

            // prep get balls
            follower.setMaxPower(0.6);
            intake.intake.setPower(0);
            follower.followPath(paths.Path1);
            while (follower.isBusy())
                followLoop();

            // get balls
            follower.setMaxPower(0.3);
            intake.intake.setPower(1);
            follower.followPath(paths.intake1);
            while (follower.isBusy())
                followLoop();

            // back to shoot
            follower.setMaxPower(0.7);
            intake.intake.setPower(1);
            follower.followPath(paths.intake2);
            while (follower.isBusy())
                followLoop();


            follower.setMaxPower(0.5);
            intake.intake.setPower(1);
            follower.followPath(paths.intake3);
            while (follower.isBusy())
                followLoop();

            follower.setMaxPower(0.23);
            intake.intake.setPower(1);
            follower.followPath(paths.intake4);
            while (follower.isBusy())
                followLoop();

            follower.setMaxPower(1);
            intake.intake.setPower(0);
            follower.followPath(paths.shoot1);
            while (follower.isBusy())
                followLoop();
            sortare.Shoot();
            while (sortare.nrBileInSort != 0 && opModeIsActive()) idle();

              sleep(5000);
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
        public PathChain Path1;
        public PathChain intake1;
        public PathChain intake2;
        public PathChain intake3;
        public PathChain intake4;
        public PathChain shoot1;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(87.409, 8.687),
                                    new Pose(130.5, 29.478)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(-17))
                    .build();

            intake1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(130.5, 29.478),
                                    new Pose(130.5, 11.145)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(-17), Math.toRadians(-17))
                    .build();

            intake2 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(130.5, 11.145),
                                    new Pose(122.948, 19.881),
                                    new Pose(91.271, 9.385),
                                    new Pose(131.408, 8.602)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(-17), Math.toRadians(0))
                    .build();

            intake3 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(131.408, 8.602),
                                    new Pose(124.200, 9.485)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                    .build();

            intake4 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(124.200, 9.485),
                                    new Pose(134.677, 9.429)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            shoot1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(134.677, 9.429),
                                    new Pose(91.839, 10.168)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                    .build();
        }
    }
}

