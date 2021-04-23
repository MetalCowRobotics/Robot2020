/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import frc.lib14.MCRCommand;
import frc.lib14.PIDController;
import frc.lib14.UtilityMethods;
import frc.robot.RobotDashboard;
import frc.systems.DriveTrain;

public class NewTurn implements MCRCommand {
    private double degrees;
    private double setPoint = 0;
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private RobotDashboard dashboard = RobotDashboard.getInstance();
    private PIDController driveController;
    private int numMatches = 0;
    private boolean firstTime = true;
    private boolean done = false;
    // constants
    private static final double TOP_SPEED = 0;
    private static final double VARIANCE = 5; //2 // .25
    private static final double MAX_ADJUSTMENT = .6;
    private static final double SLOW_VARIANCE = 15; // 10
    private static final double SLOW_ADJUSTMENT = .3;
    public static final double kP = 0.04; 
    public static final double kI = .008; 
    public static final double kD = .08;
    public static final double Iz = 5;

    // degrees = positive numbers turn right and negative numbers turn left
    public NewTurn(double degrees) {
        this.degrees = degrees;
    }

    public void run() {
        initialize();
        double currentAngle = driveTrain.getAngle();
        System.out.println("Current Angle:" + currentAngle);
        checkStatus(currentAngle);
        if (!done) {
            applyCorrection(currentAngle);
        }
    }

    private void applyCorrection(double currentAngle) {
        driveController.set_kP(dashboard.getTurnKP());
        driveController.set_kI(dashboard.getTurnKI());
        driveController.set_kD(dashboard.getTurnKD());
        driveController.set_Iz(dashboard.getTurnIz());
        double correction = driveController.calculateAdjustment(currentAngle);
        System.out.println("correction before limits" + correction);
        if (UtilityMethods.between(currentAngle, setPoint - SLOW_VARIANCE, setPoint + SLOW_VARIANCE)) {
            driveTrain.arcadeDrive(SLOW_ADJUSTMENT, limitCorrection(correction, SLOW_ADJUSTMENT));
        } else {
            driveTrain.arcadeDrive(MAX_ADJUSTMENT, limitCorrection(correction, MAX_ADJUSTMENT));
        }
    }

    private void checkStatus(double currentAngle) {
        System.out.println(numMatches);
        if (Math.abs(setPoint - currentAngle) < VARIANCE) {
            numMatches++;
        } else if (numMatches > 0) {
            numMatches--; // =0
        }
        if (numMatches > 5) {//10
            driveTrain.stop();
            done = true;
        }
    }

    private void initialize() {
        if (firstTime) {
            firstTime = false;
            driveTrain.resetGyro();
            setPoint = driveTrain.getAngle() + degrees;
            System.out.println(("TurnDegrees SetPoint:" + setPoint));
            driveController = new PIDController(setPoint, dashboard.getTurnKP(), dashboard.getTurnKI(), dashboard.getTurnKD(), dashboard.getTurnIz());
        }
    }

    protected double limitCorrection(double correction, double maxAdjustment) {
        if (Math.abs(correction) > Math.abs(maxAdjustment))
            return UtilityMethods.copySign(correction, maxAdjustment);
        return correction;
    }

    @Override
    public boolean isFinished() {
        return done;
    }

}
