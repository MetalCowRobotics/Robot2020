package frc.commands;

import frc.robot.RobotMap;
import frc.systems.DriveTrain;
import java.lang.Math;
import frc.lib14.UtilityMethods;
import frc.lib14.MCRCommand;

public class CurvatureDrive implements MCRCommand {
    int direction = 1;
    double angle = 0;
    double radius = 0;
    double startAngle = 0;
    double endAngle = 0;
    DriveTrain driveTrain = DriveTrain.getInstance();
    boolean firstTime = true;
    boolean isFinished = false;
    double outerSpeed = 0.0;
    final int AXLE_WIDTH = 30; //get actual width from robot 
    final double MAX_SPEED = 0.9;
// Direction: 1 is right and -1 is left
    public CurvatureDrive(String direction, double angle, double radius, double maximumSpeed) {
        this.outerSpeed = maximumSpeed;
        this.angle = angle;
        this.radius = radius - (AXLE_WIDTH / 2);
        if (direction.toUpperCase() == "LEFT") {
            this.direction = -1;
        } else if (direction.toUpperCase() == "RIGHT") {
            this.direction = 1;
        }
        // driveTrain.resetGyro();
    } 

    public void run() {
        if (firstTime) {
            driveTrain.calibrateGyro();
            this.startAngle = driveTrain.getAngle();
            this.endAngle = startAngle + angle * this.direction;

            double ratio = radius / (radius + AXLE_WIDTH);
            double outerRate = UtilityMethods.absMax(MAX_SPEED, outerSpeed);
            double innerRate = outerRate * ratio;
            if (direction == -1) {
                driveTrain.tankDrive(innerRate, outerRate);
            } else {
                driveTrain.tankDrive(outerRate, innerRate);
            }
            firstTime = false;
        }
        if (UtilityMethods.between(driveTrain.getAngle(), endAngle, endAngle + 10)) {
            driveTrain.stop();
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
} 