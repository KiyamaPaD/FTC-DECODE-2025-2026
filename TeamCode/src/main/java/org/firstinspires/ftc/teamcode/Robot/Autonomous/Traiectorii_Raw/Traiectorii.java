package org.firstinspires.ftc.teamcode.Traj;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class Traiectorii {

    private final Follower follower;

    public Traiectorii(Follower follower) {
        this.follower = follower;
    }

    /**
        *****     *        *   *    *****
        *    *    *        *   *    *
        *****     *        *   *    *****
        *    *    *        *   *    *
        *****     *****    *****    *****
     */

    public PathChain LeftArtifact, ShootLeft, PrepareZone, ShootZone, Park;

    public void pathLow9Blue() {
        LeftArtifact = follower.pathBuilder().addPath(
                          new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        PrepareZone = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootZone = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        Park = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57.336, 18.318),

                                new Pose(49.491, 18.255)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(121))

                .build();
    }

    public PathChain LeftArtifact1, ShootLeft1, PrepareZone1, ShootZone1, GateZone1, GateShoot1, Park1;

    public void pathLow12GateBlue() {
        LeftArtifact1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        GateZone1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(73.482, 29.786),
                                new Pose(11.622, 14.297)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(180))

                .build();

        GateShoot1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.622, 14.297),

                                new Pose(55.418, 17.309)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        Park1 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(55.418, 17.309),

                                new Pose(49.491, 18.255)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(121))

                .build();
    }

    public PathChain LeftArtifact2, ShootLeft2, PrepareZone2, ShootZone2, MiddleZone2, MiddleShoot2, Park2;

    public void pathLow12MiddleBlue() {
        LeftArtifact2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        MiddleZone2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(82.245, 37.127),
                                new Pose(26.423, 59.897)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(26.423, 59.897),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact3, ShootLeft3, PrepareZone3, ShootZone3, MiddleZone3, GateOpen3, MiddleShoot3, Park3;

    public void pathLow12MiddleOGBlue() {
        LeftArtifact3 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone3 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        MiddleZone3 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(82.245, 37.127),
                                new Pose(25.328, 60.553)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateOpen3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.328, 60.553),

                                new Pose(16.553, 63.526)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(16.553, 63.526),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain MiddleArtifacts4, OpenGate4, MiddleShoot4, UpArtifacts4, UpShoot4, Park4;

    public void pathLow12MiddleUpOGFARALowArtifactsBlue() {
        MiddleArtifacts4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(56.000, 8.000),
                                new Pose(59.252, 61.894),
                                new Pose(24.267, 60.073)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))

                .build();

        OpenGate4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.267, 60.073),

                                new Pose(16.587, 62.915)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(16.587, 62.915),

                                new Pose(58.736, 73.578)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpArtifacts4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.736, 73.578),

                                new Pose(24.237, 85.003)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpShoot4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.237, 85.003),

                                new Pose(59.526, 74.407)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(59.526, 74.407),

                                new Pose(42.900, 78.529)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact5, ShootLeft5, MiddleZone5, MiddleShoot5, UpArtifacts5, UpShoot5, Park5;

    public void pathLow12UpBlue() {
        LeftArtifact5 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        MiddleZone5 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(82.245, 37.127),
                                new Pose(26.423, 59.897)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        MiddleShoot5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(26.423, 59.897),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpArtifacts5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(25.331, 83.465)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpShoot5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.331, 83.465),

                                new Pose(60.271, 79.429)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park5 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.271, 79.429),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact6, ShootLeft6, PrepareZone6, ShootZone6, GateZone6, GateShoot6, GateZone6_2, GateShoot6_2, Park6;

    public void pathLow15GateDoubleBlue() {
        LeftArtifact6 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone6 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        GateZone6 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(73.482, 29.786),
                                new Pose(10.309, 13.641)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(180))

                .build();

        GateShoot6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.309, 13.641),

                                new Pose(55.418, 17.309)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        GateZone6_2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(55.418, 17.309),

                                new Pose(10.994, 13.736)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(180))

                .build();

        GateShoot6_2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.994, 13.736),

                                new Pose(64.517, 17.009)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park6 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(64.517, 17.009),

                                new Pose(47.000, 18.255)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact7, ShootLeft7, PrepareZone7, ShootZone7, MiddleZone7, MiddleShoot7, UpArtifacts7, UpShoot7, Park7;

    public void pathLow15MiddleUpBlue() {
        LeftArtifact7 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone7 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        MiddleZone7 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(82.245, 37.127),
                                new Pose(26.423, 59.897)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(26.423, 59.897),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpArtifacts7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(21.489, 83.903)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpShoot7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.489, 83.903),

                                new Pose(60.809, 83.006)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park7 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.809, 83.006),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact8, ShootLeft8, PrepareZone8, ShootZone8, MiddleZone8, GateOpen8, MiddleShoot8, UpArtifacts8, UpShoot8, Park8;
    public void pathLow15MiddleUpOGBlue()
    {
        LeftArtifact8 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone8 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        MiddleZone8 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(82.245, 37.127),
                                new Pose(26.423, 59.897)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateOpen8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(26.423, 59.897),

                                new Pose(16.426, 64.003)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(16.426, 64.003),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpArtifacts8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(21.489, 83.903)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpShoot8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.489, 83.903),

                                new Pose(60.809, 83.006)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park8 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.809, 83.006),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain Preload9, LeftArtifacts9, GateZone9, ShootLeft9, MiddleArtifacts9, ShootMiddle9, Park9;
    public void pathUp9Blue()
    {
        Preload9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.782, 123.145),

                                new Pose(44.000, 102.091)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(180))

                .build();

        LeftArtifacts9 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(44.000, 102.091),
                                new Pose(49.064, 83.791),
                                new Pose(20.164, 84.145)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.164, 84.145),

                                new Pose(15.618, 77.455)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootLeft9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.618, 77.455),

                                new Pose(43.600, 101.836)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        MiddleArtifacts9 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(43.600, 101.836),
                                new Pose(70.291, 61.945),
                                new Pose(23.527, 62.709)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                .build();

        ShootMiddle9 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(23.527, 62.709),
                                new Pose(79.445, 68.345),
                                new Pose(57.109, 78.818)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park9 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57.109, 78.818),

                                new Pose(40.655, 78.636)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain Preload10, LeftArtifacts10, GateZone10, ShootLeft10, MiddleArtifacts10, ShootMiddle10, LowArtifacts10, LowShoot10, Park10;
    public void pathUp12LowBlue()
    {
        Preload10 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.782, 123.145),

                                new Pose(44.000, 102.091)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(180))

                .build();

        LeftArtifacts10 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(44.000, 102.091),
                                new Pose(49.064, 83.791),
                                new Pose(20.164, 84.145)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone10 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.164, 84.145),

                                new Pose(15.618, 77.455)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootLeft10 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.618, 77.455),

                                new Pose(43.600, 101.836)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        MiddleArtifacts10 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(43.600, 101.836),
                                new Pose(70.291, 61.945),
                                new Pose(23.527, 62.709)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                .build();

        ShootMiddle10 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(23.527, 62.709),
                                new Pose(79.445, 68.345),
                                new Pose(57.109, 78.818)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        LowArtifacts10 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.109, 78.818),
                                new Pose(85.647, 31.425),
                                new Pose(24.887, 35.825)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        LowShoot10 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.887, 35.825),

                                new Pose(56.206, 77.845)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park10 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.206, 77.845),

                                new Pose(41.397, 76.781)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain Preload11, LeftArtifacts11, GateZone11, ShootLeft11, MiddleArtifacts11, ShootMiddle11, GateZone11_2, GateShoot11, Park11;
    public void pathUp12LowParkLowBlue()
    {
        Preload11 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.782, 123.145),

                                new Pose(44.000, 102.091)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(180))

                .build();

        LeftArtifacts11 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(44.000, 102.091),
                                new Pose(49.064, 83.791),
                                new Pose(20.164, 84.145)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone11 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.164, 84.145),

                                new Pose(15.618, 77.455)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootLeft11 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.618, 77.455),

                                new Pose(43.600, 101.836)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        MiddleArtifacts11 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(43.600, 101.836),
                                new Pose(70.291, 61.945),
                                new Pose(23.527, 62.709)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                .build();

        ShootMiddle11 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(23.527, 62.709),
                                new Pose(79.445, 68.345),
                                new Pose(71.553, 22.575)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone11_2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(71.553, 22.575),
                                new Pose(74.061, 43.779),
                                new Pose(23.356, 36.125)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateShoot11 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(23.356, 36.125),

                                new Pose(70.941, 22.448)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park11 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(70.941, 22.448),

                                new Pose(60.059, 33.462)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain Preload12, LeftArtifacts12, GateZone12, ShootLeft12, MiddleArtifacts12, ShootMiddle12, GateZone12_2, GateShoot12, HumanZone12, HumanShoot12, Park12;
    public void pathUp15HumanZoneParkLowBlue()
    {
        Preload12 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.782, 123.145),

                                new Pose(44.000, 102.091)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(180))

                .build();

        LeftArtifacts12 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(44.000, 102.091),
                                new Pose(49.064, 83.791),
                                new Pose(20.164, 84.145)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone12 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.164, 84.145),

                                new Pose(15.618, 77.455)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootLeft12 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.618, 77.455),

                                new Pose(43.600, 101.836)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        MiddleArtifacts12 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(43.600, 101.836),
                                new Pose(70.291, 61.945),
                                new Pose(23.527, 62.709)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                .build();

        ShootMiddle12 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(23.527, 62.709),

                                new Pose(71.553, 22.575)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone12_2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.553, 22.575),

                                new Pose(23.356, 36.125)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateShoot12 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(23.356, 36.125),

                                new Pose(70.941, 22.448)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        HumanZone12 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(70.941, 22.448),

                                new Pose(11.395, 9.824)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        HumanShoot12 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.395, 9.824),

                                new Pose(71.562, 22.350)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park12 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.562, 22.350),

                                new Pose(71.001, 34.200)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    /**
        *****     ****   ****
        *    *    *      *   *
        *****     ****   *    *
        *   **    *      *   *
        *    **   ****   ****
     */

    public PathChain LeftArtifact13, ShootLeft13, PrepareZone13, ShootZone13, Park13;

    public void pathLow9Red() {
        LeftArtifact13 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft13 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        PrepareZone13 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootZone13 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        Park13 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57.336, 18.318),

                                new Pose(49.491, 18.255)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(121))

                .build();
    }

    public PathChain LeftArtifact14, ShootLeft14, PrepareZone14, ShootZone14, GateZone14, GateShoot14, Park14;

    public void pathLow12GateRed() {
        LeftArtifact14 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft14 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone14 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone14 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        GateZone14 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(73.482, 29.786),
                                new Pose(11.622, 14.297)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(180))

                .build();

        GateShoot14 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.622, 14.297),

                                new Pose(55.418, 17.309)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        Park14 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(55.418, 17.309),

                                new Pose(49.491, 18.255)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(121))

                .build();
    }

    public PathChain LeftArtifact15, ShootLeft15, PrepareZone15, ShootZone15, MiddleZone15, MiddleShoot15, Park15;

    public void pathLow12MiddleRed() {
        LeftArtifact15 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft15 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone15 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone15 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        MiddleZone15 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(82.245, 37.127),
                                new Pose(26.423, 59.897)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot15 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(26.423, 59.897),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park15 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact16, ShootLeft16, PrepareZone16, ShootZone16, MiddleZone16, GateOpen16, MiddleShoot16, Park16;

    public void pathLow12MiddleOGRed() {
        LeftArtifact16 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft16 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone16 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone16 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        MiddleZone16 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(82.245, 37.127),
                                new Pose(25.328, 60.553)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateOpen16 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.328, 60.553),

                                new Pose(16.553, 63.526)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot16 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(16.553, 63.526),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park16 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain MiddleArtifacts17, OpenGate17, MiddleShoot17, UpArtifacts17, UpShoot17, Park17;

    public void pathLow12MiddleUpOGFARALowArtifactsRed() {
        MiddleArtifacts17 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(56.000, 8.000),
                                new Pose(59.252, 61.894),
                                new Pose(24.267, 60.073)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))

                .build();

        OpenGate17 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.267, 60.073),

                                new Pose(16.587, 62.915)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot17 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(16.587, 62.915),

                                new Pose(58.736, 73.578)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpArtifacts17 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(58.736, 73.578),

                                new Pose(24.237, 85.003)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpShoot17 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.237, 85.003),

                                new Pose(59.526, 74.407)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park17 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(59.526, 74.407),

                                new Pose(42.900, 78.529)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact18, ShootLeft18, MiddleZone18, MiddleShoot18, UpArtifacts18, UpShoot18, Park18;

    public void pathLow12UpRed() {
        LeftArtifact18 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft18 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        MiddleZone18 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(82.245, 37.127),
                                new Pose(26.423, 59.897)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        MiddleShoot18 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(26.423, 59.897),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpArtifacts18 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(25.331, 83.465)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpShoot18 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.331, 83.465),

                                new Pose(60.271, 79.429)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park18 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.271, 79.429),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact19, ShootLeft19, PrepareZone19, ShootZone19, GateZone19, GateShoot19, GateZone19_2, GateShoot19_2, Park19;

    public void pathLow15GateDoubleRed() {
        LeftArtifact19 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft19 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone19 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone19 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        GateZone19 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(73.482, 29.786),
                                new Pose(10.309, 13.641)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(180))

                .build();

        GateShoot19 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.309, 13.641),

                                new Pose(55.418, 17.309)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        GateZone19_2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(55.418, 17.309),

                                new Pose(10.994, 13.736)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(121), Math.toRadians(180))

                .build();

        GateShoot19_2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.994, 13.736),

                                new Pose(64.517, 17.009)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park19 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(64.517, 17.009),

                                new Pose(47.000, 18.255)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact20, ShootLeft20, PrepareZone20, ShootZone20, MiddleZone20, MiddleShoot20, UpArtifacts20, UpShoot20, Park20;

    public void pathLow15MiddleUpRed() {
        LeftArtifact20 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft20 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone20 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone20 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        MiddleZone20 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(82.245, 37.127),
                                new Pose(26.423, 59.897)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot20 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(26.423, 59.897),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpArtifacts20 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(21.489, 83.903)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpShoot20 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.489, 83.903),

                                new Pose(60.809, 83.006)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park20 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.809, 83.006),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain LeftArtifact21, ShootLeft21, PrepareZone21, ShootZone21, MiddleZone21, GateOpen21, MiddleShoot21, UpArtifacts21, UpShoot21, Park21;
    public void pathLow15MiddleUpOGRed()
    {
        LeftArtifact21 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.87339043934789, 8.810886985355094),
                                new Pose(58.236, 34.018),
                                new Pose(25.509, 35.764)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        ShootLeft21 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(25.509, 35.764),

                                new Pose(58.909, 20.345)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(116))
                .build();

        PrepareZone21 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(58.909, 20.345),
                                new Pose(90.545, 18.509),
                                new Pose(10.164, 8.855)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(116), Math.toRadians(180))

                .build();

        ShootZone21 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(10.164, 8.855),

                                new Pose(57.336, 18.318)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(121))

                .build();

        MiddleZone21 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.336, 18.318),
                                new Pose(82.245, 37.127),
                                new Pose(26.423, 59.897)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateOpen21 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(26.423, 59.897),

                                new Pose(16.426, 64.003)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        MiddleShoot21 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(16.426, 64.003),

                                new Pose(61.289, 82.660)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpArtifacts21 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.289, 82.660),

                                new Pose(21.489, 83.903)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        UpShoot21 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(21.489, 83.903),

                                new Pose(60.809, 83.006)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park21 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(60.809, 83.006),

                                new Pose(41.326, 82.275)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain Preload22, LeftArtifacts22, GateZone22, ShootLeft22, MiddleArtifacts22, ShootMiddle22, Park22;
    public void pathUp9Red()
    {
        Preload22 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.782, 123.145),

                                new Pose(44.000, 102.091)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(180))

                .build();

        LeftArtifacts22 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(44.000, 102.091),
                                new Pose(49.064, 83.791),
                                new Pose(20.164, 84.145)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone22 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.164, 84.145),

                                new Pose(15.618, 77.455)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootLeft22 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.618, 77.455),

                                new Pose(43.600, 101.836)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        MiddleArtifacts22 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(43.600, 101.836),
                                new Pose(70.291, 61.945),
                                new Pose(23.527, 62.709)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                .build();

        ShootMiddle22 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(23.527, 62.709),
                                new Pose(79.445, 68.345),
                                new Pose(57.109, 78.818)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park22 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(57.109, 78.818),

                                new Pose(40.655, 78.636)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain Preload23, LeftArtifacts23, GateZone23, ShootLeft23, MiddleArtifacts23, ShootMiddle23, LowArtifacts23, LowShoot23, Park23;
    public void pathUp12LowRed()
    {
        Preload23 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.782, 123.145),

                                new Pose(44.000, 102.091)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(180))

                .build();

        LeftArtifacts23 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(44.000, 102.091),
                                new Pose(49.064, 83.791),
                                new Pose(20.164, 84.145)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone23 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.164, 84.145),

                                new Pose(15.618, 77.455)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootLeft23= follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.618, 77.455),

                                new Pose(43.600, 101.836)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        MiddleArtifacts23 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(43.600, 101.836),
                                new Pose(70.291, 61.945),
                                new Pose(23.527, 62.709)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                .build();

        ShootMiddle23 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(23.527, 62.709),
                                new Pose(79.445, 68.345),
                                new Pose(57.109, 78.818)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        LowArtifacts23 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(57.109, 78.818),
                                new Pose(85.647, 31.425),
                                new Pose(24.887, 35.825)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        LowShoot23 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(24.887, 35.825),

                                new Pose(56.206, 77.845)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park23 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.206, 77.845),

                                new Pose(41.397, 76.781)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain Preload24, LeftArtifacts24, GateZone24, ShootLeft24, MiddleArtifacts24, ShootMiddle24, GateZone24_2, GateShoot24, Park24;
    public void pathUp12LowParkLowRed()
    {
        Preload24 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.782, 123.145),

                                new Pose(44.000, 102.091)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(180))

                .build();

        LeftArtifacts24 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(44.000, 102.091),
                                new Pose(49.064, 83.791),
                                new Pose(20.164, 84.145)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone24 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.164, 84.145),

                                new Pose(15.618, 77.455)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootLeft24 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.618, 77.455),

                                new Pose(43.600, 101.836)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        MiddleArtifacts24 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(43.600, 101.836),
                                new Pose(70.291, 61.945),
                                new Pose(23.527, 62.709)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                .build();

        ShootMiddle24 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(23.527, 62.709),
                                new Pose(79.445, 68.345),
                                new Pose(71.553, 22.575)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone24_2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(71.553, 22.575),
                                new Pose(74.061, 43.779),
                                new Pose(23.356, 36.125)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateShoot24 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(23.356, 36.125),

                                new Pose(70.941, 22.448)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park24 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(70.941, 22.448),

                                new Pose(60.059, 33.462)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }

    public PathChain Preload25, LeftArtifacts25, GateZone25, ShootLeft25, MiddleArtifacts25, ShootMiddle25, GateZone25_2, GateShoot25, HumanZone25, HumanShoot25, Park25;
    public void pathUp15HumanZoneParkLowRed()
    {
        Preload25 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.782, 123.145),

                                new Pose(44.000, 102.091)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(-37), Math.toRadians(180))

                .build();

        LeftArtifacts25 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(44.000, 102.091),
                                new Pose(49.064, 83.791),
                                new Pose(20.164, 84.145)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone25 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(20.164, 84.145),

                                new Pose(15.618, 77.455)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        ShootLeft25 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(15.618, 77.455),

                                new Pose(43.600, 101.836)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                .build();

        MiddleArtifacts25 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(43.600, 101.836),
                                new Pose(70.291, 61.945),
                                new Pose(23.527, 62.709)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))

                .build();

        ShootMiddle25 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(23.527, 62.709),

                                new Pose(71.553, 22.575)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateZone25_2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.553, 22.575),

                                new Pose(23.356, 36.125)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        GateShoot25 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(23.356, 36.125),

                                new Pose(70.941, 22.448)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        HumanZone25 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(70.941, 22.448),

                                new Pose(11.395, 9.824)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        HumanShoot25 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.395, 9.824),

                                new Pose(71.562, 22.350)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();

        Park25 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(71.562, 22.350),

                                new Pose(71.001, 34.200)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                .build();
    }
}//Ma fut in ele traiectorii