package frc.systems;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Intake {
    private static MCR_SRX intake = new MCR_SRX(RobotMap.Intake.INTAKE_MOTOR);
    private static final Intake instance = new Intake();

    private Intake() {

    }

    public static Intake getInstance() {
        return instance;
    }

    public void startIntake() {

    }

    public void reverseIntake() {
        
    }

    public void stopIntake() {

    }

    public void lowerIntake() {

    }

    public void retractIntake() {

    }
}