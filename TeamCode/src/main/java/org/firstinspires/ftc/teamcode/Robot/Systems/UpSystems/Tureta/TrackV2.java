package org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Localizare.Localizare_In_Timp_Real;
import org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare.Sortare;

@Config
public class TrackV2 {

    public static boolean enable_wrapAround, enabled_decoupling;
    private final double LOOP_DT = 0.02; ///50 hz in pipeline-u de la limelight = 20 ms

    public static double Q_ANGLE = 0.0001;
    public static double Q_VELOCITY = 0.13;
    public static double R_TX = 0.00001105;
    public static double trust_time = 0.3;

    LimelightKalmanFilter llkf;

    public static double tolerance, Kp = 0.01, Ki = 0.00001, Kd = 0.00025, Ks = 0.0003, Kv=0.00042, Kp_wrap = 0.003, Ki_wrap, Kd_wrap=0.0004;
    public double Kf;
    public static double ticks_per_rev = 140;
    public static double gearRatio = 7.2, pivotOffset=15;
    public static double dead_zone=10;
    public double target, second_target;
    Telemetry telemetry;
    public DcMotorEx tracker;
    LimeLight llcam;
    PIDController pid;
    double saved_yaw, saved_output;
    ElapsedTime timer = new ElapsedTime();
    double timp, lastLoopTime, lastTrackerPos;
    int zone_target, zone_tracker;
    PinpointLocalizer pinpoint;
    Localizare_In_Timp_Real localizare;

    Sortare sortare;

    public boolean wraping;

    public void ProcessTarget(LimeLight.Goal goal)
    {
        if(llcam.AprilTagVisible(goal))
        {
            double LO = llcam.AprilTagGoalDistance(goal) * Math.cos(Math.toRadians(llcam.AprilTagGoalYaw(goal)));
            double AO = llcam.AprilTagGoalDistance(goal) * Math.sin(Math.toRadians(llcam.AprilTagGoalYaw(goal)));
            double PO = LO + pivotOffset;
            double correctAngle = Math.toDegrees(Math.atan2(AO, PO));

            target = tracker.getCurrentPosition() + correctAngle * ticks_per_rev * gearRatio / 360;

            saved_yaw = llcam.AprilTagGoalYaw(goal);
            saved_output = tracker.getCurrentPosition();

            second_target = saved_output + saved_yaw * ticks_per_rev * gearRatio / 360;
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
        this.target = target * ticks_per_rev * gearRatio / 360;
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
            if(timer.seconds() - timp < trust_time) target = (double)tracker.getCurrentPosition() + (estimatedTx * ticks_per_rev * gearRatio / 360.0);
            else target = tracker.getCurrentPosition();

        // 5. Cleanup
        lastLoopTime = currentTime;
        lastTrackerPos = tracker.getCurrentPosition();

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

        double targetTurretAngle = (absoluteAngleToTarget - robotHeading);

        target = targetTurretAngle * ticks_per_rev * gearRatio / 360;

    }

    public double getCounterRotationPower() {

        double robotAngularVel = pinpoint.getVelocity().getHeading();

        double ticksPerSecondNeeded = -robotAngularVel * (ticks_per_rev / (2 * Math.PI)) * gearRatio;

        return ticksPerSecondNeeded * Kv;
    }

    public void Track(LimeLight.Goal goal)
    {
        //sortare.readyToShoot = localizare.robotInLaunchZone();

        if(enable_wrapAround)
        {
            zone_target=0;
            zone_tracker=0;

            double trackerPos = (int)tracker.getCurrentPosition() + 180 * ticks_per_rev * gearRatio;

            while (trackerPos >= ticks_per_rev * gearRatio) {trackerPos = trackerPos - ticks_per_rev* gearRatio; zone_tracker++; }
            while (trackerPos < 0) { trackerPos = trackerPos + ticks_per_rev* gearRatio; zone_tracker--; }

            while (target >= ticks_per_rev* gearRatio) { target = target - ticks_per_rev* gearRatio; zone_target++; }
            while (target < 0) { target = target + ticks_per_rev* gearRatio; zone_target--; }

            if(Math.abs(target) * 360 / (ticks_per_rev * gearRatio) > dead_zone && Math.abs(target) * 360 / (ticks_per_rev * gearRatio) < 360 - dead_zone && zone_target != zone_tracker)
            {
                wraping = true;
                pid.setPID(Kp_wrap, Kd_wrap, Ki_wrap);
                pid.reset();
            }
            if((Math.abs(target * 360 / (ticks_per_rev * gearRatio) - (double)tracker.getCurrentPosition() / (ticks_per_rev * gearRatio) * 360) < 180 && llcam.AprilTagVisible(goal)) || Math.abs(target * 360 / (ticks_per_rev * gearRatio) - (double)tracker.getCurrentPosition() / (ticks_per_rev * gearRatio) * 360) < 5)
            {
                wraping = false;
                pid.setPID(Kp, Kd, Ki);
                pid.reset();
            }

            telemetry.addData("target", target * 360 / (ticks_per_rev * gearRatio));
            telemetry.addData("current2", trackerPos / (ticks_per_rev * gearRatio) * 360);
        }

        if(Math.abs(target) * 360 / (ticks_per_rev * gearRatio)> dead_zone && Math.abs(target) * 360 / (ticks_per_rev * gearRatio)< 360-dead_zone)
            pid.setSetPoint(target);

        if (!pid.atSetPoint()) {
            double output = pid.calculate(tracker.getCurrentPosition());
            double counterPower = getCounterRotationPower();

            double true_output;

            if(enabled_decoupling) true_output = output + counterPower;
            else true_output = output;

            tracker.setPower(true_output);
        } else {
            tracker.setPower(0);
            pid.reset();
        }

        pid.setPID(Kp, Ki, Kd);
        pid.setTolerance(tolerance);
        telemetry.addData("zone tracker", zone_tracker);
        telemetry.addData("zone target", zone_target);
        telemetry.addData("wraping", wraping);
        telemetry.addData("pid setpoint", pid.getSetPoint());
    }

    public TrackV2(HardwareMap hw, Telemetry tele)
    {
        telemetry = tele;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        tracker = hw.get(DcMotorEx.class, "tracker");
        tracker.setDirection(DcMotorSimple.Direction.REVERSE);
        tracker.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        llcam = new LimeLight(hw, tele);
        localizare = new Localizare_In_Timp_Real(pinpoint);

        llkf = new LimelightKalmanFilter(LOOP_DT, Q_ANGLE, Q_VELOCITY, R_TX);

        pid = new PIDController(Kp, Ki, Kd);

        sortare = new Sortare(hw, tele);
    }

    public TrackV2(HardwareMap hw, Telemetry tele, PinpointLocalizer pinpoint)
    {
        telemetry = tele;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        this.pinpoint = pinpoint;

        tracker = hw.get(DcMotorEx.class, "tracker");
        tracker.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        llcam = new LimeLight(hw, tele);
        localizare = new Localizare_In_Timp_Real(pinpoint);

        llkf = new LimelightKalmanFilter(LOOP_DT, Q_ANGLE, Q_VELOCITY, R_TX);

        pid = new PIDController(Kp, Ki, Kd);

        //sortare = new Sortare(hw, tele);
    }
}

