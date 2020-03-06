package frc.autonomous;

import frc.commands.DriveBackwardsStraight;
import frc.commands.ShootBall;
import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;

public class ShootAndGo implements MCRCommand {
    MCRCommand mission;

    public ShootAndGo() {
        MCRCommand shoot = new ShootBall();
        mission = new SequentialCommands(new TimedCommandSet(shoot, 8), new DriveBackwardsStraight(36, 4));
    }

    @Override
    public void run() {
        mission.run();
    }

    @Override
    public boolean isFinished() {
        return mission.isFinished();
    }
}
