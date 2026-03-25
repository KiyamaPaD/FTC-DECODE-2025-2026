package org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class LimeLight {

    public static double hLL=43.7, hTag=75, llAngle=23.4;
    public static boolean reverseX=false;
    Telemetry telemetry;
    public static Limelight3A limeLight3A;
    PinpointLocalizer pinpoint;

    public int AprilTagResultID(boolean prioritize_motif) {
        LLResult llResult = limeLight3A.getLatestResult();
        if (llResult != null && llResult.isValid()) {

            if(prioritize_motif)
            {
                for (LLResultTypes.FiducialResult tag : llResult.getFiducialResults()) {
                    int id = tag.getFiducialId();
                    if (id == 21 || id == 22 || id == 23) {
                        return id;
                    }
                }
            }
            else
            {
                for (LLResultTypes.FiducialResult tag : llResult.getFiducialResults()) {
                    int id = tag.getFiducialId();
                    if (id == 20 || id == 24) {
                        return id;
                    }
                }
            }
            return llResult.getFiducialResults().get(0).getFiducialId();
        }
        return -1;
    }


    public enum Goal {
        RED,
        BLUE
    }
    public double AprilTagGoalYaw(Goal gol) {
        int targetId = (gol == Goal.RED) ? 24 : 20;
        LLResult llResult = limeLight3A.getLatestResult();

        if (llResult != null && llResult.isValid()) {
            for (LLResultTypes.FiducialResult tag : llResult.getFiducialResults()) {
                if (tag.getFiducialId() == targetId) {

                    double tx = tag.getTargetXDegrees();
                    return reverseX ? -tx : tx;
                }
            }
        }
        return 0;
    }

    public double AprilTagGoalDistance(Goal gol) {
        int targetId = (gol == Goal.RED) ? 24 : 20;
        LLResult llResult = limeLight3A.getLatestResult();

        if (llResult != null && llResult.isValid()) {
            for (LLResultTypes.FiducialResult tag : llResult.getFiducialResults()) {
                if (tag.getFiducialId() == targetId) {

                    double ty = tag.getTargetYDegrees();
                    double unghi = ty + llAngle;

                    if (Math.abs(Math.tan(Math.toRadians(unghi))) < 1e-4) return 0;

                    double distance = (hTag - hLL) / Math.tan(Math.toRadians(unghi));
                    return distance;
                }
            }
        }
        return 0;
    }

    public boolean AprilTagVisible(Goal goal) {
        int targetId = (goal == Goal.RED) ? 24 : 20;
        LLResult llResult = limeLight3A.getLatestResult();

        if (llResult != null && llResult.isValid()) {
            for (LLResultTypes.FiducialResult tag : llResult.getFiducialResults()) {
                if (tag.getFiducialId() == targetId) {
                    return true;
                }
            }
        }
        return false;
    }

    public LimeLight(HardwareMap hw, Telemetry tel, PinpointLocalizer pinpoint) {

        this.pinpoint = pinpoint;

        telemetry = tel;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        limeLight3A = hw.get(Limelight3A.class, "limelight");
        init();
    }

    public LimeLight(HardwareMap hw, Telemetry tel)
    {
        telemetry = tel;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        limeLight3A = hw.get(Limelight3A.class, "limelight");
        init();
    }

    public void init()
    {
        limeLight3A.start();
        limeLight3A.pipelineSwitch(0);

        telemetry.addLine("Initializarea este cu succes");
        telemetry.update();
    }
}
