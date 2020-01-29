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

    public boolean raisingHood = false;
    public boolean loweringHood = false;
  

    private Hood() {

    }

    public static Hood getInstance() {
        return instance;
    }

    public void run() {
        if (isHoodAtBottomPos() || isHoodAtTopPos()){
            hood.stopMotor();
        }
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
}
