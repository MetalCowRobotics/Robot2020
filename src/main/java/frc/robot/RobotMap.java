package frc.robot;

import java.util.logging.Level;

public class RobotMap {

	public final class DriverController {
		public static final int 	USB_PORT = 0;
	}

	public final class OperatorController {
		public static final int USB_PORT = 1;
	}

	public static final class Drivetrain {
		public static final int LEFT_MOTOR = 3;
		public static final int LEFT_MOTOR_NO_ENCODER = 5;
		public static final int RIGHT_MOTOR = 4;
		public static final int RIGHT_MOTOR_NO_ENCODER = 6;
		public static final int WHEEL_DIAMETER = 6;
		public static final double RAMP_SPEED = .6;
	}

	public final class DriveToSensor {
		public static final double TOP_SPEED =.6;
		public static final int SLOW_DOWN_DISTANCE = 18;
		public static final double BOTTOM_SPEED = .5;
		public static final double MAX_ADJUSTMENT = .4;
	}

	public final class DriveWithEncoder {
		public static final double TOP_SPEED = .8;
		public static final double BOTTOM_SPEED = .4;
		public static final double MAX_ADJUSTMENT = .6;
		public static final int TICS_PER_ROTATION = 4096; 
		public static final double INCHES_PER_ROTATION = Math.PI * RobotMap.Drivetrain.WHEEL_DIAMETER;
		public static final double SLOW_DOWN_DISTANCE = (12 / INCHES_PER_ROTATION) * TICS_PER_ROTATION;
		public static final double REVERSE_TOP_SPEED = -0.6;
		public static final double REVERSE_BOTTOM_SPEED = -0.4;
	}

	public final class TurnDegrees {
		public static final double kP = .04; 
		public static final double kI = .001; 
		public static final double kD = .0; 
		public static final double TOP_SPEED = 0;
		public static final double VARIANCE = 1; //.25
		public static final double MAX_ADJUSTMENT = .6;
		public static final double SLOW_VARIANCE = 20; //10
		public static final double SLOW_ADJUSTMENT = .4;
	}

	public final class DriveStraightTime {
		public static final double TOP_SPEED = .6;
		public static final double MAX_ADJUSTMENT = .4;
		public static final double BOTTOM_SPEED = .3;
		public static final double SLOW_DOWN_TIME = 2;
	}

	public final class Shooter {
		public static final int TOP_MOTOR = 0;//assign channel
		public static final int BOTTOM_MOTOR = 0;//assign channel
	}
	
	public final class Hood {
		public static final int HOOD_MOTOR = 0;//assign channel
	}

	public final class Turret {
		public static final int TURRET_MOTOR = 0;//assign channel
	}

	public final class Magazine {
		public static final int MAGAZINE_MOTOR = 0;//assign channel
	}

	public final class Intake {
		public static final double INTAKE_SPEED = 1;
		public static final double EJECT_SPEED = -1;
		public static final int INTAKE_MOTOR = 3;// CAN
		public static final double RAMP_SPEED = .8;
		public static final int RAISE_LOWER_INTAKE_MOTOR = 1;
		
	}

	public final class Climber {
		public static final int LEFT_CLIMB_MOTOR = 1;//assign channel
		public static final int RIGHT_CLIMB_MOTOR = 2;//assign channel
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
		public static final Level cargoHandlerClass = Level.WARNING;
		public static final Level hatchHandlerClass = Level.WARNING;
	}
}
