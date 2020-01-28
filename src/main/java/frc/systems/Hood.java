package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Hood {
    private static MCR_SRX hood = new MCR_SRX(RobotMap.Hood.HOOD_MOTOR);
    private static DigitalInput hoodUp = new DigitalInput(RobotMap.Hood.HOOD_UP);
    private static DigitalInput hoodDown = new DigitalInput(RobotMap.Hood.HOOD_DOWN);
    private static final Hood instance = new Hood();

    private Hood() {

    }

    public static Hood getInstance() {
        return instance;
    }

    public static void raiseHood() {
        if (!hoodUp.get()) {
            hood.set(RobotMap.Hood.HOOD_SPEED);
        } else {
            hood.stopMotor();
        }
    }

    public static void lowerHood() {
        if (!hoodDown.get()) {
            hood.set(-RobotMap.Hood.HOOD_SPEED);
        } else {
            hood.stopMotor();
        }
    }
}
