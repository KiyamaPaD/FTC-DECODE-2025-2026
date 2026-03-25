package org.firstinspires.ftc.teamcode.Robot.Teste;

import static org.firstinspires.ftc.teamcode.Robot.Systems.Memorie.memorie.alianta;
import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.localizerConstants;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake.IntakeCUHusky;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Localizare.Localizare_In_Timp_Real;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Transfer;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta.ShooterLaunch;


@TeleOp
@Config
public class test_sorter extends LinearOpMode {

    public static boolean updatePID;
    public static double flywheel_target, unghi=0.3, unghi_first=0.3, kp = 35.5, ki, kd = 1, kf = 12, pow_intake;
    Sortare sorter;
    Transfer transfer;
    DcMotor intaker;

    PinpointLocalizer pinpoint;
    IntakeCUHusky intakeMotor;

    Localizare_In_Timp_Real localizare;

    ShooterLaunch shooter;

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        while (opModeIsActive())
        {
            sorter.Update();
            telemetry.addData("vede bila", sorter.senzor.vedeBila());
            telemetry.addData("culoare bila", sorter.senzor.culoareBila());
            telemetry.addData("asteapta bila", sorter.asteaptaBila);
            telemetry.addData("intak", sorter.Intaking);
            telemetry.addData("viteza flywheel", shooter.fly1.getVelocity());
            telemetry.addData("transfer up", transfer.isTransferUp());
            telemetry.addData("transfer down", transfer.isTransferDown());


            if(updatePID) shooter.updatePIDF(kp, ki, kd, kf); //0.4 12 0.1 45
            shooter.debugTelemetry();

            shooter.setShooterVelocity(shooter.autoVelocity(localizare.distanceFromBasket(alianta)));
            shooter.adjustAngle(shooter.autoAngle(localizare.distanceFromBasket(alianta)));

            sorter.startTransferCommand = true;
            sorter.readyToShoot = true;

            intaker.setPower(pow_intake);

            //intaker.setPower((double)intake);
            telemetry.update();
        }
    }

    void Init()
    {
        pinpoint = new PinpointLocalizer(hardwareMap, localizerConstants);
        pinpoint.setStartPose(new Pose(72, 72, Math.toRadians(45)));

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        sorter = new Sortare(hardwareMap, telemetry);
        intaker = hardwareMap.get(DcMotor.class, "intake");
        shooter = new ShooterLaunch(hardwareMap, telemetry,false);
        transfer = new Transfer(hardwareMap, telemetry);
        localizare = new Localizare_In_Timp_Real(pinpoint);
        //intakeMotor = new IntakeCUHusky(hardwareMap);

        /// test
        sorter.motif[0] = Sortare.Slot.VERDE;
        sorter.Intaking = true;
        sorter.asteaptaBila = true;

        telemetry.addLine("init succes");
        telemetry.update();
    }
}
