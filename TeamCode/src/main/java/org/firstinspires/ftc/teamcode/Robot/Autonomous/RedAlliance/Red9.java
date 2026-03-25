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
@Autonomous(name="\uD83D\uDD34 🟥RED 9")
public class Red9 extends LinearOpMode {

    public static double offset = 2;
    boolean telemtrie = true;
    boolean can_shoot = true;
    boolean battery_saver = false;
    Thread thread_sortare;

    public static Pose pose = new Pose(0, 0, 0);

    private Follower follower;

    Traiectorii trajectori;

    Localizare_In_Timp_Real localizare;
    PinpointLocalizer pinpoint;

    DcMotorTrack tracker;
    IntakeCUHusky intake;
    Sortare sortare;
    ShooterLaunch shooter;


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
            sortare.sorteazaOrdinea();
            thread_sortare.start();
            // preload
            sortare.Shoot();
            while (sortare.nrBileInSort>0)
            {
                if(telemtrie)
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
            follower.followPath(paths.Path1);
            while (follower.isBusy())
                followLoop();

            // get balls
            intake.intake.setPower(1);
            follower.setMaxPower(0.23);
            follower.followPath(paths.Path2);
            while (follower.isBusy())
                followLoop();

            intake.intake.setPower(0);
            follower.setMaxPower(1);
            follower.followPath(paths.Path4);
            while (follower.isBusy())
                followLoop();
            sortare.Shoot();
            while (sortare.nrBileInSort != 0 && opModeIsActive()) idle();

            intake.intake.setPower(0);
            follower.setMaxPower(1);
            follower.followPath(paths.Path5);
            while (follower.isBusy())
                followLoop();

            intake.intake.setPower(1);
            follower.setMaxPower(0.22);
            follower.followPath(paths.Path6);
            while (follower.isBusy())
                followLoop();

            intake.intake.setPower(0);
            follower.setMaxPower(1);
            follower.followPath(paths.Path7);
            while (follower.isBusy())
                followLoop();
            sortare.Shoot();
            while (sortare.nrBileInSort != 0 && opModeIsActive()) idle();

            intake.intake.setPower(0);
            follower.setMaxPower(1);
            follower.followPath(paths.Path8);
            while (follower.isBusy())
                followLoop();

            while (sortare.nrBileInSort!=0)
                idle();
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

        MOTIF_MEMORAT = false;

        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        alianta = LimeLight.Goal.RED;

        thread_sortare = new Thread(new Runnable() {
            @Override
            public void run() {
                while (opModeIsActive()) {
                    pinpoint.update();


                    sortare.Update();

                    if (battery_saver) {
                        shooter.setShooterVelocity(0);

                    } else {
                        shooter.setShooterVelocity(shooter.autoVelocity(localizare.distanceFromBasket(alianta)));
                        shooter.adjustAngle(shooter.autoAngle(localizare.distanceFromBasket(alianta)));
                    }

                    if(!MOTIF_MEMORAT) sortare.SetMotif();
                    else
                    {
                        tracker.PredictTarget(alianta, offset);
                        tracker.Track(alianta);
                    }
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

        tracker = new DcMotorTrack(hardwareMap, telemetry, pinpoint);

        sortare.readyToShoot = can_shoot;

        sortare.nrBileInSort = 3;
        sortare.BileInSort = new Sortare.Slot[]{Sortare.Slot.VERDE, Sortare.Slot.MOV, Sortare.Slot.MOV};

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    public static class Paths {
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain Path6;
        public PathChain Path7;
        public PathChain Path8;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(87.580, 9.000),
                                    new Pose(101.716, 60.097)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                    .build();

            Path2 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(101.716, 60.097),
                                    new Pose(120.931, 60.052)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                    .build();

            Path3 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(120.931, 60.052),
                                    new Pose(110.796, 69.468),
                                    new Pose(128.139, 69.059)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                    .build();

            Path4 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(128.139, 69.059),
                                    new Pose(80.525, 73.132)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                    .build();

            Path5 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(80.525, 73.132),
                                    new Pose(100, 35.225)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                    .build();

            Path6 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(80.525, 73.132),
                                    new Pose(118.947, 35.069)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                    .build();

            Path7 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(118.947, 35.069),
                                    new Pose(76.331, 19.057)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                    .build();

            Path8 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(76.331, 19.057),
                                    new Pose(77.157, 40)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(90))
                    .build();
        }
    }
}

