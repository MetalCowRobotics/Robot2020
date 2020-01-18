package frc.systems;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Hood {

    private static MCR_SRX hood = new MCR_SRX(RobotMap.Hood.HOOD_MOTOR);
    private static final Hood instance = new Hood();

    private Hood() {

    }

    public static Hood getInstance() {
        return instance;
    }

    public void moveHood(double degrees) {
        
    }
}
