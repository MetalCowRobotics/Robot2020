package frc.autonomous;

import frc.commands.DriveBackwardsStraight;
import frc.commands.ShootBall;
import frc.commands.SpinUpDrum;
import frc.commands.TurnTurret;
import frc.lib14.MCRCommand;
import frc.lib14.ParallelCommands;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;

public class TurnShootAndGo implements MCRCommand {
    MCRCommand stepOne = new ParallelCommands(new SpinUpDrum(), new TurnTurret(-90));
    MCRCommand stepTwo = new ShootBall();
    MCRCommand mission = new SequentialCommands(stepOne, new TimedCommandSet(stepTwo, 8), new DriveBackwardsStraight(36, 4));

    @Override
    public void run() {
        mission.run();
    }

    @Override
    public boolean isFinished() {
        return mission.isFinished();
    }

}
