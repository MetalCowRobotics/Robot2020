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
    int timeInSeconds = 0;
    double startAngle = 0;
    double endAngle = 0;
    DriveTrain driveTrain = DriveTrain.getInstance();
    boolean firstTime = true;
    boolean isFinished = false;

    public CurvatureDrive(String direction, double angle, double radius, int time) {
        this.angle = angle;
        this.radius = radius;
        this.timeInSeconds = time;
        driveTrain.resetGyro();
        driveTrain.calibrateGyro();
        this.startAngle = driveTrain.getAngle();
        if (direction.toUpperCase() == "LEFT") {
            this.direction = -1;
        } else if (direction.toUpperCase() == "RIGHT") {
            this.direction = 1;
        }
        this.endAngle = startAngle + angle * this.direction;
    } 

    public void run() {
        if (firstTime) {
            double innerRate = (Math.PI * 2 * radius) / timeInSeconds;
            double outerRate = (Math.PI * 2 * (radius + 30)) / timeInSeconds;
            int innerDivisor = Double.toString(innerRate).indexOf(".");
            int outerDivisor = Double.toString(outerRate).indexOf(".");
            innerRate = innerRate / UtilityMethods.absMax(innerDivisor, outerDivisor);
            outerRate = innerRate / UtilityMethods.absMax(innerDivisor, outerDivisor);
            if (direction == -1) {
                driveTrain.tankDrive(innerRate, outerRate);
            } else {
                driveTrain.tankDrive(outerRate, innerRate);
            }
            firstTime = false;
        }
        if (UtilityMethods.between(driveTrain.getAngle(), endAngle - 5, endAngle + 5)) {
            driveTrain.stop();
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
} 