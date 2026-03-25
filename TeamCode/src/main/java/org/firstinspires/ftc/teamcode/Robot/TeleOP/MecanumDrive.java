package org.firstinspires.ftc.teamcode.Robot.TeleOP;

import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight.Goal;

public class MecanumDrive {

    Telemetry telemetry;
    PinpointLocalizer pinpoint;
    DcMotorEx MotorLF, MotorLB, MotorRF, MotorRB;
    double maxSpeed = 1;

    public double allianceOffset;
    public void Drive(double forward, double strafe, double rotate)
    {

        /**

         double maxPower = 1.0;
         double maxSpeed = 1.0;

         double sin = Math.sin(theta - Math.PI/4);
         double cos = Math.cos(theta - Math.PI/4);
         maxPower = Math.max(Math.abs(sin), Math.abs(cos));

         double lfPower = power * cos/maxPower + rotate;
         double rfPower = power * sin/maxPower - rotate;
         double lbPower = power * sin/maxPower + rotate;
         double rbPower = power * cos/maxPower - rotate;

         if(power + Math.abs(maxPower) > 1)
         {
         lfPower /= power + rotate;
         rfPower /= power + rotate;
         lbPower /= power + rotate;
         rbPower /= power + rotate;
         }

         telemetry.addData("FieldOrientated", FieldOrientated);
         telemetry.addData("LF power", lfPower);
         telemetry.addData("RF power", rfPower);
         telemetry.addData("LB power", lbPower);
         telemetry.addData("RB power", rbPower);
         telemetry.addData("Max Power", maxPower);
         **/

        double lfPower = forward + strafe + rotate;
        double rfPower = forward - strafe - rotate;
        double lbPower = forward - strafe + rotate;
        double rbPower = forward + strafe - rotate;

        double maxPower = 1.0;

        maxPower = Math.max(maxPower, Math.abs(lfPower));
        maxPower = Math.max(maxPower, Math.abs(rfPower));
        maxPower = Math.max(maxPower, Math.abs(lbPower));
        maxPower = Math.max(maxPower, Math.abs(rbPower));

        MotorLF.setPower(maxSpeed * lfPower / maxPower);
        MotorRF.setPower(maxSpeed * rfPower / maxPower);
        MotorLB.setPower(maxSpeed * lbPower / maxPower);
        MotorRB.setPower(maxSpeed * rbPower / maxPower);


        telemetry.addData("LF power", maxSpeed * lfPower / maxPower);
        telemetry.addData("RF power", maxSpeed * rfPower / maxPower);
        telemetry.addData("LB power", maxSpeed * lbPower / maxPower);
        telemetry.addData("RB power", maxSpeed * rbPower / maxPower);
        telemetry.addData("Max Power", maxPower);
    }

    public void DriveFieldOrientated(double forward, double strafe, double rotate)
    {
        double theta = Math.atan2(forward, strafe);
        double raza = Math.hypot(forward, strafe);
        double yaw = AngleUnit.normalizeRadians(pinpoint.getTotalHeading() + allianceOffset);

        theta = AngleUnit.normalizeRadians(theta - yaw);

        double newForward = raza * Math.sin(theta);
        double newStrafe = raza * Math.cos(theta);

        Drive(newForward, newStrafe, rotate);
    }

    public void setMaxSpeed(double speed)
    {
        maxSpeed = speed;
    }

    public MecanumDrive(HardwareMap hardwareMap, Telemetry tel, PinpointLocalizer pinpoint, Goal alliance)
    {
        telemetry = tel;
        /** ______CONSTRUCTOR______ **/
        this.pinpoint = pinpoint;

        if(alliance == Goal.BLUE)
            allianceOffset = Math.toRadians(-90);

        else if(alliance == Goal.RED) allianceOffset = Math.toRadians(90);

//        if(memorie.pozitieRobot!=null)
//            allianceOffset-= memorie.pozitieRobot.getHeading();

        MotorRB = hardwareMap.get(DcMotorEx.class, "br");
        MotorRF = hardwareMap.get(DcMotorEx.class, "fr");
        MotorLB = hardwareMap.get(DcMotorEx.class, "bl");
        MotorLF = hardwareMap.get(DcMotorEx.class, "fl");

        /** ______INITIALIZAREA______ **/

        MotorLB.setDirection(DcMotorEx.Direction.REVERSE);
        MotorLF.setDirection(DcMotorEx.Direction.REVERSE);
        MotorRB.setDirection(DcMotorEx.Direction.FORWARD);
        MotorRF.setDirection(DcMotorEx.Direction.REVERSE);

        MotorRB.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        MotorRF.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        MotorLB.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        MotorLF.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        MotorRB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorRF.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorLB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorLF.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public MecanumDrive(HardwareMap hardwareMap, Telemetry tel, PinpointLocalizer pinpoint)
    {
        telemetry = tel;
        /** ______CONSTRUCTOR______ **/
        this.pinpoint = pinpoint;

        MotorRB = hardwareMap.get(DcMotorEx.class, "br");
        MotorRF = hardwareMap.get(DcMotorEx.class, "fr");
        MotorLB = hardwareMap.get(DcMotorEx.class, "bl");
        MotorLF = hardwareMap.get(DcMotorEx.class, "fl");

        /** ______INITIALIZAREA______ **/

        MotorLB.setDirection(DcMotorEx.Direction.REVERSE);
        MotorLF.setDirection(DcMotorEx.Direction.REVERSE);
        MotorRB.setDirection(DcMotorEx.Direction.FORWARD);
        MotorRF.setDirection(DcMotorEx.Direction.REVERSE);

        MotorRB.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        MotorRF.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        MotorLB.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        MotorLF.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        MotorRB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorRF.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorLB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorLF.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public MecanumDrive(HardwareMap hardwareMap, Telemetry tel)
    {
        telemetry = tel;
        /** ______CONSTRUCTOR______ **/

        MotorRB = hardwareMap.get(DcMotorEx.class, "br");
        MotorRF = hardwareMap.get(DcMotorEx.class, "fr");
        MotorLB = hardwareMap.get(DcMotorEx.class, "bl");
        MotorLF = hardwareMap.get(DcMotorEx.class, "fl");

        /** ______INITIALIZAREA______ **/

        MotorLB.setDirection(DcMotorEx.Direction.REVERSE);
        MotorLF.setDirection(DcMotorEx.Direction.REVERSE);
        MotorRB.setDirection(DcMotorEx.Direction.FORWARD);
        MotorRF.setDirection(DcMotorEx.Direction.REVERSE);

        MotorRB.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        MotorRF.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        MotorLB.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        MotorLF.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        MotorRB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorRF.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorLB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        MotorLF.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }
}
