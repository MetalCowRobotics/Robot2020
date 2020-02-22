package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Hood {
    private static MCR_SRX hood = new MCR_SRX(RobotMap.Hood.HOOD_MOTOR);
    private static DigitalInput hoodUp = new DigitalInput(RobotMap.Hood.HOOD_UP);
    private static DigitalInput hoodDown = new DigitalInput(RobotMap.Hood.HOOD_DOWN);
    private static final Hood instance = new Hood();

    private boolean raisingHood = false;
    private boolean loweringHood = false;
    private boolean firstTime = true;

    private Hood() {

    }

    public static Hood getInstance() {
        return instance;
    }

    public void run() {
        if (firstTime) {
            firstTime = false;
            //set starting encoder reading so everything will be based on that
        }
        if (isHoodAtBottomPos() || isHoodAtTopPos()){
            hood.stopMotor();
        }
        //keep moving the hood until it is at position
    }

    public void raiseHood() {
        if (!isHoodAtTopPos()) {
            hood.set(RobotMap.Hood.HOOD_SPEED);
            SmartDashboard.putString("raiseHood", "running");
        } else {
            hood.stopMotor();
            SmartDashboard.putString("raiseHood", "stopped");
        }
    }

    private boolean isHoodAtTopPos() {
        return !hoodUp.get();
    }

    public void lowerHood() {
        if (!isHoodAtBottomPos()) {
            hood.set(-RobotMap.Hood.HOOD_SPEED);
        } else {
            hood.stopMotor();
        }
    }

    private boolean isHoodAtBottomPos() {
        return !hoodDown.get();
    }

    public void setFarShot() {

    }

    public void setTenFoot() {

    }

    public void setSafeZone() {

    }

}
