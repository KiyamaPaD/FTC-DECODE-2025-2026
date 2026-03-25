package org.firstinspires.ftc.teamcode.Robot.Teste;

import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.localizerConstants;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Localizare.Localizare_In_Timp_Real;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.DcMotorTrack;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;
import org.firstinspires.ftc.teamcode.Robot.TeleOP.MecanumDrive;

@TeleOp
@Config
public class tracking extends LinearOpMode {

    public static double mod, target_manual, flywheel_target, kp, ki, kd, kf;
    public static boolean manualTurret = true, TrackOK,reset_Pos_Tracker, flyangle;
    LimeLight llcam;
    DcMotorTrack tracker;
    ShooterLaunch shooter;
    MecanumDrive sasiu;
    Localizare_In_Timp_Real localizare;

    Sortare sorter;
    PinpointLocalizer pinpoint;

    @Override
    public void runOpMode() throws InterruptedException {
        initi();
        waitForStart();
        while(opModeIsActive())
        {

            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;

            if(reset_Pos_Tracker)
                tracker.tracker.setMode(DcMotor.RunMode.RESET_ENCODERS);


            sasiu.Drive(forward, strafe, rotate);

            if(manualTurret) tracker.setTargetManual(target_manual);
            else tracker.PredictTarget(LimeLight.Goal.BLUE);


            tracker.Track(LimeLight.Goal.BLUE);


            if(flyangle)
            {
                shooter.setShooterVelocity(shooter.autoVelocity(localizare.distanceFromBasket(LimeLight.Goal.BLUE)));
                shooter.adjustAngle(shooter.autoAngle(localizare.distanceFromBasket(LimeLight.Goal.BLUE)));
            }
            else
            {
                shooter.adjustAngle(0.89);
                shooter.setShooterVelocity(0);
            }


            sorter.Update();

            shooter.debugTelemetry();
            telemetry.addData("output", tracker.tracker.getCurrentPosition());
            telemetry.addData("vede apriltag", llcam.AprilTagVisible(LimeLight.Goal.BLUE));

            double unghi;
            unghi = (double) tracker.tracker.getCurrentPosition() * 360 / tracker.ticks_per_rev / tracker.gearRatio;
            telemetry.addData("unghi", unghi);
            telemetry.update();
        }
    }

    void initi()
    {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry());

        pinpoint = new PinpointLocalizer(hardwareMap, localizerConstants);
        pinpoint.setStartPose(new Pose(87.58, 9.28, Math.toRadians(90)));

        llcam = new LimeLight(hardwareMap, telemetry, pinpoint);
        tracker = new DcMotorTrack(hardwareMap, telemetry, pinpoint);
        sasiu = new MecanumDrive(hardwareMap, telemetry);
        shooter = new ShooterLaunch(hardwareMap, telemetry, false);
        localizare = new Localizare_In_Timp_Real(pinpoint);
        sorter = new Sortare(hardwareMap, telemetry);

        telemetry.addLine("Initializarea este cu succes");
        telemetry.update();
    }
}
