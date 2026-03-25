package org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Transfer{

    Telemetry telemetry;
    public Servo transfer;
    AnalogInput analog;
    public boolean active;
    public static double pos_sus=0.39, pos_jos=0.05, tolUp = 0.030, tolDown = 0.030;
    public Transfer(HardwareMap hardwareMap, Telemetry tel){
        telemetry = new MultipleTelemetry(tel, FtcDashboard.getInstance().getTelemetry());
        transfer = hardwareMap.get(Servo.class,"transfer");
        analog = hardwareMap.get(AnalogInput.class, "analog transfer");
    }
    public void trUpdate(){

        telemetry.addData("analog v", analog.getVoltage());
        telemetry.addData("transfer", transfer.getPosition());

        double target = active ? pos_sus : pos_jos;
        if (transfer.getPosition() != target) {
            transfer.setPosition(target);
        }
    }

    public boolean isTransferUp()
    {
        return active && Math.abs(analog.getVoltage() - 1.360) < tolUp;
    }

    public boolean isTransferDown()
    {
        return !active && Math.abs(analog.getVoltage() - 0.520) < tolDown;
    }
}