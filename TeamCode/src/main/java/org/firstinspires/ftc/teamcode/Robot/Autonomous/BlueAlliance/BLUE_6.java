package org.firstinspires.ftc.teamcode.Robot.Autonomous.BlueAlliance;

import static org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare.MOTIF_MEMORAT;
import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.alianta;
import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.pozitieRobot;
import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.localizerConstants;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Localizare.Localizare_In_Timp_Real;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.DcMotorTrack;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Configurable
@Autonomous(name="🔵 Blue Alliance Low")
public class BLUE_6 extends LinearOpMode {

    public static double offset;

    boolean telemtrie = true;
    boolean can_shoot = true;
    boolean battery_saver = false;
    Thread thread_sortare;

    public static Pose startPose = new Pose(56.42, 9.28, Math.toRadians(90));

    private Follower follower;
    private PinpointLocalizer pinpoint;
    private Localizare_In_Timp_Real localizare;
    private DcMotorTrack tracker;
    private IntakeCUHusky intake;
    private Sortare sortare;
    private ShooterLaunch shooter;

    private TelemetryManager panelsTelemetry;
    private int pathState;
    private Paths paths;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();

        while (!opModeIsActive() && opModeInInit()) {
            sortare.SetMotif();
            telemetry.addData("Alliance", "BLUE");
            telemetry.addData("Motif detected", sortare.GetID());
            telemetry.update();
        }

        waitForStart();

        if (opModeIsActive()) {

            sortare.sorteazaOrdinea();
            thread_sortare.start();

            sortare.Shoot();
            while (sortare.nrBileInSort > 0 && opModeIsActive()) {
                idle();
            }

            follower.followPath(paths.PrepBall);
            while (follower.isBusy() && opModeIsActive()) followLoop();
            /*
            intake.intake.setPower(1);
            follower.setMaxPower(0.23);
            follower.followPath(paths.GetBall);
            while (follower.isBusy() && opModeIsActive()) followLoop();

            intake.intake.setPower(0);
            follower.setMaxPower(1);
            follower.followPath(paths.GoShoot);
            while (follower.isBusy() && opModeIsActive()) followLoop();

            sortare.Shoot();
            while (sortare.nrBileInSort != 0 && opModeIsActive()) idle();

            intake.intake.setPower(0);
            follower.setMaxPower(1);
            follower.followPath(paths.Leave);
            while (follower.isBusy() && opModeIsActive()) followLoop();

            while (sortare.nrBileInSort != 0 && opModeIsActive()) idle();

            */
            alianta = LimeLight.Goal.BLUE;
            pinpoint.update();
            pozitieRobot= pinpoint.getPose();
        }
    }

    void followLoop() {
        follower.update();
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.update();
    }

    private void Init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        alianta = LimeLight.Goal.BLUE;

        thread_sortare = new Thread(() -> {
            while (opModeIsActive()) {
                pinpoint.update();
                sortare.Update();

                if (battery_saver) {
                    shooter.setShooterVelocity(0);
                } else {
                    double dist = localizare.distanceFromBasket(alianta);
                    shooter.setShooterVelocity(shooter.autoVelocity(dist));
                    shooter.adjustAngle(shooter.autoAngle(dist));
                }

                if (!MOTIF_MEMORAT) {
                    sortare.SetMotif();
                } else {
                    tracker.PredictTarget(alianta, offset);
                    tracker.Track(alianta);
                }
                telemetry.addData("distanta", localizare.distanceFromBasket(alianta));
                telemetry.update();
            }
        });

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(startPose);

        paths = new Paths(follower);

        pinpoint = new PinpointLocalizer(hardwareMap, localizerConstants);
        pinpoint.setStartPose(startPose);
        localizare = new Localizare_In_Timp_Real(pinpoint);

        intake = new IntakeCUHusky(hardwareMap, telemetry, gamepad1);
        shooter = new ShooterLaunch(hardwareMap, telemetry, false);
        sortare = new Sortare(hardwareMap, telemetry);
        tracker = new DcMotorTrack(hardwareMap, telemetry, pinpoint);

        sortare.readyToShoot = can_shoot;
        sortare.nrBileInSort = 3;
        sortare.BileInSort = new Sortare.Slot[]{Sortare.Slot.VERDE, Sortare.Slot.MOV, Sortare.Slot.MOV};

        panelsTelemetry.debug("Status", "Blue Initialized");
        panelsTelemetry.update(telemetry);
    }

    public static class Paths {
        public PathChain PrepBall;
        public PathChain GetBall;
        public PathChain GoShoot;
        public PathChain Leave;

        public Paths(Follower follower) {
            PrepBall = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(56.42, 9.28), new Pose(48, 35.0)))
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .build();

            GetBall = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(48, 35.0), new Pose(58.1, 40)))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            GoShoot = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(24, 35.0), new Pose(58.1, 13.06)))
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                    .build();

            Leave = follower.pathBuilder()
                    .addPath(new BezierLine(new Pose(58.1, 13.06), new Pose(58.1, 40)))
                    .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(90))
                    .build();

        }
    }
}