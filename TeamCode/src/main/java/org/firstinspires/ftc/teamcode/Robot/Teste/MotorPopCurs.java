package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class MotorPopCurs extends LinearOpMode {

    DcMotorEx motor_test;
    public static double power = 0;
    public static String nume;

    @Override
    public void runOpMode() throws InterruptedException {

        Initializare();

        waitForStart();

        while(opModeIsActive())
        {
            motor_test.setPower(power);

            telemetry.addData("puterea motorului este: ", motor_test.getPower()); ///telemetrie pentru a arata puterea motorului
            telemetry.update();

        }
    }

    void Initializare()
    {
        motor_test = hardwareMap.get(DcMotorEx.class, nume); ///constructorul

        motor_test.setDirection(DcMotorSimple.Direction.FORWARD); ///directia la motor
        motor_test.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_test.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); ///float merge liber, brake se opreste instant (deci va stagna)

        telemetry.addLine("Initializarea este cu succes"); ///telemetrie ca s o terminat Initializarea
        telemetry.update();

    }

}
