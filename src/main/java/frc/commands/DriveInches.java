package frc.commands;

import frc.lib14.MCRCommand;
import frc.lib14.PIDController;
import frc.lib14.UtilityMethods;
import frc.robot.RobotMap;
import frc.systems.DriveTrain;

public class DriveInches implements MCRCommand {
    private static final DriveTrain driveTrain = DriveTrain.getInstance();
    private boolean firstTime = true;
    private boolean finished = false;
    private double startTics;
    private double absTargetTics;
    public int dir = 1;
    protected PIDController driveController;

    // constants
    public static final int TICS_PER_ROTATION = RobotMap.Drivetrain.TICS_PER_ROTATION;
    public static final double INCHES_PER_ROTATION = RobotMap.Drivetrain.INCHES_PER_ROTATION;
    public static final double SLOW_DOWN_TICS = (12 / INCHES_PER_ROTATION) * TICS_PER_ROTATION;
    public static final double TOP_SPEED = .9;//.75
    public static final double BOTTOM_SPEED = .6;
    private double kP = .4;
    private double kI = 0;
    private double kD = .1;
    private final double MAX_TURN_ADJUSTMENT = .6;

    // direction 1 = forward and -1 = backwards
    public DriveInches(int direction, double targetInches) {
        dir = direction;
        absTargetTics = inchesToTics(targetInches);
    }

    public void run() {
        if (firstTime) {
            firstTime = false;
            driveTrain.resetGyro();
            startTics = driveTrain.getEncoderTics();
            driveController = new PIDController(driveTrain.getAngle(), kP, kI, kD);
            System.out.println("Starting Drive Inches");
        }

        if (absTicsTravelled() < absTargetTics & !finished) {
            System.out.println("DriveInches driving");
            driveTrain.arcadeDrive(calculateSpeed() * dir, getCorrection() * dir);
        } else {
            System.out.println("DriveInches stopping");
            end();
        }
        System.out.println("gyro:" + driveTrain.getAngle() + "  | correction:" + getCorrection());
        System.out.println("target:" + absTargetTics + "  |  ticsTravelled:" + absTicsTravelled() + "  |  tics:" + driveTrain.getEncoderTics() + "  |  startTics:" + startTics);
    }

    private double inchesToTics(double inches) {
        return (inches / RobotMap.DriveWithEncoder.INCHES_PER_ROTATION) * RobotMap.DriveWithEncoder.TICS_PER_ROTATION;
    }

    private void end() {
        driveTrain.stop();
        finished = true;
    }

    private double absTicsTravelled() {
        return Math.abs(driveTrain.getEncoderTics() - startTics);
    }

    private double calculateSpeed() {
        if (absTicsTravelled() > absTargetTics - SLOW_DOWN_TICS)
            return BOTTOM_SPEED;
        return TOP_SPEED;
    }

    private double getCorrection() {
        return limitCorrection(driveController.calculateAdjustment(driveTrain.getAngle()), MAX_TURN_ADJUSTMENT);
    }

    private double limitCorrection(double correction, double maxAdjustment) {
        // if (Math.abs(correction) > Math.abs(maxAdjustment))
        //     return UtilityMethods.copySign(correction, maxAdjustment);
        // return correction;
        return UtilityMethods.limit(correction, -maxAdjustment, maxAdjustment);
    }

    public boolean isFinished() {
        return finished;
    }
}
