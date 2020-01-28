package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Magazine {
    private static MCR_SRX motor1 = new MCR_SRX(RobotMap.Magazine.LEFT_MAGAZINE_MOTOR);
    private static MCR_SRX motor2 = new MCR_SRX(RobotMap.Magazine.RIGHT_MAGAZINE_MOTOR);
    private static final Magazine instance = new Magazine();
    private static final DigitalInput topLimit = new DigitalInput(RobotMap.Magazine.LIMIT_SWITCH_TOP);
	private static final DigitalInput bottomLimit = new DigitalInput(RobotMap.Magazine.LIMIT_SWITCH_BOTTOM);
    private Magazine() {

    }

    public static Magazine getInstance() {
        return instance;
    }

    public void runMagazine() {

    }

    public void stopMagazine() {

    }

    public void checkIfLoaded() {

    }
}
