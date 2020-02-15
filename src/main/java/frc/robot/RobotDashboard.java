package frc.robot;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.PIDController;

public class RobotDashboard {
	private static final Logger logger = Logger.getLogger(RobotDashboard.class.getName());
	private static final RobotDashboard ourInstance = new RobotDashboard();
	private SendableChooser<AutoMission> autonomousAction = new SendableChooser<>();
	private SendableChooser<AutoPosition> startingPosition = new SendableChooser<>();
	private DriverStation driverStation;
	private PowerDistributionPanel pdp;

	public enum AutoMission {
		AUTOMODE_NONE, 
		AUTOMODE_SHOOT_N_GO, 
		AUTOMODE_SHOOT_N_GATHER
	}

	public enum AutoPosition {
		AUTOMODE_LEFT_OF_TARGET, 
		AUTOMODE_CENTER, 
		AUTOMODE_RIGHT_OF_TARGET
	}

	public static RobotDashboard getInstance() {
		return ourInstance;
	}

	private RobotDashboard() {
		logger.setLevel(RobotMap.LogLevels.robotDashboardClass);
	}

	public void initializeDashboard() {
		// driverStation = edu.wpi.first.wpilibj.DriverStation.getInstance();
		// // boolean isFMSAttached = driverStation.isFMSAttached();
		// if (!isFMSAttached) {
		// pdp = new PowerDistributionPanel();
		// pdp.resetTotalEnergy();
		// }
		// pushElevatorPID();
	}

	public void pushAuto() {
		autonomousAction.setDefaultOption("shoot and go", AutoMission.AUTOMODE_SHOOT_N_GO);
		autonomousAction.addOption("shoot and gather", AutoMission.AUTOMODE_SHOOT_N_GATHER);
		autonomousAction.addOption("no autonomous", AutoMission.AUTOMODE_NONE);
		startingPosition.setDefaultOption("center position", AutoPosition.AUTOMODE_CENTER);
		startingPosition.addOption("left position", AutoPosition.AUTOMODE_LEFT_OF_TARGET);
		startingPosition.addOption("right position", AutoPosition.AUTOMODE_RIGHT_OF_TARGET);
		SmartDashboard.putData("missions", autonomousAction);
		SmartDashboard.putData("starting Position", startingPosition);		
	}

	public AutoMission getAutoMission() {
		return autonomousAction.getSelected();
	}

	public AutoPosition getStartingPosition() {
		return startingPosition.getSelected();
	}

	public void pushShooterPIDValues(double P, double I, double D, double Iz) {
		SmartDashboard.putNumber("SkP", P);
		SmartDashboard.putNumber("SkI", I);
		SmartDashboard.putNumber("SkD", D);
		SmartDashboard.putNumber("SIz", Iz);
	}

	public void pushGyro(double angle) {
		SmartDashboard.putNumber("Gyro Reading", angle);
	}

	public double getIntakeEjectSpeed() {
		// return SmartDashboard.getNumber("IntakeEjectSpeed", 0);
		return 0;
	}

	public void pushLeftEncoder(double leftTics) {
		SmartDashboard.putNumber("driveLeftTics", leftTics);
	}

	public void pushRightEncoder(double rightTics) {
		SmartDashboard.putNumber("driveRight", rightTics);
	}

	public void pushSpeed() {
		// SmartDashboard.putNumber("crawlSpeed", RobotMap.Drivetrain.CRAWL_SPEED);
		// SmartDashboard.putNumber("sprintSpeed", RobotMap.Drivetrain.SPRINT_SPEED);
		// SmartDashboard.putNumber("normalSpeed", RobotMap.Drivetrain.NORMAL_SPEED);
	}

	// Turn commands
	public void pushTurnPID() {
		SmartDashboard.putNumber("TkP", RobotMap.TurnDegrees.kP);
		SmartDashboard.putNumber("TkI", RobotMap.TurnDegrees.kI);
		SmartDashboard.putNumber("TkD", RobotMap.TurnDegrees.kD);
		SmartDashboard.putNumber("TIz", RobotMap.TurnDegrees.Iz);
	}

	public double getTurnKP() {
		return SmartDashboard.getNumber("TkP", RobotMap.TurnDegrees.kP);
	}

	public double getTurnKI() {
		return SmartDashboard.getNumber("TkI", RobotMap.TurnDegrees.kI);
	}

	public double getTurnKD() {
		return SmartDashboard.getNumber("TkD", RobotMap.TurnDegrees.kD);
	}

	public void pushFieldMode(Boolean Mode) {
		SmartDashboard.putBoolean("Field Mode", Mode);
	}

}
