
package frc.systems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Climber {
    
    private static MCR_SRX leftClimber = new MCR_SRX(RobotMap.Climber.RIGHT_CLIMB_MOTOR);
    private static MCR_SRX rightClimber = new MCR_SRX(RobotMap.Climber.LEFT_CLIMB_MOTOR);
    private static final SpeedControllerGroup CLIMBER = new SpeedControllerGroup(leftClimber, rightClimber); 
    private static final Climber instance = new Climber();

    private Climber() {

    }

    public static Climber getInstance() {
        return instance;
    }

    public void raiseClimber() {

    }
    
    public void lowerClimber() {
        
    }
}
