package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Intake {

    // private static MCR_SRX intake = new MCR_SRX(RobotMap.Intake.INTAKE_MOTOR);
    private static VictorSP intake = new VictorSP(1);
    // private static MCR_SRX lift = new MCR_SRX(RobotMap.Intake.LIFT_MOTOR);
    // private static DigitalInput topLimit = new DigitalInput(RobotMap.Intake.TOP_LIMIT_SWITCH);
    // private static DigitalInput bottomLimit = new DigitalInput(RobotMap.Intake.BOTTOM_LIMIT_SWITCH);
    private static MCR_SRX raiseLowerIntake = new MCR_SRX(RobotMap.Intake.RAISE_LOWER_INTAKE_MOTOR);
    private static final Intake instance = new Intake();


    private final double TopSpeed = .75;
    double LowerSpeed = .3;
    double RaiseSpeed = -.3;
    boolean stowing = false;
    boolean deploying = false;
    private boolean intakeIsRunning = false;

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

    // run the intake belts
    public void startIntake() {
        intake.set(TopSpeed);
        intakeIsRunning = true;
    }

    // run the intake belts in a reverse direction
    public void reverseIntake() {
        intake.set(-TopSpeed);
    }

    public void stopIntake() {
        intake.stopMotor();
        intakeIsRunning = false;
    }

    // lower the intake mechanism
    public void lowerIntake() {
        raiseLowerIntake.set(.5);
        deploying = true;
        stowing = false;
        System.out.println("Lower Intake");
    }

    // raise the intake mechanism
    public void retractIntake() {
        raiseLowerIntake.set(-.5);
        stowing = true;
        deploying = false;
       System.out.println("Retract Intake");
    }

    public boolean intakeDeployed() {
        // 1=closed
       System.out.println("Is intake deployed?" +raiseLowerIntake.isFwdLimitSwitchClosed());
        return 1 == raiseLowerIntake.isFwdLimitSwitchClosed();
    }

    public void run() {
       // System.out.println("deploying:"+ deploying + "   Stowing:" + stowing);
        if (deploying && intakeDeployed()) {
            System.out.println("stop deploying");
            raiseLowerIntake.stopMotor();           
            deploying = false;
        }
        if (stowing && intakeStowed()) {
            System.out.println("stop stowing");
            raiseLowerIntake.stopMotor();
            stowing = false;
        } else {
            //System.out.println("not stopping stowing");
        }
    }

    public boolean intakeStowed() {
        // 1==closed
        System.out.println("Is intake stowed?" +raiseLowerIntake.isRevLimitSwitchClosed());
        return 1 == raiseLowerIntake.isRevLimitSwitchClosed();
    }
    public void toggleIntakeState(){
        if (intakeIsRunning){
            stopIntake();
        }
        else{
            startIntake();
        }
    }

}