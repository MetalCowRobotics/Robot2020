package frc.commands;

import frc.robot.RobotMap;
import frc.systems.DriveTrain;

import java.lang.Math;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.UtilityMethods;
import frc.lib14.MCRCommand;
import java.lang.Math;

public class CurvatureDrive implements MCRCommand {
    int direction = 1;
    double angle = 0;
    double radius = 0;
    double startAngle;
    double endAngle;
    DriveTrain driveTrain = DriveTrain.getInstance();
    boolean firstTime = true;
    boolean isFinished = false;
    double outerSpeed = 0.0;
    final int AXLE_WIDTH = 30; //get actual width from robot 
    final double MAX_SPEED = 0.8;
    double left, right = 0;
    int targetTics;
    int currentTics;
    DriveTrain drive = DriveTrain.getInstance();
// Direction: 1 is right and -1 is left
    public CurvatureDrive(String direction, double angle, double radius, double maximumSpeed) {
        this.outerSpeed = maximumSpeed;
        this.angle = angle;
        this.radius = radius - (AXLE_WIDTH / 2);
        this.currentTics = drive.getEncoderTics();
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
            this.endAngle = this.startAngle + angle * this.direction;

            double ratio = radius / (radius + AXLE_WIDTH);
            double outerRate = UtilityMethods.absMax(MAX_SPEED, outerSpeed);
            double innerRate = outerRate * ratio;
            if (direction == -1) {
                driveTrain.tankDrive(innerRate, outerRate);
                left = innerRate;
                right = outerRate;

                angle = Math.toRadians(angle);
                double targetLength = radius * angle;
                int ticsAddition = targetTics(targetLength);
                this.targetTics = currentTics + ticsAddition;
            } else {
                driveTrain.tankDrive(outerRate, innerRate);
                left = outerRate;
                right = innerRate;
            }
            
            System.out.println("startAngle:"+this.startAngle+"   targetAngle:"+this.endAngle );

            firstTime = false;
        }
        System.out.println("currentAngle:"+driveTrain.getAngle()+"   onTarget:"+UtilityMethods.between(driveTrain.getAngle(), endAngle - 10, endAngle + 10) );
        SmartDashboard.putNumber("curvatureDrivebetween", endAngle - driveTrain.getAngle());
        if (isFinished()) {
            return;
        }
        if (UtilityMethods.between(driveTrain.getAngle(), endAngle - 10, endAngle + 10)) {
            driveTrain.stop();
            isFinished = true;
            System.out.println("Curvature Drive is finished");
        } else {
            driveTrain.tankDrive(left, right);
        }

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    private int targetTics(double target_inches) {
        return (int) ((target_inches / RobotMap.DriveWithEncoder.INCHES_PER_ROTATION)
                * RobotMap.DriveWithEncoder.TICS_PER_ROTATION);
    }
} 