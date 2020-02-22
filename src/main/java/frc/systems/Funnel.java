package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.lib14.XboxControllerMetalCow;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Funnel {
    private static final DigitalInput bottomLimit = new DigitalInput(RobotMap.Funnel.LIMIT_SWITCH_BOTTOM);
    private static MCR_SRX motorA = new MCR_SRX(RobotMap.Funnel.Agitator_Motor);
    private static MCR_SRX motorM = new MCR_SRX(RobotMap.Funnel.Magazine_Funnel_Motor);
    private static final Funnel instance = new Funnel();

    private Funnel() {
    }

    public static Funnel getInstance() {
        return instance;
    }

    public void run() {
        if (bottomLimit.get() == true) {
            runMotors();
        } else {
            motorA.stopMotor();
            motorM.stopMotor();
        }
    }

    private void runMotors() {
        motorA.set(RobotMap.Funnel.motorASpeed);
        motorM.set(RobotMap.Funnel.motorSpeed);
    }
}
