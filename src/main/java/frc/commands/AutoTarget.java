package frc.commands;

import frc.lib14.MCRCommand;
import frc.robot.RobotDashboard;

public class AutoTarget implements MCRCommand{
    private boolean firstTime = true;
    private boolean autoTarget = true;

    public AutoTarget(boolean autoOn) {
        autoTarget = autoOn;
    }

    @Override
    public void run() {
        if (firstTime) {
            firstTime = false;
            RobotDashboard.getInstance().pushAutoTargeting(autoTarget);
        }
    }

    @Override
    public boolean isFinished() {
        return !firstTime;
    }

}
