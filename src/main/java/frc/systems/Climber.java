
package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Climber {

    private static MCR_SRX leftClimber = new MCR_SRX(RobotMap.Climber.RIGHT_CLIMB_MOTOR);
    private static MCR_SRX rightClimber = new MCR_SRX(RobotMap.Climber.LEFT_CLIMB_MOTOR);
    private static final SpeedControllerGroup climber = new SpeedControllerGroup(leftClimber, rightClimber);
    private static final DigitalInput camIn = new DigitalInput(4);//= new DigitalInput(RobotMap.Climber.CAM_IN_LIMITSWITCH);
    private static final Servo camServo = new Servo(RobotMap.Climber.SERVO);
    private static final Climber instance = new Climber();
    private static final double RAISE_SPEED = 0.5;
    private static final double LOWER_SPEED = -0.5;
    private static final double SERVO_OUT = -0.1;
    private static final double SERVO_IN = 0.1;

    private Climber() {

    }

    public static Climber getInstance() {
        return instance;
    }

    public void stopClimber() {
        climber.stopMotor();
        if (!isCamDeployed()) {
            deployCam();
        }
    }

    // lift the robot off the ground
    public void raiseClimber() {
        if (!isCamDeployed()) {
            deployCam();
        } else {
            climber.set(RAISE_SPEED);
        }
    }

    // deploy hook
    public void lowerClimber() {
        SmartDashboard.putBoolean("limitSwitch", camIn.get());
        if (isCamDeployed()) {
            releaseCam();
        } else {
            climber.set(LOWER_SPEED);
        }
    }

    private void releaseCam() {
        camServo.set(SERVO_OUT);
    }

    private void deployCam() {
        camServo.set(SERVO_IN);
    }

    private boolean isCamDeployed() {
        SmartDashboard.putBoolean("camLimit", !camIn.get());
        return !camIn.get();
    }

	public void run() {
	}
}
