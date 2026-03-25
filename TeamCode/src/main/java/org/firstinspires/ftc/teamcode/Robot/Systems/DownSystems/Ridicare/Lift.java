package org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Ridicare;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift {

    CRServo servo1, servo2;

    public void ridicare()
    {
        servo1.setPower(1);
        servo2.setPower(1);
    }

    public void idle_power()
    {
        servo1.setPower(1);
        servo2.setPower(1);
    }

    public void set_Power(double power)
    {
        servo1.setPower(power);
        servo2.setPower(power);
    }

    public double get_Power()
    {
        return  servo1.getPower();
    }

    public Lift(HardwareMap hm, Telemetry tel)
    {
        servo1 = hm.get(CRServo.class,"liftST");
        servo2 = hm.get(CRServo.class,"liftDR");

        servo2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

}
