package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Transfer;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;
import org.firstinspires.ftc.teamcode.Robot.TeleOP.MecanumDrive;

@Disabled
@TeleOp
public class ManualOBila extends LinearOpMode {

    MecanumDrive sasiu;
    ShooterLaunch shooter;
    IntakeCUHusky intak;
    Transfer puta;
    public double k=0;
    public int max=1,min=0;

    Servo sort;

    @Override
    public void runOpMode() throws InterruptedException {
        initi();
        waitForStart();
        while (opModeIsActive())
        {


            if(gamepad1.square)
                shooter.setShooterVelocity(1);
            if(gamepad1.squareWasReleased())
            {
                shooter.setShooterVelocity(0);
            }
            if(gamepad1.triangle)
            {
                puta.active = true;
            }
            if(gamepad1.triangleWasReleased())
            {
                puta.active = false;
            }
            if(gamepad1.dpad_up)
            {
                k=k+0.05;
            }
            if(gamepad1.dpad_down)
            {

                k=k-0.05;
            }

            shooter.adjustAngle(k);
            ///  telemetry.addData("pozitie hood ",k);

            double forward = -gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;

            sasiu.Drive(forward, strafe, rotate);
            intak.merg();
            puta.trUpdate();
            sort.setPosition(0);
        }
    }

    public void initi()
    {
        sasiu = new MecanumDrive(hardwareMap, telemetry);
        shooter = new ShooterLaunch(hardwareMap, telemetry,false);
        puta = new Transfer(hardwareMap, telemetry);
        intak = new IntakeCUHusky(hardwareMap, telemetry, gamepad1);
        sort = hardwareMap.get(Servo.class, "sortare");
        shooter.adjustAngle(0);
    }
}
