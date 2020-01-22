package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Intake {
    
    private static MCR_SRX intake = new MCR_SRX(RobotMap.Intake.INTAKE_MOTOR);
    private static MCR_SRX lift = new MCR_SRX(RobotMap.Intake.LIFT_MOTOR);
    private static DigitalInput topLimit = new DigitalInput(RobotMap.Intake.TOP_LIMIT_SWITCH);
    private static DigitalInput bottomLimit = new DigitalInput(RobotMap.Intake.BOTTOM_LIMIT_SWITCH);
    private static final Intake instance = new Intake();

    private final double TopSpeed = .5;
    double LowerSpeed = .5;
    double RaiseSpeed = -.5;

    private Intake() {


    }

    public static Intake getInstance() {
        return instance;
    }

    public void startIntake() {
        intake.set(TopSpeed);

    }

    public void reverseIntake() {
        intake.set(-TopSpeed);
    }

    public void stopIntake() {
        intake.stopMotor();

    }

    public void lowerIntake() {
        if(!bottomLimit.get()){
            lift.set(LowerSpeed);
        } else {
            lift.stopMotor();
        }
    }

    public void retractIntake() {
        if (!topLimit.get()){
            stopIntake();
            lift.set(RaiseSpeed);
        }else {
            lift.stopMotor();
        }

    }
}