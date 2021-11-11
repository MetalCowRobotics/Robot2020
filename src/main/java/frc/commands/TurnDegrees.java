package frc.commands;

import frc.lib14.MCRCommand;
import frc.lib14.PIDController;
import frc.lib14.UtilityMethods;
import frc.robot.RobotDashboard;
import frc.robot.RobotMap;
import frc.systems.DriveTrain;

public class TurnDegrees implements MCRCommand {
	private double degrees;
	private double setPoint = 0;
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private RobotDashboard dashboard = RobotDashboard.getInstance();
	private PIDController driveController;
	private int currentState = 0;
	private final int IDLE = 0;
	private final int ACTIVE = 1;
	private final int DONE = 2;
	private int numMatches = 0;

	public TurnDegrees(double degrees) {
		super();
		this.degrees = degrees;

	}

	public void run() {
		switch (currentState) {

		case IDLE:
			driveTrain.resetGyro();
			setPoint = driveTrain.getAngle() + degrees;
			System.out.println(("TurnDegrees SetPoint:" + setPoint));
			driveController = new PIDController(setPoint, RobotMap.TurnDegrees.kP, RobotMap.TurnDegrees.kI,	RobotMap.TurnDegrees.kD, RobotMap.TurnDegrees.Iz);
			System.out.println("Before line 35  " + driveController.calculateAdjustment(setPoint));
			// driveTrain.arcadeDrive(RobotMap.TurnDegrees.TOP_SPEED,
			// driveController.calculateAdjustment(setPoint));
			System.out.println("After line 35");
			currentState = ACTIVE;
			break;
		case ACTIVE:
			driveController.set_kP(dashboard.getTurnKP());

			driveController.set_kD(dashboard.getTurnKD());

			double currentAngle = driveTrain.getAngle();
			if (Math.abs(currentAngle) >= Math.abs(.95 * degrees)) {
			//if (Math.abs(currentAngle) >= Math.abs(degrees)) {

				driveController.set_kI(dashboard.getTurnKI());
			} else {
				driveController.set_kI(0);
			}

			if (Math.abs(setPoint - currentAngle) < RobotMap.TurnDegrees.VARIANCE) {
				numMatches++;
			} else {
				numMatches = 0;
			}
			// }
			// // logger.info("======== turn on target !!! =========");
			// System.out.println("angle:" + driveTrain.getAngle());
			// driveTrain.stop();
			// if (numMatches > 20)
			// currentState = DONE;
			// } else {
			// numMatches = 0;
			double correction = driveController.calculateAdjustment(currentAngle);
			// driveTrain.arcadeDrive(RobotMap.TurnDegrees.TOP_SPEED,
			// limitCorrection(correction, RobotMap.TurnDegrees.MAX_ADJUSTMENT));
			// if (UtilityMethods.between(currentAngle, setPoint -
			// RobotMap.TurnDegrees.SLOW_VARIANCE,
			// setPoint + RobotMap.TurnDegrees.SLOW_VARIANCE)) {
			// driveTrain.arcadeDrive(RobotMap.TurnDegrees.TOP_SPEED,
			// limitCorrection(correction, RobotMap.TurnDegrees.SLOW_ADJUSTMENT));
			// // logger.info("Turn Degrees Slow");
			// } else {
			if (numMatches > 5) {
				driveTrain.stop();
				currentState = DONE;
			} else {
				if (UtilityMethods.between(currentAngle, setPoint - RobotMap.TurnDegrees.SLOW_VARIANCE,
						setPoint + RobotMap.TurnDegrees.SLOW_VARIANCE)) {
					driveTrain.arcadeDrive(RobotMap.TurnDegrees.TOP_SPEED,
							limitCorrection(correction, RobotMap.TurnDegrees.SLOW_ADJUSTMENT));
				} else {
					driveTrain.arcadeDrive(RobotMap.TurnDegrees.TOP_SPEED,
							limitCorrection(correction, RobotMap.TurnDegrees.MAX_ADJUSTMENT));
				}
			}
			System.out.println("correction:" + correction);
			// logger.info("Turn Degrees Fast");
			// }
			// logger.info("Angle:" + driveTrain.getAngle());
			// logger.info("correction:" + correction);
			// }
			break;
		case DONE:
			break;
		}
	}

	protected double limitCorrection(double correction, double maxAdjustment) {
		if (Math.abs(correction) > Math.abs(maxAdjustment))
			return UtilityMethods.copySign(correction, maxAdjustment);
			if (correction < .12){
				return .12;
			}
		return correction;
	}

	@Override
	public boolean isFinished() {
		return DONE == currentState;
	}
}