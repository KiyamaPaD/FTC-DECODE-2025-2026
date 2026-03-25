package org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta;

/// innebunesc
public class LimelightKalmanFilter {
    private double[][] x = new double[2][1];

    private double[][] P = new double[2][2];

    private double[][] A = new double[2][2];

    private double[][] B = new double[2][1];

    private final double[][] H = new double[][]{{1.0, 0.0}};

    private double[][] Q = new double[2][2];

    private double[][] R = new double[1][1];

    private final double dt;

    public LimelightKalmanFilter(double dt, double processNoiseAngle, double processNoiseVelocity, double measurementNoiseTx) {
        this.dt = dt;


        x[0][0] = 0.0;
        x[1][0] = 0.0;

        P[0][0] = 1000.0;
        P[1][1] = 1000.0;

        A[0][0] = 1.0; A[0][1] = dt;
        A[1][0] = 0.0; A[1][1] = 1.0;

        B[0][0] = 0.0;
        B[1][0] = 1.0;

        Q[0][0] = processNoiseAngle;
        Q[1][1] = processNoiseVelocity;

        R[0][0] = measurementNoiseTx;
    }

    public double update(double limelightTx, double commandedAngularAcceleration, boolean targetVisible) {

        double[][] x_k_minus = Matrix.add(
                Matrix.multiply(A, x),
                Matrix.multiply(B, new double[][]{{commandedAngularAcceleration}})
        );

        double[][] AP = Matrix.multiply(A, P);
        double[][] P_k_minus = Matrix.add(
                Matrix.multiply(AP, Matrix.transpose(A)),
                Q
        );

        if (targetVisible) {

            double[][] H_P = Matrix.multiply(H, P_k_minus);
            double[][] S = Matrix.add(Matrix.multiply(H_P, Matrix.transpose(H)), R);
            double S_inv = 1.0 / S[0][0];
            double[][] P_HT = Matrix.multiply(P_k_minus, Matrix.transpose(H));
            double[][] K = Matrix.scalarMultiply(P_HT, S_inv);

            double[][] H_x = Matrix.multiply(H, x_k_minus);
            double residual = limelightTx - H_x[0][0];

            x = Matrix.add(x_k_minus, Matrix.scalarMultiply(K, residual));

            double[][] KH = Matrix.multiply(K, H);
            double[][] I_minus_KH = new double[2][2];
            I_minus_KH[0][0] = 1.0 - KH[0][0]; I_minus_KH[0][1] = 0.0 - KH[0][1];
            I_minus_KH[1][0] = 0.0 - KH[1][0]; I_minus_KH[1][1] = 1.0 - KH[1][1];

            P = Matrix.multiply(I_minus_KH, P_k_minus);

        } else {
            x = x_k_minus;
            P = P_k_minus;
            x[1][0] = x[1][0] * 0.95;
        }

        return x[0][0];
    }
    public void setProcessNoise(double qAngle, double qVelocity) {
        if (qAngle > 0 && qVelocity > 0) {
            Q[0][0] = qAngle;
            Q[1][1] = qVelocity;
        }
    }
    public void setMeasurementNoise(double rTx) {
        if (rTx > 0) {
            R[0][0] = rTx;
        }
    }

    public double getEstimatedVelocity()
    {
        return x[1][0];
    }
}
