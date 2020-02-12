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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

import java.io.IOException;
import java.util.HashMap;

/**
 * Add your docs here.
 */
public class ColorWheel {
    private static final ColorWheel instance = new ColorWheel();
    private static MCR_SRX wheelMotor = new MCR_SRX(RobotMap.ColorWheel.Motor);//TODO: needs to be mapped

    HashMap<Color, ColorMatchResult> colorWheelMap = new HashMap<>();

    I2C.Port port = I2C.Port.kOnboard;
    ColorSensorV3 sensor = new ColorSensorV3(port);
    ColorMatch colorMatcher = new ColorMatch();

    final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);  //TODO: External config this
    final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113); //TODO: Should this be a range?
    private boolean rotate = false;
    private boolean onColor = false;
    private int matches = 0;

    private ColorWheel() {
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);

        // colorWheelMap.put("R", "B");
        // colorWheelMap.put("G", "Y");
        // colorWheelMap.put("B", "R");
        // colorWheelMap.put("Y", "G");
        colorWheelMap.put(kRedTarget, new ColorMatchResult(kBlueTarget, 100));
        colorWheelMap.put(kGreenTarget, new ColorMatchResult(kYellowTarget, 100));
        colorWheelMap.put(kBlueTarget, new ColorMatchResult(kRedTarget, 100));
        colorWheelMap.put(kYellowTarget, new ColorMatchResult(kGreenTarget, 100));
    }
    private ColorMatchResult getGoalColor() throws IOException {
        String fmsColor = DriverStation.getInstance().getGameSpecificMessage();
        if (fmsColor.length()>0) {
            switch(fmsColor.charAt(0)) {
                case 'B': return new ColorMatchResult(kBlueTarget, 100);
                case 'G': return new ColorMatchResult(kGreenTarget, 100);
                case 'R': return new ColorMatchResult(kRedTarget, 100);
                case 'Y': return new ColorMatchResult(kYellowTarget, 100);
            }
        }
        throw new IOException("Color Wheel Data not Provided");
        
    }
    public static ColorWheel getInstance() {
        return instance;
    }

    public ColorMatchResult readCurrentColor() {
        // SmartDashboard.putNumber("red", sensor.getRed());
        // SmartDashboard.putNumber("green", sensor.getGreen());
        // SmartDashboard.putNumber("blue", sensor.getBlue());
        // SmartDashboard.putNumber("proximity", sensor.getProximity());
        ColorMatchResult result = colorMatcher.matchClosestColor(sensor.getColor());
        SmartDashboard.putNumber("confidence", result.confidence); //TODO: Move to smartdashboard method confidence/color 
        if (result.color == kRedTarget) {
            SmartDashboard.putString("color", "red");
        } else if (result.color == kGreenTarget) {
            SmartDashboard.putString("color", "green");
        } else if (result.color == kBlueTarget) {
            SmartDashboard.putString("color", "blue");
        } else if (result.color == kYellowTarget) {
            SmartDashboard.putString("color", "yellow");
        }
        else {
            SmartDashboard.putString("color", "unknown");
        }
        return result;
    }
    public void run(){

//if the controller is pressed for go to field color then
        try {
            goToColor(getGoalColor());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//elseif the controller is presse for rotating three times
        rotate3Times();
//finally --- stop and do nothing
        wheelMotor.stopMotor();





        
    }
    public ColorMatchResult getGoalColorWithOffset(ColorMatchResult fmsColor) {
        return colorWheelMap.get(fmsColor.color);
    }

    public void goToColor(ColorMatchResult targetColor) {
            ColorMatchResult offsetColor = getGoalColorWithOffset(targetColor);        
            if(offsetColor != readCurrentColor()){
                wheelMotor.set(0.4);
            } else { 
                wheelMotor.stopMotor();
            }
        }
    
    public void rotate3Times(){
        // rotate = true;
        // targetColor =  colorMatcher.matchClosestColor(sensor.getColor());
        // wheelMotor.set(.4);
        // onColor = true; 
        // matches = 1;
    }
}