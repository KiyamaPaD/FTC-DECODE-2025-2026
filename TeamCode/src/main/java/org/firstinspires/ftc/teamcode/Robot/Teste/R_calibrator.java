package org.firstinspires.ftc.teamcode.Robot.Teste;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;
// Removed unused imports: com.qualcomm.hardware.rev.RevHubOrientationOnRobot, com.qualcomm.robotcore.hardware.IMU, org.firstinspires.ftc.teamcode.Robot.SubSystem.Tureta.DcMotorTrack

import java.util.ArrayList;
import java.util.List;

@Config
public class R_calibrator {

    public static int SAMPLE_COUNT = 500;
    public static long LOOP_DELAY_MS = 20;

    private Telemetry tele;
    private LimeLight llcam;

    public R_calibrator(LimeLight llcam, Telemetry tele1) {
        this.llcam = llcam;
        this.tele = tele1;
    }

    public double calculateMeasurementNoiseR(LimeLight.Goal goal, LinearOpMode opMode) {

        List<Double> rawData = new ArrayList<>(SAMPLE_COUNT);

        tele.addLine("--- Starting R Calibration ---");
        tele.addData("Status", "Collecting %d samples...", SAMPLE_COUNT);
        tele.update();

        for (int i = 0; i < SAMPLE_COUNT && opMode.opModeIsActive(); i++) {

            double rawTx = llcam.AprilTagGoalYaw(goal);

            if (llcam.AprilTagVisible(goal)) {
                rawData.add(rawTx);
            }

            tele.addData("Samples Collected", rawData.size());
            tele.addData("Raw Tx (Current)", rawTx);
            tele.update();

            try {
                Thread.sleep(LOOP_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        tele.addData("Status", "Data collection complete. Calculating...");
        tele.addData("Total Valid Samples", rawData.size());
        tele.update();

        if (rawData.size() < SAMPLE_COUNT / 2) {
            tele.addLine("WARNING: Too few samples collected.");
            tele.update();
            return -1.0;
        }

        double sum = 0.0;
        for (double tx : rawData) {
            sum += tx;
        }
        double mean = sum / rawData.size();

        double sumOfSquaresOfDifferences = 0.0;
        for (double tx : rawData) {
            sumOfSquaresOfDifferences += Math.pow(tx - mean, 2);
        }

        double varianceR = sumOfSquaresOfDifferences / (rawData.size() - 1);

        // --- Output Results (Use addData for key results) ---
        tele.addLine("-------------------------------------");
        tele.addData("Result: Mean Tx", "%.4f degrees", mean);
        tele.addData("Result: Std Dev (Noise)", "%.4f degrees", Math.sqrt(varianceR));
        tele.addData("Result: R_TX Value", "%.8f", varianceR);
        tele.update();

        return varianceR;
    }

    @Config
    @TeleOp (group = "tests", name = "R_Calibrator")
    public static class Test_R extends LinearOpMode
    {
        LimeLight llcam;
        R_calibrator r_calibrator;
        double calculatedR = -1.0;

        @Override
        public void runOpMode() throws InterruptedException {

            initialize();
            waitForStart();

            if(opModeIsActive())
            {
                calculatedR = r_calibrator.calculateMeasurementNoiseR(LimeLight.Goal.RED, this);
            }

            while(opModeIsActive())
            {
                telemetry.addData("Last Calculated R_TX", "%.8f", calculatedR);
                telemetry.addLine("Calibration complete. Results stable.");
                telemetry.update();
            }
        }

        public void initialize()
        {
            telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

            llcam = new LimeLight(hardwareMap, telemetry);
            r_calibrator = new R_calibrator(llcam, telemetry);

            telemetry.addLine("R-Calibrator Initialized. Press START to begin collection.");
            telemetry.update();
        }
    }
}