package frc.commands;

import frc.lib14.MCRCommand;
import frc.systems.Intake;

public class DeployIntake implements MCRCommand {
    Intake intake = Intake.getInstance();
    boolean done = false;
    @Override
    public void run() {
        intake.lowerIntake();
        done = true;
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return done;
    }

}