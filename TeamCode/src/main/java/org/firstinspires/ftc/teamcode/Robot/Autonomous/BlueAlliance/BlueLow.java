package org.firstinspires.ftc.teamcode.Robot.Autonomous.BlueAlliance;

import static org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare.MOTIF_MEMORAT;
import static org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare.motif;
import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.alianta;
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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Autonomous.Traiectorii_Raw.Traiectorii;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Localizare.Localizare_In_Timp_Real;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.DcMotorTrack;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Disabled
@Configurable
@Autonomous(name="\uD83D\uDD34 NU e rosu Jos")
public class BlueLow extends LinearOpMode {

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

        while (!opModeIsActive() && opModeInInit())
        {
            sortare.SetMotif();
            telemetry.addData("Motif detected",sortare.GetID());
            telemetry.addLine("Motif " + motif[0] + " " + motif[1] + " " + motif[2]);
            telemetry.update();
        }
        waitForStart();

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
            follower.followPath(paths.PrepBall);
            while (follower.isBusy())
                followLoop();

            // get balls
            intake.intake.setPower(1);
            follower.setMaxPower(0.23);
            follower.followPath(paths.GetBall);
            while (follower.isBusy())
                followLoop();

            intake.intake.setPower(0);
            follower.setMaxPower(1);
            follower.followPath(paths.GoShoot);
            while (follower.isBusy())
                followLoop();

            sortare.Shoot();

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
                        tracker.PredictTarget(alianta);
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
        public PathChain PrepBall;
        public PathChain GetBall;
        public PathChain GoShoot;

        public Paths(Follower follower) {
            PrepBall = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(87.58, 9.28),
                                    new Pose(96, 35.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                    .build();

            GetBall = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(96, 35.000),
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
        }
    }
}

