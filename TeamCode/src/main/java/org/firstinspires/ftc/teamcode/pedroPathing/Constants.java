package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(9)
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.008025,0,0.00219,0.6,0.08))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.001,0.0000002,0,0.6,0.01))
            .translationalPIDFCoefficients(new PIDFCoefficients(0.48, 0, 0.047, 0.0635))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.05, 0, 0.035, 0.098))
            .headingPIDFCoefficients(new PIDFCoefficients(0.8, 0, 0.1, 0.07))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(0.5, 0, 0.033, 0.03))
            .useSecondaryTranslationalPIDF(true)
            .useSecondaryHeadingPIDF(true)
            .useSecondaryDrivePIDF(true);

    public static MecanumConstants driveConstants = new MecanumConstants()
//            .xVelocity(81.21791521207555)
//            .yVelocity(50.65569059116633)
            .maxPower(1)
            .rightFrontMotorName("fr")
            .rightRearMotorName("br")
            .leftRearMotorName("bl")
            .leftFrontMotorName("fl")
            .leftFrontMotorDirection(DcMotorEx.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorEx.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorEx.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorEx.Direction.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 8, 1);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-8.35)
            .strafePodX(-10.1)
            //.forwardPodY(-13)
            //.strafePodX(-8.7)
            .distanceUnit(DistanceUnit.CM)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);



    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}