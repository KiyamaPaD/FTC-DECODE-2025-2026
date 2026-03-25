package org.firstinspires.ftc.teamcode.Robot.Teste;

import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.alianta;
import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.pozitieDef;
import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.pozitieRobot;
import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.localizerConstants;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Localizare.Localizare_In_Timp_Real;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.DcMotorTrack;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;
import org.firstinspires.ftc.teamcode.Robot.TeleOP.MecanumDrive;

@Config
@TeleOp (name = "MAIN")
public class SasiuTest extends LinearOpMode {

    public static boolean manual, toogle_intake = false, toogle_auto = true, toogle_shoot = false;
    public static double unghi, unghi_first, velocity, intake_pow;
    MecanumDrive sasiu;
    Localizare_In_Timp_Real localizare;
    PinpointLocalizer pinpoint;

    DcMotorTrack tracker;
    ShooterLaunch shooter;
    IntakeCUHusky intake;


    Sortare sortare;
    boolean field = true;

    double forward, strafe, rotate;

    @Override
    public void runOpMode() throws InterruptedException {
        initializare();

        sortare.openIntake();
        boolean lastCircle = false;

        while(opModeInInit())
        {
            if(gamepad1.dpadLeftWasPressed())
            {
                sortare.motif[0] = Sortare.Slot.VERDE;
                sortare.motif[1] = Sortare.Slot.MOV;
                sortare.motif[0] = Sortare.Slot.MOV;
            }
            if(gamepad1.dpadUpWasPressed())
            {
                sortare.motif[1] = Sortare.Slot.MOV;
                sortare.motif[0] = Sortare.Slot.VERDE;
                sortare.motif[0] = Sortare.Slot.MOV;
            }
            if(gamepad1.dpadRightWasPressed())
            {
                sortare.motif[2] = Sortare.Slot.MOV;
                sortare.motif[0] = Sortare.Slot.MOV;
                sortare.motif[0] = Sortare.Slot.VERDE;
            }

            if(gamepad1.dpadDownWasPressed())
            {
                if(alianta == LimeLight.Goal.RED) alianta = LimeLight.Goal.BLUE;
                else alianta = LimeLight.Goal.RED;
            }

            telemetry.addLine("dpad up v m m ");
            telemetry.addLine("dpad left m v m ");
            telemetry.addLine("dpad down m m v ");
            telemetry.addLine("current alliance: " + alianta + " - DPAD DOWN TO SWITCH");
            telemetry.update();
        }
        waitForStart();
        while(opModeIsActive())
        {

            if(gamepad1.circleWasPressed()) sortare.Shoot();

            sortare.Update();

            pinpoint.update();

            intake.merg();

            TelemetryPacket packet = new TelemetryPacket();
            Canvas overlay = packet.fieldOverlay();

            overlay.setFill("red");
            overlay.setStroke("red");
            overlay.setStrokeWidth(1);
            overlay.setTranslation(0, 0);
            overlay.setRotation(Math.toRadians(90));

            forward = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;

            if(gamepad1.dpadUpWasPressed()) field = !field;

            if(gamepad1.dpadLeftWasPressed())
            {
                if(sortare.currentState == Sortare.SortState.SWITCH_SLOT)
                {
                    sortare.nrBileInSort++;
                    sortare.bilaLaRand--;
                }
            }

//            if(gamepad1.crossWasPressed())
//                toogle_intake = !toogle_intake;

            if(gamepad1.triangleWasPressed())
                toogle_auto = !toogle_auto;

            if(!toogle_auto)
            {
                shooter.setShooterVelocity(0);

            }
            else
            {
                shooter.setShooterVelocity(shooter.autoVelocity(localizare.distanceFromBasket(alianta)));
                shooter.adjustAngle(shooter.autoAngle(localizare.distanceFromBasket(alianta)));
            }

//            if(toogle_intake)
//                intake.setPower(1);


            if(field) sasiu.DriveFieldOrientated(forward, strafe, rotate);
            else sasiu.Drive(forward, strafe, rotate);

            if(gamepad1.leftBumperWasPressed()) sasiu.setMaxSpeed(0.5);
            if(gamepad1.rightBumperWasPressed()) sasiu.setMaxSpeed(1);

            tracker.PredictTarget(alianta);
            tracker.Track(alianta);

            if(localizare.LB != null && localizare.RB != null && localizare.LF != null && localizare.RF != null)
            {
                overlay.strokeLine(localizare.LB.getX()-72, localizare.LB.getY()-72, localizare.RB.getX()-72, localizare.RB.getY()-72);
                overlay.strokeLine(localizare.RB.getX()-72, localizare.RB.getY()-72, localizare.RF.getX()-72, localizare.RF.getY()-72);
                overlay.strokeLine(localizare.RF.getX()-72, localizare.RF.getY()-72, localizare.LF.getX()-72, localizare.LF.getY()-72);
                overlay.strokeLine(localizare.LF.getX()-72, localizare.LF.getY()-72, localizare.LB.getX()-72, localizare.LB.getY()-72);

                overlay.fillCircle(localizare.RF.getX()-72, localizare.RF.getY()-72, 1.5);
                overlay.fillCircle(localizare.LF.getX()-72, localizare.LF.getY()-72, 1.5);
            }

            FtcDashboard.getInstance().sendTelemetryPacket(packet);

            telemetry.addData("vede bila", sortare.senzor.vedeBila());
            telemetry.addData("culoare bila", sortare.senzor.culoareBila());
            telemetry.addData("asteapta bila", sortare.asteaptaBila);
            telemetry.addData("intak", sortare.Intaking);
            telemetry.addData("viteza flywheel", shooter.fly1.getVelocity());

//            if(manual)
//            {
//                shooter.setShooterVelocity(velocity);
//                if(sorter.bilaLaRand == 0) shooter.adjustAngle(unghi_first);
//                else shooter.adjustAngle(unghi);
//            }
//            else
//            {
//                shooter.setShooterVelocity(shooter.autoVelocity(localizare.distanceFromBasket(alianta)));
//                shooter.adjustAngle(shooter.autoAngle(localizare.distanceFromBasket(alianta)));
//            }
            //intakeMotor.manual(pow_intake);

//            intake.setPower(intake_pow);

            telemetry.addData("pos", pinpoint.getPose());
            telemetry.addData("distance from " + alianta + "basket", localizare.distanceFromBasket(alianta));
            telemetry.addData("hood", shooter.autoAngle(localizare.distanceFromBasket(alianta)));
            telemetry.addData("velocity", shooter.autoVelocity(localizare.distanceFromBasket(alianta)));
            telemetry.addData("in launch", localizare.robotInLaunchZone());
            telemetry.update();
        }
        pinpoint.update();
        pozitieRobot = pinpoint.getPose();
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
        sortare = new Sortare(hardwareMap, telemetry);
        shooter = new ShooterLaunch(hardwareMap, telemetry, false);
        tracker = new DcMotorTrack(hardwareMap, telemetry, pinpoint);
        intake = new IntakeCUHusky(hardwareMap,telemetry,gamepad1);


        telemetry.addLine(status);
        telemetry.addLine("init succes");
        telemetry.update();
    }
}
