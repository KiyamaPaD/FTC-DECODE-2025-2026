package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Config
public class PIDTestPopCurs extends LinearOpMode {

    DcMotorEx motor_test;

    PIDController pidController;
    public static double p,i,d;
    public static double target = 0;
    public static boolean rawpower;
    public static double power = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        Initializare();

        waitForStart();

        while(opModeIsActive())
        {
            if(!rawpower) {
                pidController.setPID(p, i, d);
                pidController.setSetPoint(target);
                double outputbase = pidController.calculate(motor_test.getVelocity(), target);
                motor_test.setVelocity(outputbase);

                telemetry.addData("output base", outputbase);
                telemetry.addData("fly1 target", pidController.getSetPoint());
                telemetry.addData("fly1 position", motor_test.getVelocity());
                telemetry.update();
            }
            else
            {
                motor_test.setPower(power);
            }
        }

    }

    void Initializare()
    {
        telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry()); ///telemetrie pentru dashboard

        motor_test = hardwareMap.get(DcMotorEx.class, "fly1");

        motor_test.setDirection(DcMotorSimple.Direction.FORWARD);

        //motor_test.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pidController = new PIDController(0,0,0);
        pidController.setSetPoint(target);
        pidController.reset();
        telemetry.addLine("Initializarea este cu succes"); ///telemetrie ca s o terminat Initializarea
        telemetry.update();
    }

}
