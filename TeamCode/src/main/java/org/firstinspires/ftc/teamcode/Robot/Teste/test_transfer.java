package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Transfer;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;

@Config
@TeleOp
public class test_transfer extends LinearOpMode {

    Transfer puta;
    ShooterLaunch shooter;
    public static boolean ok;
    public static double pow, angle=0.3, kp, ki, kd, kf, flywheel_target;
    @Override
    public void runOpMode() throws InterruptedException {
        initi();
        waitForStart();
        while (opModeIsActive())
        {
            puta.active = ok;
            puta.trUpdate();

            shooter.setShooterVelocity(flywheel_target);
            shooter.updatePIDF(kp, ki, kd, kf);
            shooter.debugTelemetry();
            shooter.adjustAngle(angle);
            telemetry.update();
        }
    }

    void initi()
    {
        puta = new Transfer(hardwareMap, telemetry);
        shooter = new ShooterLaunch(hardwareMap, telemetry,false);
    }
}
