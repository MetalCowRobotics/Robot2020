package frc.systems;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.lib14.UtilityMethods;
import frc.lib14.XboxControllerMetalCow;
import frc.robot.RobotDashboard;
import frc.robot.RobotMap;

public class MasterControls {
	private static final Logger logger = Logger.getLogger(MasterControls.class.getName());
	private static final XboxControllerMetalCow driver = new XboxControllerMetalCow(RobotMap.DriverController.USB_PORT);
	private static final XboxControllerMetalCow operator = new XboxControllerMetalCow(RobotMap.OperatorController.USB_PORT);
	private static final RobotDashboard dashboard = RobotDashboard.getInstance();
	private static final MasterControls instance = new MasterControls();

	private boolean fieldMode = true;

	private MasterControls() {
		// Intentionally Blank for Singleton
		logger.setLevel(RobotMap.LogLevels.masterControlsClass);
	}

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
		return isDpadUpperHalf(operator);
		// if (UtilityMethods.between(operator.getPOV(), 0, 89)) {
		// return true;
		// }
		// if (UtilityMethods.between(operator.getPOV(), 271, 360)) {
		// return true;
		// }
		// return false;
	}

	public boolean lowerIntake() {
		return isDpadLowerHalf(operator);
		// if (UtilityMethods.between(operator.getPOV(), 91, 269)) {
		// return true;
		// }
		// return false;
	}

	private boolean isDpadUpperHalf(XboxControllerMetalCow controller) {
		if (UtilityMethods.between(controller.getPOV(), 0, 89)) {
			return true;
		}
		if (UtilityMethods.between(controller.getPOV(), 271, 360)) {
			return true;
		}
		return false;
	}

	private boolean isDpadLowerHalf(XboxControllerMetalCow controller) {
		if (UtilityMethods.between(controller.getPOV(), 91, 269)) {
			return true;
		}
		return false;
	}

	public boolean intakeOnOff() {
		return operator.getBumperPressed(Hand.kRight);
	}

	public void changeMode() {
		if (operator.getRawButtonPressed(7)) {
			fieldMode = !fieldMode;
		}
		dashboard.pushFieldMode(fieldMode);
	}

	public boolean getFieldMode() {
		return fieldMode;
	}

	public double climbSpeed() {
		if (fieldMode) {
			return 0;
		}
		return UtilityMethods.deadZoneCalculation(operator.getLT(), 0.1);
	}

	public boolean prepairToShoot() {
		if (operator.getRT() > .1) {
			return true;
		}
		return false;
	}

	// public boolean target() {
	// return operator.getYButtonPressed();
	// }

	public boolean shootNow() {
		return operator.getAButton();
	}

	public boolean shootWhenReady() {
		return operator.getBButton();
	}

	public double hoodAdjustment() {
		return operator.getLY();
	}

	public double turretAdjustment() {
		return operator.getRX();
	}
}