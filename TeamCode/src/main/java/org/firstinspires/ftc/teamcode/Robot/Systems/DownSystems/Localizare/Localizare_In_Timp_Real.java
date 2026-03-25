package org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Localizare;

import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;

public class Localizare_In_Timp_Real {

    /// LAUNCH ZONE MARE
    private final Pose blue_corner = new Pose(0, 144);
    private final Pose red_corner = new Pose(144, 144);
    private final Pose middle = new Pose(72, 72);

    /// LAUNCH ZONE MIC
    private final Pose colt_SJ = new Pose(48, 0);
    private final Pose colt_DJ = new Pose(96, 0);
    private final Pose colt_Sus = new Pose(72, 24);

    private final Pose blue_basket = new Pose(14.48, 128.75);
    private final Pose red_basket = new Pose(130, 128.75);

    public static double robot_length_x = 44.5, robot_length_y = 43.8, turret_offset = 4.5 / 2.54;

    public Pose LF, LB, RF, RB, turret;

    PinpointLocalizer pinpoint;

    public Localizare_In_Timp_Real(PinpointLocalizer pinpoint)
    {
        this.pinpoint = pinpoint;
    }

    public boolean InLaunchZone(Pose pozitie)
    {
        return InBigLaunchZone(pozitie) || InSmallLaunchZone(pozitie);
    }

    double sign(Pose p1, Pose p2, Pose p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

    boolean PointInTriangle(Pose pt, Pose v1, Pose v2, Pose v3) {
        double d1, d2, d3;
        boolean has_neg, has_pos;

        d1 = sign(pt, v1, v2);
        d2 = sign(pt, v2, v3);
        d3 = sign(pt, v3, v1);

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

    public boolean InBigLaunchZone(Pose pozitie) {
        return PointInTriangle(pozitie, blue_corner, red_corner, middle);
    }

    public boolean InSmallLaunchZone(Pose pozitie) {
        return PointInTriangle(pozitie, colt_SJ, colt_Sus, colt_DJ);
    }

    public boolean robotInLaunchZone()
    {
        Pose currentPose = pinpoint.getPose();
        double x = currentPose.getX();
        double y = currentPose.getY();
        double heading = currentPose.getHeading();

        double dx = (robot_length_x / 2.54) / 2.0;
        double dy = (robot_length_y / 2.54) / 2.0;

        double cos = Math.cos(heading);
        double sin = Math.sin(heading);
        LF = new Pose(x + (dx * cos - dy * sin), y + (dx * sin + dy * cos));
        RF = new Pose(x + (dx * cos - (-dy) * sin), y + (dx * sin + (-dy) * cos));
        LB = new Pose(x + (-dx * cos - dy * sin), y + (-dx * sin + dy * cos));
        RB = new Pose(x + (-dx * cos - (-dy) * sin), y + (-dx * sin + (-dy) * cos));

        return InLaunchZone(LF) || InLaunchZone(RF) || InLaunchZone(LB) || InLaunchZone(RB);
    }

    public void updateTurretPose() {
        Pose currentPose = pinpoint.getPose();
        double x = currentPose.getX();
        double y = currentPose.getY();
        double heading = currentPose.getHeading();

        double cos = Math.cos(heading);
        double sin = Math.sin(heading);

        double turretX = x + (turret_offset * cos);
        double turretY = y + (turret_offset * sin);

        turret = new Pose(turretX, turretY, heading);
    }

    public double distanceFromBasket(LimeLight.Goal goal)
    {
        updateTurretPose();

        Pose targetBasket;
        if (goal == LimeLight.Goal.BLUE) {
            targetBasket = blue_basket;
        } else {
            targetBasket = red_basket;
        }

        double dx = targetBasket.getX() - turret.getX();
        double dy = targetBasket.getY() - turret.getY();

        return Math.sqrt(dx * dx + dy * dy) * 2.54;
    }

    public void autoPark()
    {
        /// numai avem timp :(
    }
}
