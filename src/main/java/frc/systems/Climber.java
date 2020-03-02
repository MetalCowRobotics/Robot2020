
package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Climber {
    private static MCR_SRX rightClimber = new MCR_SRX(RobotMap.Climber.RIGHT_CLIMB_MOTOR);
    private static MCR_SRX leftClimber = new MCR_SRX(RobotMap.Climber.LEFT_CLIMB_MOTOR);
    // private static final SpeedControllerGroup climber = new SpeedControllerGroup(leftClimber);
    private static final SpeedControllerGroup climber = new SpeedControllerGroup(leftClimber, rightClimber);

    // Singleton Instance
    private static final Climber instance = new Climber();

    private Climber() {
        rightClimber.configFactoryDefault();
        rightClimber.setNeutralMode(NeutralMode.Brake);
        leftClimber.configFactoryDefault();
        leftClimber.setNeutralMode(NeutralMode.Brake);
    }

    public static Climber getInstance() {
        return instance;
    }

    public void run() {

    }

    public void raiseClimber(double speed) {
        climber.set(speed);
    }

}
