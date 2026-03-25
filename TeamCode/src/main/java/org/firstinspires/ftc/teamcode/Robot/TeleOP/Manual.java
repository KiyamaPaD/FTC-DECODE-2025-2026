package org.firstinspires.ftc.teamcode.Robot.TeleOP;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.DcMotorTrack;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;

import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.localizerConstants;

@Config
@TeleOp (name = "MainTeleOp")
public class Manual extends LinearOpMode
{
    PinpointLocalizer pinpoint;
    /** ______VARIABILE______ **/
    MecanumDrive Sasiu;
    Sortare sortare;
    DcMotorTrack shooter;
    ServoEx servo_sortare;
    LimeLight llcam;
    ShooterLaunch launch;
    IntakeCUHusky huskyIntake;

    double unghi;


    public static boolean FieldOrientated, manualTurret = true, TrackOK;
    public static int mod = 3;
    public static double target_manual;
    public static Pose startingPose = new Pose(63.25370675453046, 8.894563426688626, 90);

    @Override
    public void runOpMode() throws InterruptedException
    {
        /** ______MANUAL______ **/
        double forward, strafe, rotate;

        Initializeaza();

        waitForStart();

        pinpoint.setStartPose(startingPose);

        while(opModeIsActive())
        {
            if(gamepad1.rightBumperWasPressed())
                if(FieldOrientated) FieldOrientated = false;
                    else FieldOrientated = true;

            if(gamepad1.touchpadWasPressed()) TrackOK = !TrackOK;

            /*
            forward = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;

            if(FieldOrientated)
                Sasiu.DriveFieldOrientated(forward, strafe, rotate);
            else
            {
                Sasiu.Drive(forward, strafe, rotate);
            }
             */



            if(manualTurret)
            {
                shooter.setTargetManual(target_manual);
            }
            else
            {
                if(TrackOK)
                {
                    if(mod == 1) shooter.PredictTarget(LimeLight.Goal.RED); ///cu Kalman Filter
                    else if(mod == 2) shooter.ProcessTarget(LimeLight.Goal.RED);
                    else if(mod == 3) shooter.PositionTarget(2);
                }
            }
            //huskyIntake.merg(telemetry, gamepad1);

            /*if(gamepad1.right_trigger > 0.6) launch.setShooterVelocity(1);

            if(gamepad1.dpad_up && unghi < 0.8) unghi += 0.01;
            if(gamepad1.dpad_down && unghi > 0) unghi -= 0.01;

            launch.adjustAngle(unghi);

            //double robotVel = imu.getRobotAngularVelocity().zRotationRate;*/
            shooter.Track(LimeLight.Goal.RED);

            telemetry.addData("output", shooter.tracker.getCurrentPosition());
            telemetry.addData("vede apriltag", llcam.AprilTagVisible(LimeLight.Goal.RED));

            double unghi;
            unghi = (double)shooter.tracker.getCurrentPosition() * 360 / shooter.ticks_per_rev / shooter.gearRatio;
            telemetry.addData("unghi", unghi);
            //telemetry.addData("target manual", target_manual);

            //telemetry.addData("lime light red", llcam.AprilTagVisible(LimeLight.Goal.RED));
            //telemetry.addData("lime light red yaw", llcam.AprilTagGoalYaw(LimeLight.Goal.RED));
            //telemetry.addData("lime light blue", llcam.AprilTagVisible(LimeLight.Goal.BLUE));
            //telemetry.addData("lime light blue yaw", llcam.AprilTagGoalYaw(LimeLight.Goal.BLUE));
            //telemetry.addData("tag nr", llcam.AprilTagResultID());

            telemetry.update();

        }

    }
    void Initializeaza()
    {

        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry());

        pinpoint = new PinpointLocalizer(hardwareMap, localizerConstants);

        //Sasiu = new MecanumDrive(hardwareMap, telemetry, pinpoint);
        llcam = new LimeLight(hardwareMap, telemetry, pinpoint);
        shooter = new DcMotorTrack(hardwareMap, telemetry, pinpoint);
        //launch = new ShooterLaunch(hardwareMap, telemetry);
        //huskyIntake = new IntakeCUHusky(hardwareMap);

        telemetry.addLine("Initializarea este cu succes");
        telemetry.update();
    }

}
