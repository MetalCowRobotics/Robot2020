package frc.commands;

import frc.lib14.MCRCommand;
import frc.lib14.UtilityMethods;
import frc.systems.Hood;
import frc.robot.RobotDashboard;

public class MoveHood implements MCRCommand {
    Hood hood = Hood.getInstance();
    private static RobotDashboard dashboard = RobotDashboard.getInstance();

    private boolean firstTime = true;
    private int moveTics;
    private int targetTics;
    private boolean done = false;
    
    //positive numbers turn left, negative number turn right
    public MoveHood (int tics) {
        targetTics = tics;
    }

    @Override
    public void run() {
        double currentTics = hood.getCurrentTics();
        //dashboard.pushHoodPositionText((int) currentTics); 
        double target = targetTics + dashboard.hoodCorrection();
        double error = (target - currentTics) / 100;
        error = UtilityMethods.absMin(error, 1);
        error = UtilityMethods.absMax(error, .3);
        if (Math.abs(target - currentTics) < 4) {
            hood.hood.stopMotor();
            done = true;
        } else {
            //TODO why setting speed to 0.4?
            hood.hood.set(UtilityMethods.copySign(error, .4));
            System.out.println("tracking to target");
        }
    }

    @Override
    public boolean isFinished() {
        return done;
    }
    
}
