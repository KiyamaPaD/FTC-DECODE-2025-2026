package org.firstinspires.ftc.teamcode.Robot.TeleOP;

import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.alianta;
import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.pozitieDef;
import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.pozitieRobot;
import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.localizerConstants;

import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.DcMotorTrack;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Localizare.Localizare_In_Timp_Real;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;

public class
MainTeleOp extends LinearOpMode {

    PinpointLocalizer pinpoint;
    MecanumDrive sasiu;
    Localizare_In_Timp_Real localizare;
    Sortare sorter;
    DcMotorTrack tracker;
    ShooterLaunch shooter;
    IntakeCUHusky intake;

    @Override
    public void runOpMode() throws InterruptedException {
        initializare();
        waitForStart();
        while(opModeIsActive())
        {
            /// SASIU
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;

            sasiu.DriveFieldOrientated(forward, strafe, rotate);

            /// TURETA
            tracker.PredictTarget(alianta);
            tracker.Track(alianta);

            shooter.autoVelocity(localizare.distanceFromBasket(alianta));
            shooter.autoAngle(localizare.distanceFromBasket(alianta));

            /// SORTARE
            sorter.Update();

            /// INTAKE
            intake.merg();

            telemetry.update();
        }
    }

    void initializare()
    {
        String status;
        pinpoint = new PinpointLocalizer(hardwareMap, localizerConstants);

        if (pozitieRobot != null) {
            pinpoint.setStartPose(pozitieRobot);
            status = "LOADED FROM AUTO";
        } else {
            pinpoint.setStartPose(pozitieDef);
            status = "NO SAVED DATA: Using Default Pose, default alliance RED";
        }

        sasiu = new MecanumDrive(hardwareMap, telemetry, pinpoint, alianta);
        localizare = new Localizare_In_Timp_Real(pinpoint);
        sorter = new Sortare(hardwareMap, telemetry);
        shooter = new ShooterLaunch(hardwareMap, telemetry, false);
        tracker = new DcMotorTrack(hardwareMap, telemetry, pinpoint);
        intake = new IntakeCUHusky(hardwareMap, telemetry, gamepad1);

        telemetry.addLine(status);
        telemetry.addLine("init succes");
        telemetry.update();
    }
}
