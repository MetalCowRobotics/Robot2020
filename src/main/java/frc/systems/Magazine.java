package frc.systems;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Magazine {
    private static MCR_SRX magazine = new MCR_SRX(RobotMap.Magazine.MAGAZINE_MOTOR);
    private static final Magazine instance = new Magazine();

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
