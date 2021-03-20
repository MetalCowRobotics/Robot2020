package frc.autonomous;

import frc.lib14.MCRCommand;
import frc.commands.CurvatureDrive;
import frc.commands.TurnDegrees;
import frc.commands.DriveInches;
import frc.lib14.SequentialCommands;

public class BarrelRacing implements MCRCommand {
    MCRCommand mission;

    public BarrelRacing() {
        //MCRCommand firstLoop = new SequentialCommands(new TurnDegrees(-14.9), new DriveInches(1, 108.1), new CurvatureDrive("right", 360, 60, 0.7));
        //MCRCommand secondLoop = new SequentialCommands(new TurnDegrees(40.2), new DriveInches(1, 91.5), new CurvatureDrive("left", 360, 60, 0.7));
        //MCRCommand thirdLoop = new SequentialCommands(new TurnDegrees(21.8), new DriveInches(1, 68.38), new CurvatureDrive("left", 226.6, 60, 0.7));
        // MCRCommand collect = new ParallelCommands(new IntakeDeployRun(), new AutoTarget(true), new DriveInches( 1, 144));
       // mission = new SequentialCommands(
        //    firstLoop, secondLoop, thirdLoop
        //);
        mission = new CurvatureDrive("right", 180, 50, 0.8);
        // mission = new DriveInches(1, 91.5);
        // mission = new TurnDegrees(90);

    }
    public void run() {

        mission.run();
    }
    public boolean isFinished() {
        return false;
    }
}