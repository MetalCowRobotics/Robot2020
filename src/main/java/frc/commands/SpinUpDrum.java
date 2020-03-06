package frc.commands;

import frc.lib14.MCRCommand;
import frc.systems.Shooter;

public class SpinUpDrum implements MCRCommand {
    private Shooter shooter = Shooter.getInstance();
    private boolean firstTime = true;
    private boolean done = false;

    @Override
    public void run() {
        if (firstTime) {
            firstTime = false;
            shooter.runDrum();
            done = true;
        }
    }

    @Override
    public boolean isFinished() {
        return done;
    }
}
