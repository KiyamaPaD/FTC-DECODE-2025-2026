package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
public class Kf_Calibrator extends LinearOpMode {

    public static String nume_motor = "tracker";
    public static double pow, Ks = 0.003, Kv=0.000420, target_velocity;
    public double velocity;
    ElapsedTime timer = new ElapsedTime();
    DcMotorEx motor;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while(opModeIsActive())
        {
            double power = Ks + (target_velocity * Kv);
            motor.setPower(power);
            telemetry.addData("velocity", motor.getVelocity());
            telemetry.update();
        }
    }

    public void initialize()
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        motor = hardwareMap.get(DcMotorEx.class, nume_motor);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("init succes");
        telemetry.update();
    }
}
