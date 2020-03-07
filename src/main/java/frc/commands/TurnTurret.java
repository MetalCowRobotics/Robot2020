package frc.commands;

import frc.lib14.MCRCommand;
import frc.systems.Turret;

public class TurnTurret implements MCRCommand {
    Turret turret = Turret.getInstance();
    private boolean firstTime = false;
    private int moveTics;
    private int targetTics;
    private boolean done = false;
    
    //positive numbers turn left, negative number turn right
    public TurnTurret (int tics) {
        moveTics = tics;
    }

    @Override
    public void run() {
        if (firstTime) {
            firstTime = false;
            targetTics = turret.getTurretPosition() + moveTics;
        }
        double error = turret.getTurretPosition() - targetTics;
        if (Math.abs(error) < 5) {
            done = true;
            turret.stopTurret();
        } else if (error < 0) {
            turret.turnTurret(.2);
        } else {
            turret.turnTurret(-.2);
        }
    }

    @Override
    public boolean isFinished() {
        return done;
    }
    
}
