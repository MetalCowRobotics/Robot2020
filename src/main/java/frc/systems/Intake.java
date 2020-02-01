package frc.systems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Intake {
    private static MCR_SRX intake = new MCR_SRX(RobotMap.Intake.INTAKE_MOTOR);
    private static final Intake instance = new Intake();
    private static MCR_SRX raiseLowerIntake = new MCR_SRX(RobotMap.Intake.RAISE_LOWER_INTAKE_MOTOR);

    private Intake() {
        raiseLowerIntake.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyClosed);
        raiseLowerIntake.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyOpen);
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
        raiseLowerIntake.set(.5);

    }

    public void retractIntake() {
        raiseLowerIntake.set(-.5);

    }

    public boolean intakeDeployed() {
        // 1=closed
        return 1 == raiseLowerIntake.isFwdLimitSwitchClosed();
    }
}