package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Funnel {
    //private static final DigitalInput bottomLimit = new DigitalInput(RobotMap.Funnel.LIMIT_SWITCH_BOTTOM);
    //private static MCR_SRX agitator = new MCR_SRX(RobotMap.Funnel.Agitator_Motor);
    private static MCR_SRX magIntake = new MCR_SRX(RobotMap.Funnel.Magazine_Funnel_Motor);
    private static final Funnel instance = new Funnel();

    private Funnel() {
        magIntake.configFactoryDefault();
		magIntake.setNeutralMode(NeutralMode.Coast);
    }

    public static Funnel getInstance() {
        return instance;
    }

    public void run(boolean bottomLimit) {
        if (bottomLimit) {
            runMotors();
        } else {
            //agitator.stopMotor();
            magIntake.stopMotor();
        }
    }

    private void runMotors() {
        // agitator.set(RobotMap.Funnel.motorASpeed);
        magIntake.set(RobotMap.Funnel.motorSpeed);
    }
}
