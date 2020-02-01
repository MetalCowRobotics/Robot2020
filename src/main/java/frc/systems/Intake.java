package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Intake {
    
    private static MCR_SRX intake = new MCR_SRX(RobotMap.Intake.INTAKE_MOTOR);
    private static MCR_SRX lift = new MCR_SRX(RobotMap.Intake.LIFT_MOTOR);
    private static DigitalInput topLimit = new DigitalInput(RobotMap.Intake.TOP_LIMIT_SWITCH);
    private static DigitalInput bottomLimit = new DigitalInput(RobotMap.Intake.BOTTOM_LIMIT_SWITCH);
    private static final Intake instance = new Intake();
    private static MCR_SRX raiseLowerIntake = new MCR_SRX(RobotMap.Intake.RAISE_LOWER_INTAKE_MOTOR);

    private final double TopSpeed = .3;
    double LowerSpeed = .3;
    double RaiseSpeed = -.3;

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
        raiseLowerIntake.set(.5);
    }

    public void retractIntake() {
        raiseLowerIntake.set(-.5);
    }
}