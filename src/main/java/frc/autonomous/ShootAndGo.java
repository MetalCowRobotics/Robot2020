package frc.autonomous;

import frc.commands.AutoTarget;
import frc.commands.DriveBackwardsStraight;
import frc.commands.ShootBall;
import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;

public class ShootAndGo implements MCRCommand {
    MCRCommand mission;

    public ShootAndGo() {
        MCRCommand shoot = new SequentialCommands(new ShootBall(12));
        mission = new SequentialCommands(new AutoTarget(false), new DriveBackwardsStraight(36, 4), new TimedCommandSet(shoot, 6));
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
