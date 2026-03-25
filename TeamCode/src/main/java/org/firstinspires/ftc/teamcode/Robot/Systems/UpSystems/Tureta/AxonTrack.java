package org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Teste.RTPAxon;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;

@Config
public class AxonTrack {

    private final double LOOP_DT = 0.02; ///50 hz in pipeline-u de la limelight = 20 ms

    public static double Q_ANGLE = 0.0001;
    public static double Q_VELOCITY = 3;
    public static double R_TX = 0.00001105;
    public static double trust_time = 1;

    LimelightKalmanFilter llkf;

    public static double tolerance, Kp = 0.003, Ki = 0.000005, Kd = 0.0003, Ks = 0.0003, Kv=0.00042, Kp_wrap = 0.003, Ki_wrap, Kd_wrap=0.0004;
    public double Kf;
    public static double gearRatio = 5.625, pivotOffset=13.3;
    public static double dead_zone=10;
    public double target, second_target;
    Telemetry telemetry;

    public CRServo axon;
    public AnalogInput analog;
    public RTPAxon tracker;
    public static double maxPower;
    LimeLight llcam;
    PIDController pid;
    double saved_yaw, saved_output;
    ElapsedTime timer = new ElapsedTime();
    double timp, lastLoopTime, lastTrackerPos;
    int zone_target, zone_tracker;
    PinpointLocalizer pinpoint;

    public boolean wraping;


    public void ProcessTarget(LimeLight.Goal goal)
    {
        if(llcam.AprilTagVisible(goal))
        {
            double LO = llcam.AprilTagGoalDistance(goal) * Math.cos(Math.toRadians(llcam.AprilTagGoalYaw(goal)));
            double AO = llcam.AprilTagGoalDistance(goal) * Math.sin(Math.toRadians(llcam.AprilTagGoalYaw(goal)));
            double PO = LO + pivotOffset;
            double correctAngle = Math.toDegrees(Math.atan2(AO, PO));

            target = tracker.getTotalRotation() + correctAngle * gearRatio;

            saved_yaw = llcam.AprilTagGoalYaw(goal);
            saved_output = tracker.getTotalRotation();

            second_target = saved_output + saved_yaw * gearRatio;
        }
        else
        {
            target = second_target;
        }
        telemetry.addData("saved yaw", saved_yaw);
        telemetry.addData("saved output", saved_output);
    }

    public void setTargetManual(double target)
    {
        this.target = target * gearRatio;
        pid.setSetPoint(this.target);
    }
    public void PredictTarget(LimeLight.Goal goal)
    {
        double currentTime = timer.seconds();

        double commandedAcceleration = 0;

        llkf.setProcessNoise(Q_ANGLE, Q_VELOCITY);
        llkf.setMeasurementNoise(R_TX);

        double estimatedTx;
        double LO = llcam.AprilTagGoalDistance(goal) * Math.cos(Math.toRadians(llcam.AprilTagGoalYaw(goal)));
        double AO = llcam.AprilTagGoalDistance(goal) * Math.sin(Math.toRadians(llcam.AprilTagGoalYaw(goal)));
        double PO = LO + pivotOffset;
        double correctAngle = Math.toDegrees(Math.atan2(AO, PO));

        telemetry.addData("limelight angle", llcam.AprilTagGoalYaw(goal));
        telemetry.addData("correct angle", correctAngle);

        estimatedTx = llkf.update(correctAngle, commandedAcceleration, llcam.AprilTagVisible(goal));

        double target_velocity = llkf.getEstimatedVelocity();
        if(target_velocity > 0) Kf = Ks * Math.signum(target_velocity) + target_velocity * Kv;
        else Kf = 0;

        if(llcam.AprilTagVisible(goal)) timp = timer.seconds();

        if(!wraping)
            if(timer.seconds() - timp < trust_time) target = (double)tracker.getTargetRotation() + (estimatedTx * gearRatio);
            else target = tracker.getTotalRotation();

        // 5. Cleanup
        lastLoopTime = currentTime;
        lastTrackerPos = tracker.getTotalRotation();

        telemetry.addData("Estimated Offset (Deg)", estimatedTx);
        telemetry.addData("Target (Ticks)", target);
    }

    public void PositionTarget(int basket)
    {
        pinpoint.update();
        double robotX = pinpoint.getPose().getX();
        double robotY = pinpoint.getPose().getY();
        double robotHeading = pinpoint.getPose().getHeading(); // În Radiani

        double targetX = 12;
        double targetY = 137;

        if(basket == 2) /// cosu rosu
        {
            targetX = 133;
            targetY = 137;
        }

        double deltaX = targetX - robotX;
        double deltaY = targetY - robotY;

        double absoluteAngleToTarget = Math.atan2(deltaY, deltaX);

        target = (absoluteAngleToTarget - robotHeading);

    }
    public void Track(LimeLight.Goal goal)
    {
        zone_target=0;
        zone_tracker=0;

        double trackerPos = (int)tracker.getTotalRotation();

        while (trackerPos >= 360) {trackerPos = trackerPos - 360; zone_tracker++; }
        while (trackerPos < 0) { trackerPos = trackerPos + 360; zone_tracker--; }

        while (target >= 360) { target = target - 360; zone_target++; }
        while (target < 0) { target = target + 360; zone_target--; }

        if(Math.abs(target) > dead_zone && Math.abs(target) < 360 - dead_zone && zone_target != zone_tracker)
        {
            wraping = true;
            tracker.setPidCoeffs(Kp_wrap, Ki_wrap, Kd_wrap);
            tracker.resetPID();
        }
        if((Math.abs(target - (double)tracker.getTotalRotation()) < 180 && llcam.AprilTagVisible(goal)) || Math.abs(target - (double)tracker.getTotalRotation()) < 5)
        {
            wraping = false;
            tracker.setPidCoeffs(Kp, Ki, Kd);
            tracker.resetPID();
        }

        telemetry.addData("target", target);
        telemetry.addData("current2", (double)tracker.getTotalRotation());

        if(Math.abs(target)> dead_zone && Math.abs(target)< 360-dead_zone)
            tracker.setTargetRotation(target);

        if(!tracker.isAtTarget(tolerance)) tracker.update();
        else tracker.setPower(0);

        tracker.setPidCoeffs(Kp, Ki, Kd);
        telemetry.addData("zone tracker", zone_tracker);
        telemetry.addData("zone target", zone_target);
        telemetry.addData("wraping", wraping);
        telemetry.addData("pid setpoint", pid.getSetPoint());
    }

    public AxonTrack(HardwareMap hw, Telemetry tele)
    {
        telemetry = tele;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        axon = hw.get(CRServo.class, "tracker");
        analog = hw.get(AnalogInput.class, "tracker analog");
        tracker = new RTPAxon(axon, analog);
        tracker.setRtp(true);
        tracker.setMaxPower(maxPower);
        tracker.setPidCoeffs(Kp, Ki, Kd);

        llcam = new LimeLight(hw, tele);
        llkf = new LimelightKalmanFilter(LOOP_DT, Q_ANGLE, Q_VELOCITY, R_TX);
    }

    public AxonTrack(HardwareMap hw, Telemetry tele, PinpointLocalizer pinpoint)
    {
        telemetry = tele;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        this.pinpoint = pinpoint;

        axon = hw.get(CRServo.class, "tracker");
        analog = hw.get(AnalogInput.class, "tracker analog");
        tracker = new RTPAxon(axon, analog);
        tracker.setRtp(true);
        tracker.setMaxPower(maxPower);
        tracker.setPidCoeffs(Kp, Ki, Kd);

        llcam = new LimeLight(hw, tele);
        llkf = new LimelightKalmanFilter(LOOP_DT, Q_ANGLE, Q_VELOCITY, R_TX);
    }
}

