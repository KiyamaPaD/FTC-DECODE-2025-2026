package org.firstinspires.ftc.teamcode.Robot.Systems.DownSystems.Sortare;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Systems.UpSystems.Camera_LimeLight.LimeLight;


public class Sortare {

    public static double tolerance = 0.02;
    public static double up_delay = 0.3, down_delay = 0.6, wait_next_slot = 0.3;

    ElapsedTime timer = new ElapsedTime();
    double timp;

    Telemetry telemetry;

    public enum Slot {GOL, MOV, VERDE}
    public Slot[] BileInSort = {Slot.GOL, Slot.GOL, Slot.GOL};
    public static Slot[] motif = {Slot.MOV, Slot.MOV, Slot.MOV};
    public static boolean MOTIF_MEMORAT, moveConfirmed;
    public int nrBileInSort, bilaLaRand;
    int[] rand = {1, 2, 3}, randDef = {1, 2, 3};

    public boolean Intaking, readyToShoot = true, asteaptaBila;


    public boolean startTransferCommand = false;

    Servo sortare;
    AnalogInput sortareAnalog;
    public DetectareMovVerde senzor;
    public Transfer puta;

    LimeLight llcam;

    public Sortare(HardwareMap hardwareMap, Telemetry tel) {
        this.telemetry = tel;

        sortare = hardwareMap.get(Servo.class, "sortare");
        sortareAnalog = hardwareMap.get(AnalogInput.class, "analog");
        llcam = new LimeLight(hardwareMap, tel);

        senzor = new DetectareMovVerde(hardwareMap, tel);
        puta = new Transfer(hardwareMap, tel);
    }

    public boolean SetMotif() {
        int detectedID = llcam.AprilTagResultID(true);

        if (detectedID != -1) {
            if (detectedID == 21) {
                motif[0] = Slot.VERDE; motif[1] = Slot.MOV; motif[2] = Slot.MOV;
                return true;
            } else if (detectedID == 22) {
                motif[0] = Slot.MOV; motif[1] = Slot.VERDE; motif[2] = Slot.MOV;
                return true;
            } else if (detectedID == 23) {
                motif[0] = Slot.MOV; motif[1] = Slot.MOV; motif[2] = Slot.VERDE;
                return true;
            }
        }
        return false;
    }
    public int GetID()
    {
        return  llcam.AprilTagResultID(true);
    }
    public void Update() {

        telemetry.addData("STATE", currentState);
        telemetry.addData("Bile in Sort", nrBileInSort);
        telemetry.addData("Puta Active", puta.active);
        telemetry.addData("can shoot", readyToShoot);
        telemetry.addData("servo at pos", servoAtPosition(sortare, sortareAnalog.getVoltage()));
        telemetry.addLine("motif >> " + motif[0] + " " + motif[1] + " " + motif[2]);
        telemetry.addLine("bile >> " + BileInSort[0] + " " + BileInSort[1] + " " + BileInSort[2]);
        telemetry.addLine("rand >> " + rand[0] + " " + rand[1] + " " + rand[2]);

        if (Intaking) {
            handleIntake();
        } else if (readyToShoot) {
            sortUpdate();
        }
        puta.trUpdate();
    }

    private void handleIntake() {
        if (nrBileInSort == 0) sortare.setPosition(0);
        else if (nrBileInSort == 1) sortare.setPosition(0.38);
        else if (nrBileInSort == 2) sortare.setPosition(0.77);

        if (senzor.vedeBila()) {
            Intake();
            asteaptaBila = false;
        } else {
            asteaptaBila = true;
        }
    }

    public void Shoot()
    {
        if (!Intaking && nrBileInSort > 0 && currentState == Sortare.SortState.WAIT_START_TRANSFER) {
            startTransferCommand = true;
        }
        else if (Intaking && nrBileInSort > 0) {
            closeIntake();
            startTransferCommand = true;
        }
    }

    public enum SortState {WAIT_START_TRANSFER, SWITCH_SLOT, DELAY_UP, TRANSFER_UP, DELAY_DOWN, TRANSFER_DOWN, WAIT_NEXT}

    public SortState currentState = SortState.WAIT_START_TRANSFER;

    public void sortUpdate() {
        if (nrBileInSort > 0 && bilaLaRand < 3) {

            switch (currentState) {
                case WAIT_START_TRANSFER:

                    if (startTransferCommand) {
                        currentState = SortState.SWITCH_SLOT;
                    }
                    break;

                case SWITCH_SLOT:

                    if (rand[bilaLaRand] == 1) sortare.setPosition(0);
                    else if (rand[bilaLaRand] == 2) sortare.setPosition(0.38);
                    else if (rand[bilaLaRand] == 3) sortare.setPosition(0.77);

                    if (servoAtPosition(sortare, sortareAnalog.getVoltage())) {
                        timp = timer.seconds();
                        currentState = SortState.DELAY_UP;
                    }
                    puta.active = false;
                    break;

                case DELAY_UP:
                    if (timer.seconds() - timp >= up_delay) {
                        currentState = SortState.TRANSFER_UP;
                    }
                    break;

                case TRANSFER_UP:
                    puta.active = true;

                    if (puta.isTransferUp()) {
                        timp = timer.seconds();
                        currentState = SortState.DELAY_DOWN;
                    }
                    break;

                case DELAY_DOWN:
                    if (timer.seconds() - timp >= down_delay) {
                        currentState = SortState.TRANSFER_DOWN;
                    }
                    break;

                case TRANSFER_DOWN:
                    puta.active = false;

                    if (puta.isTransferDown()) {
                        timp = timer.seconds();
                        currentState = SortState.WAIT_NEXT;
                    }
                    break;

                case WAIT_NEXT:
                    if (timer.seconds() - timp >= wait_next_slot) {
                        bilaLaRand++;
                        nrBileInSort--;
                        currentState = SortState.SWITCH_SLOT;
                    }
                    break;
            }
        } else {
            openIntake();

            currentState = SortState.WAIT_START_TRANSFER;
            startTransferCommand = false;
        }
    }

    public void Intake() {
        if (asteaptaBila && nrBileInSort < 3) {
            BileInSort[nrBileInSort] = senzor.culoareBila();
            nrBileInSort++;
            if (nrBileInSort == 3) closeIntake();
        }
    }

    public void openIntake() {
        puta.active = false;
        moveConfirmed = false;
        bilaLaRand = 0;
        Intaking = true;
    }

    public void closeIntake() {
        bilaLaRand = 0;
        rand = randDef.clone();
        Intaking = false;
        timp = timer.seconds();
        sorteazaOrdinea();
    }

    public void sorteazaOrdinea() {
        for (int i = 0; i < nrBileInSort; i++) {
            for (int j = i; j < nrBileInSort; j++) {
                if (motif[i] == BileInSort[j] && BileInSort[j] != Slot.GOL) {
                    if (i == j) break;
                    Slot tempBila = BileInSort[i];
                    BileInSort[i] = BileInSort[j];
                    BileInSort[j] = tempBila;
                    int tempRand = rand[i];
                    rand[i] = rand[j];
                    rand[j] = tempRand;
                    break;
                }
            }
        }
    }

    double getServoPosition(double currentVoltage) {
        double minV = 0.195;
        double maxV = 3.05;
        double position = (currentVoltage - minV) / (maxV - minV);
        if (position > 1.0) position = 1.0;
        if (position < 0.0) position = 0.0;
        return position;
    }

    boolean servoAtPosition(Servo servo, double analogInput) {
        return Math.abs(servo.getPosition()-getServoPosition(analogInput)) < tolerance;
    }
}