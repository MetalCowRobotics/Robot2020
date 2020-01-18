package frc.systems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Shooter {
    
    private static MCR_SRX topShooter = new MCR_SRX(RobotMap.Shooter.TOP_MOTOR);
    private static MCR_SRX bottomShooter = new MCR_SRX(RobotMap.Shooter.BOTTOM_MOTOR);
    private static final SpeedControllerGroup SHOOTER = new SpeedControllerGroup(topShooter, bottomShooter); 
    private static final Shooter instance = new Shooter();

    private Shooter() {

    }

    public static Shooter getInstance() {
        return instance;
    }

    public void runShooter() {
        
    }

    public void stopShooter() {

    }

    public void checkSpeed() {
        
    }
}
