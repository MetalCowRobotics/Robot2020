package frc.commands;

import frc.lib14.MCRCommand;
import frc.systems.Turret;

public class TurnTurret implements MCRCommand {
    Turret turret = Turret.getInstance();
    @Override
    public void run() {

    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
