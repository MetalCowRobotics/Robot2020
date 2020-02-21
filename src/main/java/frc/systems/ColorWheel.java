/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.RobotMap;
import frc.lib14.XboxControllerMetalCow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Add your docs here.
 */
public class ColorWheel {
    private static final int COLOR_WHEEL_MOTOR_MAPPING = 10;
    private static final ColorWheel instance = new ColorWheel();
    private static MCR_SRX colorWheelMotor = new MCR_SRX(COLOR_WHEEL_MOTOR_MAPPING); //TODO: needs to be mapped

    HashMap<Color, ColorMatchResult> colorWheelMap = new HashMap<Color, ColorMatchResult>();
    ArrayList<String> colorWheelValues = new ArrayList<String>();

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
    private int speed = 0;

    Color startColor;
    Color previousColor;
    int changes = 0;
    boolean firstTimeChange = true;
    boolean changed = false;

    XboxControllerMetalCow controller = new XboxControllerMetalCow(0);

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
    public void resetVariables() {
        rotate = false;
        onColor = false;
        matches = 0;
        changes = 0;
        firstTimeChange = true;
        changed = false;
    }
    private ColorMatchResult getGoalColor() throws IOException {                //gets FMS data for color
        String fmsColor = DriverStation.getInstance().getGameSpecificMessage();
        if (fmsColor.length() > 0) {
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

    public ColorMatchResult readCurrentColor() { // gets and returns color under our sensor
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
    public void run(boolean goToColor){ //calls each path
//if the controller is pressed for go to field color then
        if (goToColor) {
            SmartDashboard.putString("Color sensor value", "specific color");
            try {
                goToColor(getGoalColor());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
        // goToColor(new ColorMatchResult(kBlueTarget, 100));
            rotate3Times();
            SmartDashboard.putString("Color sensor value", "rotate 3 times");
        }
//elseif the controller is presse for rotating three times
        // rotate3Times();
//finally --- stop and do nothing
        // wheelMotor.stopMotor();
    }
    public ColorMatchResult getGoalColorWithOffset(ColorMatchResult fmsColor) {
        return colorWheelMap.get(fmsColor.color);
    }

    public void goToColor(ColorMatchResult targetColor) {
            ColorMatchResult offsetColor = getGoalColorWithOffset(targetColor);     
            if (offsetColor.color == kRedTarget) {
                SmartDashboard.putString("Color With Offset", "red");
            } else if (offsetColor.color == kGreenTarget) {
                SmartDashboard.putString("Color With Offset", "green");
            } else if (offsetColor.color == kBlueTarget) {
                SmartDashboard.putString("Color With Offset", "blue");
            } else if (offsetColor.color == kYellowTarget) {
                SmartDashboard.putString("Color With Offset", "yellow");
            } else {
                SmartDashboard.putString("Color With Offset", "unknown");
            }
            if(offsetColor.color != readCurrentColor().color){
                // wheelMotor.set(0.4);
                    //motor needs mapping
                    SmartDashboard.putNumber("direction", getTurnDirection(readCurrentColor().color, targetColor.color));
                    colorWheelMotor.set(getTurnDirection(readCurrentColor().color, targetColor.color) * .2);
                SmartDashboard.putString("Color Sensor Motor", "Moving");
            } else { 
                // wheelMotor.stopMotor();
                   //motor needs mapping
                   colorWheelMotor.stopMotor();
                SmartDashboard.putString("Color Sensor Motor", "Stopped");
            }
        }
    public void rotate3Times(){
        // rotate = true;
        // targetColor =  colorMatcher.matchClosestColor(sensor.getColor());
        // wheelMotor.set(.4);
        // onColor = true; 
        // matches = 1;
        if (firstTimeChange) {
            startColor = readCurrentColor().color;
            previousColor = readCurrentColor().color;
            firstTimeChange = false;
        }
        if (startColor == kRedTarget) {
            SmartDashboard.putString("Start Color", "red");
        } else if (startColor == kGreenTarget) {
            SmartDashboard.putString("Start Color", "green");
        } else if (startColor == kBlueTarget) {
            SmartDashboard.putString("Start Color", "blue");
        } else if (startColor == kYellowTarget) {
            SmartDashboard.putString("Start Color", "yellow");
        } else {
            SmartDashboard.putString("Start Color", "unknown");
        }
        if (readCurrentColor().color != previousColor) {
            previousColor = readCurrentColor().color;
            changed = true;
        }
        SmartDashboard.putBoolean("Changed Boolean", changed); 
        if (readCurrentColor().color == startColor && changed) {
            //move motor
            colorWheelMotor.set(0.2);
            SmartDashboard.putString("Color Sensor Motor", "Moving");
            changes = changes + 1;
        }
        SmartDashboard.putNumber("Changes", changes);
        if (changes >= 8) {
            colorWheelMotor.stopMotor();
            SmartDashboard.putString("Color Sensor Motor", "Stopped");
        }
        changed = false;
    }
    //clockwise
    // String[] colorWheelValues = {"red", "yellow", "blue", "green"};

    public int getTurnDirection(Color currentColor, Color goalColorWithOffset) {
        colorWheelValues.add("red");
        colorWheelValues.add("yellow");
        colorWheelValues.add("blue");
        colorWheelValues.add("green");
        int currentIndex = 999;
        int goalIndex = 999;
        if (currentColor == kRedTarget) {
            currentIndex = colorWheelValues.indexOf("red");
        } else if (currentColor == kYellowTarget) {
            currentIndex = colorWheelValues.indexOf("yellow");
        } else if (currentColor == kBlueTarget) {
            currentIndex = colorWheelValues.indexOf("blue");
        } else if (currentColor == kGreenTarget) {
            currentIndex = colorWheelValues.indexOf("green");
        }
        if (goalColorWithOffset == kRedTarget) {
            goalIndex = colorWheelValues.indexOf("red");
        } else if (goalColorWithOffset == kYellowTarget) {
            goalIndex = colorWheelValues.indexOf("yellow");
        } else if (goalColorWithOffset == kBlueTarget) {
            goalIndex = colorWheelValues.indexOf("blue");
        } else if (goalColorWithOffset == kGreenTarget) {
            goalIndex = colorWheelValues.indexOf("green");
        }
        SmartDashboard.putNumber("current Color Index", currentIndex);
        SmartDashboard.putNumber("Target Color Index", goalIndex);
        if (Math.abs(currentIndex-goalIndex) == 1 && currentIndex>goalIndex) {
            return 1;
        } else {
            return -1;
        }
        // if (currentIndex == goalIndex) {
        //     SmartDashboard.putString("if statement", "same indexes");
        //     return 0;
        // }
        // if (currentIndex == 0) {
        //     if (goalIndex == 3) {
        //         SmartDashboard.putString("if statement", "us at 0 and goal at 3");
        //         return -1;
        //     }
        // }
        // if (goalIndex == (currentIndex - 1)) {
        //     SmartDashboard.putString("if statement", "we are 1 behind");
        //     return -1;
        // } else {
        //     SmartDashboard.putString("if statement", "we are 1 ahead");
        //     return 1;
            
        // }

    }
    public void override(double speed) {
        if (controller.getRB()) {
            colorWheelMotor.set(-0.2 * speed);
        } else if (controller.getLB()) {
            colorWheelMotor.set(0.2 * speed);
        } else {
            colorWheelMotor.set(0);
        }
    }
}