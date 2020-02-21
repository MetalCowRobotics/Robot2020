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
    private static MCR_SRX motor1 = new MCR_SRX(RobotMap.Funnel.Agitator_Motor);
    private static MCR_SRX motor2 = new MCR_SRX(RobotMap.Funnel.Magazine_Funnel_Motor);

    public void run() {
        if (bottomLimit.get() == true) {
            runMotors();
        }
    }

    private void runMotors() {
        motor1.set(RobotMap.Funnel.motorSpeed);
        motor2.set(RobotMap.Funnel.motorSpeed);
    }
}
