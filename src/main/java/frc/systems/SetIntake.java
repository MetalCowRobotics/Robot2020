package frc.systems;

import frc.lib14.MCRCommand;

public class SetIntake implements MCRCommand {
    Intake intake = Intake.getInstance();
    boolean done = false;
    String command;
    public SetIntake(String command) {
        this.command = command;
    }
    @Override
    public void run() {
        if (command.equalsIgnoreCase("start")) {
            intake.startIntake();
            done = true;
        } else if (command.equalsIgnoreCase("stop")) {
            intake.stopIntake();
            done = true;
        }
   
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return done;
    }
    
}