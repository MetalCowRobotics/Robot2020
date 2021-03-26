package frc.systems;

// import java.util.logging.Logger;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.IMUFixed;
import frc.robot.RobotDashboard;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Drivetrain;

public class DriveTrain {
	// private static final Logger logger = Logger.getLogger(DriveTrain.class.getName());
	public static final ADIS16470_IMU GYRO = new ADIS16470_IMU();
	// public static final IMUFixed GYRO = new IMUFixed();
	private static MCR_SRX rightFrontMotor = new MCR_SRX(RobotMap.Drivetrain.RIGHT_MOTOR);
	private static MCR_SRX rightBackMotor = new MCR_SRX(Drivetrain.RIGHT_MOTOR_NO_ENCODER); 
	private static MCR_SRX leftFrontMotor = new MCR_SRX(RobotMap.Drivetrain.LEFT_MOTOR);
	private static MCR_SRX leftBackMotor = new MCR_SRX(Drivetrain.LEFT_MOTOR_NO_ENCODER); 
	private static final SpeedControllerGroup RIGHT_DRIVE_MOTORS = new SpeedControllerGroup(rightFrontMotor, rightBackMotor); 
	private static final SpeedControllerGroup LEFT_DRIVE_MOTORS = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
	private static final DifferentialDrive drive = new DifferentialDrive(LEFT_DRIVE_MOTORS, RIGHT_DRIVE_MOTORS);
	private static final RobotDashboard dashboard = RobotDashboard.getInstance();
	private static final MasterControls controller = MasterControls.getInstance();


	public static final double SPRINT_SPEED = 1.0;
	public static final double NORMAL_SPEED = 0.7;
	public static final double CRAWL_SPEED = .5;

	
	private int inverted = 1;
	private static final DriveTrain instance = new DriveTrain();
		
	

	// Singleton
	protected DriveTrain() {
		// set to coast
		rightFrontMotor.configFactoryDefault();
		rightFrontMotor.setNeutralMode(NeutralMode.Brake);
		rightBackMotor.configFactoryDefault();
		rightBackMotor.setNeutralMode(NeutralMode.Brake);
		leftFrontMotor.configFactoryDefault();
		leftFrontMotor.setNeutralMode(NeutralMode.Brake);
		leftBackMotor.configFactoryDefault();
		leftBackMotor.setNeutralMode(NeutralMode.Brake);
		leftFrontMotor.setSensorPhase(true);

		leftFrontMotor.setSelectedSensorPosition(0);
		rightFrontMotor.setSelectedSensorPosition(0);

		// set to ramp
		rightBackMotor.configOpenloopRamp(.25);
		rightFrontMotor.configOpenloopRamp(.25);
		leftBackMotor.configOpenloopRamp(.25);
		leftFrontMotor.configOpenloopRamp(.25);

		// rightFrontMotor.configOpenloopRamp(Drivetrain.RAMP_SPEED);
		// leftFrontMotor.configOpenloopRamp(Drivetrain.RAMP_SPEED);
		// rightFrontMotor.setNeutralMode(NeutralMode.Brake);
		// leftFrontMotor.setNeutralMode(NeutralMode.Brake);
		// logger.setLevel(RobotMap.LogLevels.driveTrainClass);
	}

	public static DriveTrain getInstance() {
		return instance;
	}

	public void drive() {
		if (controller.invertDrive()) {
			invert();
		}
		double speed = (controller.forwardSpeed() - controller.reverseSpeed()) * inverted * getThrottle();
		// drive.curvatureDrive(speed, controller.direction(), false);
		// drive.arcadeDrive(speed, controller.direction() * getThrottle());
		if (controller.isCrawlToggle()) {
			drive.arcadeDrive(speed, controller.direction()* .5) ;
		} else {
			drive.arcadeDrive(speed, controller.direction()* .8);
		}

		// drive.arcadeDrive(speed, controller.direction() * UtilityMethods.absMin(getThrottle(), .7));
		//testing
		dashboard.pushLeftEncoder(getLeftEncoderTics());
		dashboard.pushRightEncoder(getRightEncoderTics());

		SmartDashboard.putNumber("Gyro", getAngle());
	}
	
	public void arcadeDrive(double speed, double angle) {
		drive.arcadeDrive(speed, angle);
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
		drive.tankDrive(leftSpeed, rightSpeed);
	}

	public void stop() {
		drive.stopMotor();
	}

	public void calibrateGyro() {
		GYRO.calibrate();
	}

	public void resetGyro() {
		DriverStation.reportWarning("Gyro Before Reset: " + getAngle(), false);
		GYRO.reset();
		DriverStation.reportWarning("Gryo After Reset: " + getAngle(), false);
	}

	public double getAngle() {
		return -GYRO.getAngle();
	}

	/**
	 * Determine the top speed threshold: CRAWL - Lowest speed threshold Normal -
	 * Normal driving conditions SPRINT - Highest speed threshold
	 * 
	 * @link org.usfirst.frc.team4213.robot.RobotMap
	 */
	private double getThrottle() {
		if (controller.isCrawlToggle()) {
			return CRAWL_SPEED;
		} else if (controller.isSprintToggle()) {
			return SPRINT_SPEED; 
		} else {
			return NORMAL_SPEED;
		}
	}

	private void invert() {
		inverted = inverted * -1;
	}

	private double getLeftEncoderTics() {
		return leftFrontMotor.getSelectedSensorPosition();
	}

	private double getRightEncoderTics() {
		return rightFrontMotor.getSelectedSensorPosition();
	}

	public void printRightEncoder() {
		System.out.println(getRightEncoderTics() + " RightEncoder");
		SmartDashboard.putNumber("right encoder", getRightEncoderTics());
	}
	public void printLeftEncoder() {
		System.out.println(getLeftEncoderTics() + " LeftEncoder");
		SmartDashboard.putNumber("left encoder", getLeftEncoderTics());
	}
	
	public double encoderDifference() {
		return (getRightEncoderTics() - getLeftEncoderTics());
	}

	public double getEncoderTics() {
		// return (getRightEncoderTics() + getLeftEncoderTics()) / 2;
		// return getRightEncoderTics();
		return getLeftEncoderTics();
	}

}
