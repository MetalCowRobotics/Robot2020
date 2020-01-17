package frc.systems;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Turret {
    private static MCR_SRX turret = new MCR_SRX(RobotMap.Turret.TURRET_MOTOR);
    private static final Turret instance = new Turret();

    private Turret() {

    }

    public static Turret getInstance() {
        return instance;
    }

    public void rotateTurret(double degrees) {
        
    }
}
