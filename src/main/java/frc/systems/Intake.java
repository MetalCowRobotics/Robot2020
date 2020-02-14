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
    boolean stowing = false;
    boolean deploying = false;

    private Intake() {
        raiseLowerIntake.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyClosed);
        raiseLowerIntake.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyOpen);
    }

    public static Intake getInstance() {
        return instance;
    }

    // run the intake belts
    public void startIntake() {
        intake.set(TopSpeed);
    }

    // run the intake belts in a reverse direction
    public void reverseIntake() {
        intake.set(-TopSpeed);
    }

    public void stopIntake() {
        intake.stopMotor();
    }

    // lower the intake mechanism
    public void lowerIntake() {
        raiseLowerIntake.set(.5);
        deploying = true;
    }

    // raise the intake mechanism
    public void retractIntake() {
        raiseLowerIntake.set(-.5);
        stowing = true;
    }

    public boolean intakeDeployed() {
        // 1=closed
        return 1 == raiseLowerIntake.isFwdLimitSwitchClosed();
    }

    public void run() {
        if (deploying && intakeDeployed()) {
            raiseLowerIntake.stopMotor();
            deploying = false;
        }
        if (stowing && intakeStowed()) {
            raiseLowerIntake.stopMotor();
            stowing = false;
        }
    }

    public boolean intakeStowed() {
        // 1==closed
        return 1 == raiseLowerIntake.isRevLimitSwitchClosed();
    }

}