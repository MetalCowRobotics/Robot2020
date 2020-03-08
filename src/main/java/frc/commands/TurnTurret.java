package frc.commands;

import frc.lib14.MCRCommand;
import frc.systems.Turret;

public class TurnTurret implements MCRCommand {
    Turret turret = Turret.getInstance();
    private boolean firstTime = true;
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
            System.out.println("target:" + targetTics);
        }
        double error = turret.getTurretPosition() - targetTics;
        System.out.println("error:"+error);
        if (Math.abs(error) < 5) {
            done = true;
            turret.stopTurret();
            System.out.print("  done");
        } else if (error < 0) {
            System.out.print("  left");
            turret.turnTurret(-.4);
            // turret.setTurretPower(.4);
        } else {
            System.out.print("  right");
            // turret.setTurretPower(-.4);
            turret.turnTurret(.4);
        }
    }

    @Override
    public boolean isFinished() {
        return done;
    }
    
}
