package frc.systems;

import java.util.logging.Logger;

import frc.lib14.UtilityMethods;
import frc.lib14.XboxControllerMetalCow;
import frc.robot.RobotDashboard;
import frc.robot.RobotMap;

public class MasterControls {
	private static final Logger logger = Logger.getLogger(MasterControls.class.getName());
	private static final double throttleVariance = .14;
	private static final MasterControls instance = new MasterControls();

	private static final XboxControllerMetalCow driver = new XboxControllerMetalCow(RobotMap.DriverController.USB_PORT);
	private static final XboxControllerMetalCow operator = new XboxControllerMetalCow(
			RobotMap.OperatorController.USB_PORT);
	private boolean fieldMode = true;
	private static boolean xLast = false;

	private MasterControls() {
		// Intentionally Blank for Singleton
		logger.setLevel(RobotMap.LogLevels.masterControlsClass);
	}

	private static final RobotDashboard dash = RobotDashboard.getInstance();

	public static MasterControls getInstance() {
		return instance;
	}

	public boolean isSprintToggle() {
		return driver.getXButton();
	}

	public boolean isCrawlToggle() {
		return driver.getAButton();
	}

	public boolean invertDrive() {
		return driver.getYButtonPressed();
	}

	public double forwardSpeed() {
		return driver.getRT();
	}

	public double reverseSpeed() {
		return driver.getLT();
	}

	public double direction() {
		return driver.getLX();
	}

	public boolean raiseIntake() {
		if (UtilityMethods.between(operator.getPOV(), 0, 89)) {
			return true;
		}
		if (UtilityMethods.between(operator.getPOV(), 271, 360)) {
			return true;
		}
		return false;
	}

	public boolean lowerIntake() {
		if (UtilityMethods.between(operator.getPOV(), 91, 269)) {
			return true;
		}

		return false;
	}
	public boolean prepairToShoot (){
		if (operator.getRT() > .1){
			return true;
		}else {
			return false;
		}
	}

	public boolean intakeOnOff() {
		return operator.getYButtonPressed();
	}

	public void changeMode() {
		if (operator.getRawButtonPressed(7)) {
			fieldMode = !fieldMode;
		}
	}

	public boolean getFieldMode() {
		return fieldMode;
	}

	public double climbSpeed() {
		if (fieldMode) {
			return 0;
		}
		return operator.getLY();
	}

	public boolean target() {
		return operator.getYButtonPressed();
	}

	public boolean shootNow() {
		return operator.getAButton();
	}

	public boolean spinUpAndShoot() {
		return operator.getBButton();
	}
}