package frc.robot;

import java.util.logging.Level;

public class RobotMap {

	public final class DriverController {
		public static final int USB_PORT = 0;
	}

	public final class OperatorController {
		public static final int USB_PORT = 1;
	}

	public static final class Drivetrain {
		public static final int LEFT_MOTOR = 8;
		public static final int LEFT_MOTOR_NO_ENCODER = 13;
		public static final int RIGHT_MOTOR = 14;
		public static final int RIGHT_MOTOR_NO_ENCODER = 11;
		public static final int WHEEL_DIAMETER = 6;
		public static final int TICS_PER_ROTATION = 4096; 
		public static final double INCHES_PER_ROTATION = Math.PI * RobotMap.Drivetrain.WHEEL_DIAMETER;		
		public static final double RAMP_SPEED = .6;
	}

	public final class DriveToSensor {
		public static final double TOP_SPEED =.95;
		public static final int SLOW_DOWN_DISTANCE = 18;
		public static final double BOTTOM_SPEED = .5;
		public static final double MAX_ADJUSTMENT = .4;
	}

	public final class DriveWithEncoder {
		public static final double TOP_SPEED = .7;
		public static final double BOTTOM_SPEED = .511111;
		public static final double MAX_ADJUSTMENT = .6;
		public static final int TICS_PER_ROTATION = 4096; 
		public static final double INCHES_PER_ROTATION = Math.PI * RobotMap.Drivetrain.WHEEL_DIAMETER;
		public static final double SLOW_DOWN_DISTANCE = (12 / INCHES_PER_ROTATION) * TICS_PER_ROTATION;
		public static final double REVERSE_TOP_SPEED = -0.6;
		public static final double REVERSE_BOTTOM_SPEED = -0.4;
	}

	public final class TurnDegrees {
		public static final double kP = 0.04; 
		public static final double kI = .008; 
		public static final double kD = .08;
		public static final double Iz = 5;
		public static final double TOP_SPEED = 0;
		public static final double VARIANCE = 1; //.25
		public static final double MAX_ADJUSTMENT = .6;
		public static final double SLOW_VARIANCE = 15; //10
		public static final double SLOW_ADJUSTMENT = .4;
	}
	

	public final class DriveStraightTime {
		public static final double TOP_SPEED = 1;
		public static final double MAX_ADJUSTMENT = .4;
		public static final double BOTTOM_SPEED = .3;
		public static final double SLOW_DOWN_TIME = 2;
	}

	public final class Shooter {
		public static final int TOP_MOTOR = 3;
		public static final int BOTTOM_MOTOR = 4;
		public static final int TOP_MOTOR_ID = 0;
		public static final int BOTTOM_MOTOR_ID = 1;
	}
	
	public final class Hood {
		public static final int HOOD_MOTOR = 12;
		// public static final int HOOD_UP = 0;
		// public static final int HOOD_DOWN = 1;
		public static final double HOOD_SPEED = .6;

	}

	public final class Turret {
		public static final int TURRET_MOTOR = 7;
	}
	public final class Test {
		public static final int BAG_MOTOR = 2;
	}
	public final class Magazine {
		public static final int MAGAZINE_MOTOR = 0; //PWM
		//public static final int LIMIT_SWITCH_TOP = 0;
		//public static final int RIGHT_MAGAZINE_MOTOR = 0;
		//public static final int LEFT_MAGAZINE_MOTOR = 1;
		public static final int IS_THERE_A_BALL_TOP = 0;//DIO
		public static final int IS_THERE_A_BALL_BOTTOM = 1;//DIO
	}

	public final class Intake {
		public static final double INTAKE_SPEED = 1;
		public static final double EJECT_SPEED = -1;
		public static final double RAMP_SPEED = .8;
		//public static final int LIFT_MOTOR = 16; //CAN
		public static final int INTAKE_MOTOR = 12; //CAN
		public static final int TOP_LIMIT_SWITCH = 1;//DIO
		public static final int BOTTOM_LIMIT_SWITCH = 2;//DIO
		public static final int RAISE_LOWER_INTAKE_MOTOR = 16;
		
	}

	public final class Climber {
		public static final int LEFT_CLIMB_MOTOR = 10;
		public static final int RIGHT_CLIMB_MOTOR = 9;
		public static final int CAM_IN_LIMITSWITCH = 4;
		public static final int SERVO = 9;
	}

	
	public static final class LogLevels {
		public static final Level robotClass = Level.WARNING;
		public static final Level robotDashboardClass = Level.WARNING;
		public static final Level masterControlsClass = Level.WARNING;
		public static final Level driveTrainClass = Level.WARNING;
		public static final Level elevatorClass = Level.WARNING;
		public static final Level intakeClass = Level.WARNING;
		public static final Level climberClass = Level.WARNING;
		public static final Level missionClass = Level.WARNING;
		public static final Level autoDriveClass = Level.WARNING;
		public static final Level componentBuilderClass = Level.WARNING;
	}


	public static final class ColorWheel{
		public static final int Motor = 6;
	} 

	public static final class LightRing {
		public static final int PORT = 0; //RELAY Port
	}

	public static final class LightBar {
		public static final int PORT = 0;  //PWM Port
		public static final int LENGTH = 60;
	}

	public static final class Funnel{
		public static final int Agitator_Motor = 5;
		public static final int Magazine_Funnel_Motor = 15;
		public static final int LIMIT_SWITCH_BOTTOM = 1;
		public static final double motorSpeed = 1;
		public static final double motorASpeed = 1;
       
	}
}
