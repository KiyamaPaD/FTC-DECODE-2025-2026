package org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Tureta;

public class Matrix {
    public static double[][] multiply(double[][] A, double[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int rowsB = B.length;
        int colsB = B[0].length;

        if (colsA != rowsB) {
            throw new IllegalArgumentException("colsA != rowsB");
        }

        double[][] C = new double[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static double[][] add(double[][] A, double[][] B) {
        int rows = A.length;
        int cols = A[0].length;
        if (rows != B.length || cols != B[0].length) {
            throw new IllegalArgumentException("same dimensions for addition.");
        }

        double[][] C = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    public static double[][] transpose(double[][] A) {
        int rows = A.length;
        int cols = A[0].length;
        double[][] B = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                B[j][i] = A[i][j];
            }
        }
        return B;
    }

    public static double[][] scalarMultiply(double[][] A, double scalar) {
        int rows = A.length;
        int cols = A[0].length;
        double[][] B = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                B[i][j] = A[i][j] * scalar;
            }
        }
        return B;
    }
}
