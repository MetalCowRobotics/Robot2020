/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.systems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class ColorWheel {
    private static final ColorWheel instance = new ColorWheel();
    private static MCR_SRX wheelMotor = new MCR_SRX(RobotMap.ColorWheel.Motor);// needs to be mapped

    private ColorWheel() {

    }

    public static ColorWheel getInstance() {
        return instance;
    }

    I2C.Port port = I2C.Port.kOnboard;
    ColorSensorV3 sensor = new ColorSensorV3(port);
    ColorMatch color = new ColorMatch();

    final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    private boolean rotate = false;
    private ColorMatchResult targetColor;
    private boolean onColor = false;
    private int matches = 0;

    public void ColorWheelInit() {
        // testing
        color.addColorMatch(kBlueTarget);
        color.addColorMatch(kGreenTarget);
        color.addColorMatch(kRedTarget);
        color.addColorMatch(kYellowTarget);
        // Magazine.getInstance();
    }

    public void ColorSensorValues() {

        //
        // color sensor testing
        //
        SmartDashboard.putNumber("red", sensor.getRed());
        SmartDashboard.putNumber("green", sensor.getGreen());
        SmartDashboard.putNumber("blue", sensor.getBlue());
        SmartDashboard.putNumber("proximity", sensor.getProximity());
        ColorMatchResult result = color.matchClosestColor(sensor.getColor());
        SmartDashboard.putNumber("confidence", result.confidence);
        if (result.color == kRedTarget) {
            SmartDashboard.putString("color", "red");
        } else if (result.color == kGreenTarget) {
            SmartDashboard.putString("color", "green");
        } else if (result.color == kBlueTarget) {
            SmartDashboard.putString("color", "blue");
        } else if (result.color == kYellowTarget) {
            SmartDashboard.putString("color", "yellow");
        }
    }
    public void run(){
        if(rotate){
            if(onColor && targetColor != color.matchClosestColor(sensor.getColor()) ){
                onColor = false;
                if(matches >= 6){
                    wheelMotor.stopMotor();
                    rotate = false;
                }
            } if(!onColor && targetColor ==  color.matchClosestColor(sensor.getColor()) ){
                onColor = true; 
                matches++;
            }
        }
        
    }
    public void rotate3Times(){
        rotate = true;
        targetColor =  color.matchClosestColor(sensor.getColor());
        wheelMotor.set(.4);
        onColor = true; 
        matches = 1;

    }
}