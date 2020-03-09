package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.lib14.MCR_SPX;
import frc.lib14.MCR_SRX;
import frc.robot.RobotDashboard;
import frc.robot.RobotMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ColorWheel {
    // private static MCR_SRX colorWheelMotor = new MCR_SRX(RobotMap.ColorWheel.Motor);// TODO: needs to be mapped
    private static MCR_SPX colorWheelMotor = new MCR_SPX(RobotMap.ColorWheel.Motor);
    private static I2C.Port port = I2C.Port.kOnboard;
    private static ColorSensorV3 sensor = new ColorSensorV3(port);
    private RobotDashboard dashboard = RobotDashboard.getInstance();

    private HashMap<Color, ColorMatchResult> colorWheelMap = new HashMap<>();
    private ColorMatch colorMatcher = new ColorMatch();
    final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429); // TODO: External config this
    final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113); // TODO: Should this be a range?

    private boolean rotateWheel = false;
    private boolean goToMatch = false;
    private Color targetColor;
    private Color previousColor;
    private int changes = 0;
    private boolean changed = false;

    private static final double COLOR_WHEEL_SPEED = .4;

    // singleton instance
    private static final ColorWheel instance = new ColorWheel();

    private ColorWheel() {
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
        colorWheelMap.put(kRedTarget, new ColorMatchResult(kBlueTarget, 100));
        colorWheelMap.put(kGreenTarget, new ColorMatchResult(kYellowTarget, 100));
        colorWheelMap.put(kBlueTarget, new ColorMatchResult(kRedTarget, 100));
        colorWheelMap.put(kYellowTarget, new ColorMatchResult(kGreenTarget, 100));
        colorWheelMotor.configFactoryDefault();
		colorWheelMotor.setNeutralMode(NeutralMode.Coast);
    }

    public static ColorWheel getInstance() {
        return instance;
    }

    public void run() {
        if (rotateWheel) {
            rotate();
        } else if (goToMatch) {
            rotateToColor();
        } else {
            stopColorWheel();
        }
        //TODO how do i do the reverse of goal color to target color so i can have i see this color do field sees this color
        dashboard.pushCurrentColor(readCurrentColor().toString(), null);
    }

    public void rotate3Times() {
        rotateWheel = true;
        targetColor = readCurrentColor();
        previousColor = targetColor;
        colorWheelMotor.set(COLOR_WHEEL_SPEED);
        changes = 1;
        changed = false;
        goToMatch = false; // make sure the other mission is off
    }

    public void goToTargetColor() {
        goToMatch = true;
        try {
            Color goalColor = getGoalColor();
            targetColor = getGoalColorWithOffset(goalColor);
            colorWheelMotor.set(getTurnDirection(readCurrentColor(), targetColor) * COLOR_WHEEL_SPEED);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            goToMatch = false;
        }
        rotateWheel = false; // make sure the other mission is off
    }

    public void stopColorWheel() {
        colorWheelMotor.stopMotor();
        rotateWheel = false;
        goToMatch = false;
    }

    public void override(double speed) {
        colorWheelMotor.set(speed);
    }

    private void rotate() {
        if (readCurrentColor() != previousColor) {
            previousColor = readCurrentColor();
            changed = true;
        }
        SmartDashboard.putBoolean("Changed Boolean", changed);
        if (readCurrentColor() == targetColor && changed) {
            changes = changes + 1;
        }
        if (changes >= 8) {
            stopColorWheel();
        }
        changed = false;
    }

    private void rotateToColor() {
        if (targetColor == readCurrentColor()) {
            stopColorWheel();
        }
    }

    private ColorMatchResult getGoalColorMatch() throws IOException {
        String fmsColor = DriverStation.getInstance().getGameSpecificMessage();
        if (fmsColor.length() > 0) {
            dashboard.pushTargetColor(fmsColor);
            switch (fmsColor.charAt(0)) {
            case 'B':
                return new ColorMatchResult(kBlueTarget, 100);
            case 'G':
                return new ColorMatchResult(kGreenTarget, 100);
            case 'R':
                return new ColorMatchResult(kRedTarget, 100);
            case 'Y':
                return new ColorMatchResult(kYellowTarget, 100);
            }
        }
        throw new IOException("Color Wheel Data not Provided");
    }

    private Color getGoalColor() throws IOException {
        String fmsColor = DriverStation.getInstance().getGameSpecificMessage();
        if (fmsColor.length() > 0) {
            dashboard.pushTargetColor(fmsColor);
            switch (fmsColor.charAt(0)) {
            case 'B':
                return kBlueTarget;
            case 'G':
                return kGreenTarget;
            case 'R':
                return kRedTarget;
            case 'Y':
                return kYellowTarget;
            }
        }
        throw new IOException("Color Wheel Data not Provided");
    }

    private ColorMatchResult readCurrentColorMatch() {
        dashboard.pushColorSensor(sensor);
        ColorMatchResult result = colorMatcher.matchClosestColor(sensor.getColor());
        dashboard.pushColorMatch(result);
        return result;
    }

    private Color readCurrentColor() {
        dashboard.pushColorSensor(sensor);
        ColorMatchResult result = colorMatcher.matchClosestColor(sensor.getColor());
        dashboard.pushColorMatch(result);
        return result.color;
    }

    private ColorMatchResult getGoalColorMatchWithOffset(ColorMatchResult fmsColor) {
        return colorWheelMap.get(fmsColor.color);
    }

    private Color getGoalColorWithOffset(Color fmsColor) {
        //TODO make this hash Color Color
        return colorWheelMap.get(fmsColor).color;
    }

    private int getTurnDirection(Color currentColor, Color goalColor) {
        ArrayList<String> colorWheelValues = new ArrayList<String>();
        colorWheelValues.add("red");
        colorWheelValues.add("yellow");
        colorWheelValues.add("blue");
        colorWheelValues.add("green");
        int currentIndex = 999;
        int goalIndex = 999;
        currentIndex = colorWheelValues.indexOf(getColorString(currentColor));
        // if (currentColor == kRedTarget) {
        // currentIndex = colorWheelValues.indexOf("red");
        // } else if (currentColor == kYellowTarget) {
        // currentIndex = colorWheelValues.indexOf("yellow");
        // } else if (currentColor == kBlueTarget) {
        // currentIndex = colorWheelValues.indexOf("blue");
        // } else if (currentColor == kGreenTarget) {
        // currentIndex = colorWheelValues.indexOf("green");
        // }
        goalIndex = colorWheelValues.indexOf(getColorString(goalColor));
        // if (goalColor == kRedTarget) {
        // goalIndex = colorWheelValues.indexOf("red");
        // } else if (goalColor == kYellowTarget) {
        // goalIndex = colorWheelValues.indexOf("yellow");
        // } else if (goalColor == kBlueTarget) {
        // goalIndex = colorWheelValues.indexOf("blue");
        // } else if (goalColor == kGreenTarget) {
        // goalIndex = colorWheelValues.indexOf("green");
        // }
        SmartDashboard.putNumber("current Color Index", currentIndex);
        SmartDashboard.putNumber("Target Color Index", goalIndex);
        if (Math.abs(currentIndex - goalIndex) == 1 && currentIndex > goalIndex) {
            return 1;
        } else {
            return -1;
        }
    }

    private String getColorString(Color color) {
        if (kRedTarget == color) {
            return "red";
        }
        if (kGreenTarget == color) {
            return "green";
        }
        if (kBlueTarget == color) {
            return "blue";
        }
        if (kYellowTarget == color) {
            return "yellow";
        }
        return "unknown";
    }
}