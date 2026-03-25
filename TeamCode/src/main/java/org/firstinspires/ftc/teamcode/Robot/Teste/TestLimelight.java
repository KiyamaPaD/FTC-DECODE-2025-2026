package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;

@Config
@TeleOp (group = "tests")
public class TestLimelight extends LinearOpMode {


    public static int posST, posDR, tolerance;
    public static double speed;
    DcMotorEx tracker;
    LimeLight llcam;

    int error;
    boolean ok;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();
        while(opModeIsActive())
        {
            if(ok)
            {
                tracker.setTargetPosition((int)Math.round(posDR));
                tracker.setVelocity(speed);
                tracker.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if(Math.abs(tracker.getCurrentPosition() - tracker.getTargetPosition()) < tolerance)
                {
                    ok = !ok;
                }
            }
            else
            {
                tracker.setTargetPosition((int)Math.round(posST));
                tracker.setVelocity(speed);
                tracker.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if(Math.abs(tracker.getCurrentPosition() - tracker.getTargetPosition()) < tolerance)
                {
                    ok = !ok;
                }
            }

            if(llcam.AprilTagGoalYaw(LimeLight.Goal.RED) == 0) error++;
            telemetry.addData("undetected frames", error);
            telemetry.update();
        }
    }

    void initialize()
    {
        tracker = hardwareMap.get(DcMotorEx.class, "tracker");
        tracker.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        tracker.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        llcam = new LimeLight(hardwareMap, telemetry);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addLine("init cu succes");
        telemetry.update();
    }
}
