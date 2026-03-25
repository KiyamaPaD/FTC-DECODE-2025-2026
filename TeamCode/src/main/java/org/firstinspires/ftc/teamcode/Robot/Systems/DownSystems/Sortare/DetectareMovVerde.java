package org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class DetectareMovVerde {

    Telemetry telemetry;
    public static double pragVerde = 0.45, pragMov = 0.55, minSumRGB = 0.0145;
    private NormalizedColorSensor senzorCuloareIntake;
    public DetectareMovVerde(HardwareMap hardwareMap, Telemetry tel) {
        senzorCuloareIntake = hardwareMap.get(NormalizedColorSensor.class, "SCintake");
        telemetry = new MultipleTelemetry(tel, FtcDashboard.getInstance().getTelemetry());
    }
    public boolean vedeBila() {
        NormalizedRGBA colors = senzorCuloareIntake.getNormalizedColors();
        float sumRGB = colors.red + colors.green + colors.blue;
        telemetry.addData("sum rgb", sumRGB);

        if (sumRGB < minSumRGB) return false;

        float ratioGreen = colors.green / sumRGB;
        float ratioPurple = (colors.red + colors.blue) / sumRGB;

        return (ratioGreen > pragVerde) || (ratioPurple > pragMov);
    }

    public Sortare.Slot culoareBila() {
        NormalizedRGBA colors = senzorCuloareIntake.getNormalizedColors();
        float sumRGB = colors.red + colors.green + colors.blue;
        if (sumRGB < minSumRGB) return Sortare.Slot.GOL;

        float ratioGreen = colors.green / sumRGB;
        float ratioPurple = (colors.red + colors.blue) / sumRGB;

        telemetry.addData("%verde", ratioGreen);
        telemetry.addData("%mov", ratioPurple);

        if((ratioGreen > pragVerde) && (ratioPurple > pragMov))
        {
            if(ratioGreen > ratioPurple) return Sortare.Slot.VERDE;
            else return Sortare.Slot.MOV;
        }
        if(ratioGreen > pragVerde) return Sortare.Slot.VERDE;
        else if(ratioPurple > pragMov) return Sortare.Slot.MOV;

        return Sortare.Slot.GOL;
    }
}