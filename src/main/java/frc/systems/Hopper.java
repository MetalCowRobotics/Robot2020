package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.lib14.XboxControllerMetalCow;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Hopper {
    private static final DigitalInput bottomLimit = new DigitalInput(RobotMap.Hopper.LIMIT_SWITCH_BOTTOM);
    private static MCR_SRX motor1 = new MCR_SRX(RobotMap.Hopper.Agitator_Motor);
    private static MCR_SRX motor2 = new MCR_SRX(RobotMap.Hopper.Magazine_Hopper_Motor);

    public void run() {
        if (bottomLimit.get() == true) {
            runMotors();
        }
    }

    private void runMotors() {
        motor1.set(RobotMap.Hopper.hopperSpeed);
        motor2.set(RobotMap.Hopper.hopperSpeed);
    }
}
