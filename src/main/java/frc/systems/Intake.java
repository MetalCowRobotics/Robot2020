package frc.systems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.VictorSP;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Intake {
    private static VictorSP intake = new VictorSP(1);
    private static MCR_SRX raiseLowerIntake = new MCR_SRX(RobotMap.Intake.RAISE_LOWER_INTAKE_MOTOR);

    private final double TOP_SPEED = 1.0;
    private final double LOWER_SPEED = .5;
    private final double RETRACT_SPEED = -.5;

    private boolean stowing = false;
    private boolean deploying = false;
    private boolean intakeIsRunning = false;
    PowerDistributionPanel pdp = new PowerDistributionPanel(0);

    // Singleton instance
    private static final Intake instance = new Intake();

    private Intake() {
        raiseLowerIntake.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyOpen);
        raiseLowerIntake.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyOpen);
        raiseLowerIntake.configFactoryDefault();
        raiseLowerIntake.setNeutralMode(NeutralMode.Coast);
    }

    public static Intake getInstance() {
        return instance;
    }

    public void run() {
        if (deploying && intakeDeployed()) {
            raiseLowerIntake.stopMotor(); startIntake();
            deploying = false;
        }
        if (stowing && intakeStowed()) {
            raiseLowerIntake.stopMotor(); stopIntake();
            stowing = false;
        }
    }

    // run the intake belts
    public void startIntake() {
        intake.set(TOP_SPEED);
        intakeIsRunning = true;
    }

    // run the intake belts in a reverse direction
    public void reverseIntake() {
        intake.set(-TOP_SPEED);
        intakeIsRunning = true;
    }

    public void stopIntake() {
        intake.stopMotor();
        intakeIsRunning = false;
    }

    // lower the intake mechanism
    public void lowerIntake() {
        raiseLowerIntake.set(LOWER_SPEED);
        deploying = true;
        stowing = false;
    }
    public double current(){
        return pdp.getCurrent(6);
    }
    // raise the intake mechanism
    public void retractIntake() {
        raiseLowerIntake.set(RETRACT_SPEED);
        stowing = true;
        deploying = false;
    }

    public boolean intakeDeployed() {
        // 1=closed
        System.out.println("Is intake deployed?" + raiseLowerIntake.isFwdLimitSwitchClosed());
        return 1 == raiseLowerIntake.isFwdLimitSwitchClosed();
    }

    public boolean intakeStowed() {
        // 1==closed
        System.out.println("Is intake stowed?" + raiseLowerIntake.isRevLimitSwitchClosed());
        return 1 == raiseLowerIntake.isRevLimitSwitchClosed();
    }

    public void toggleIntakeState() {
        if (intakeIsRunning) {
            stopIntake();
        } else {
            startIntake();
        }
    }

}