package org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Camera_Husky_SI_Intake;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeCUHusky {

    double midle = 120;

    public DcMotorEx intake;
    public HuskyLens huskyLens;

    Telemetry telemetry;
    Gamepad gamepad;

    // Mov: ID 1, 2, 3
    // Verde: ID 4, 5, 6
    private final int[] IDS_MOV = {1, 2, 3};
    private final int[] IDS_VERDE = {4, 5, 6};

    public IntakeCUHusky(HardwareMap hardwareMap, Telemetry tel, Gamepad gamepad) {
        telemetry = new MultipleTelemetry(tel, FtcDashboard.getInstance().getTelemetry());
        this.gamepad = gamepad;

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        huskyLens = hardwareMap.get(HuskyLens.class, "husky");
        if (!huskyLens.knock()) {
        }
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);
    }


    /** if true partea dreapta ca prefer dreapta **/
    public boolean isRight()
    {
        HuskyLens.Block[] blocks = huskyLens.blocks();
        int st = 0;
        int dr = 0;
        for(int i = 0;i< blocks.length;i++)
        {
            if(blocks[i].x<midle)
                st++;
            else
                dr++;
        }
        if(dr>=st)
            return true;
        return false;
    }

    private boolean esteCuloareValida(int id) {
        for (int m : IDS_MOV) {
            if (id == m) return true;
        }
        for (int v : IDS_VERDE) {
            if (id == v) return true;
        }
        return false;
    }

    public void merg() {
        HuskyLens.Block[] blocks = huskyLens.blocks();
        boolean bilaDetectata = false;

        if (blocks != null && blocks.length > 0) {
            for (HuskyLens.Block block : blocks) {
                if (esteCuloareValida(block.id)) {
                    bilaDetectata = true;
                    telemetry.addData("Detectat ID", block.id);
                    break;
                }
            }
        }

        if (bilaDetectata || gamepad.cross) {
            intake.setPower(1.0);

        }
        else if(gamepad.square) {
            intake.setPower(-1);
        }
        else
        {
            intake.setPower(0.0);

        }
    }

    public void manual(double pow)
    {
        intake.setPower(pow);
    }
}