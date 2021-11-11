package frc.autonomous;

import frc.lib14.MCRCommand;
import frc.commands.CurvatureDrive;
import frc.commands.TurnDegrees;
import frc.commands.DriveInches;
import frc.lib14.SequentialCommands;

public class BarrelRacing implements MCRCommand {
    MCRCommand mission;

    public BarrelRacing() {
        // MCRCommand firstLoop = new SequentialCommands(new TurnDegrees(-14.9), new DriveInches(1, 108.1), new CurvatureDrive("right", 360, 60, 0.7));
        MCRCommand firstLoop = new SequentialCommands(new DriveInches(1, 81), new CurvatureDrive("RIGHT", 361, 42, 0.8));
        // MCRCommand secondLoop = new SequentialCommands(new TurnDegrees(40.2), new DriveInches(1, 91.5), new CurvatureDrive("left", 360, 60, 0.7));
        MCRCommand secondLoop = new SequentialCommands( new DriveInches(1, 62), new CurvatureDrive("LEFT", 305, 48, 0.8));
        //MCRCommand thirdLoop = new SequentialCommands(new TurnDegrees(21.8), new DriveInches(1, 68.38), new CurvatureDrive("left", 226.6, 60, 0.7));
        // MCRCommand collect = new ParallelCommands(new IntakeDeployRun(), new AutoTarget(true), new DriveInches( 1, 144));
       // mission = new SequentialCommands(
        //    firstLoop, secondLoop, thirdLoop
        //);
        // mission = new CurvatureDrive("right", 90, 50, 0.8);
        // mission = new SequentialCommands(new CurvatureDrive("right", 90, 50, 0.8), new DriveInches(1, 108.1));
        // mission = new DriveInches(1, 91.5);
        // mission = new TurnDegrees(90);
        mission = new SequentialCommands(firstLoop, secondLoop, new DriveInches(1, 70), new CurvatureDrive("LEFT", 225, 48, 0.7), new DriveInches(1, 275));
        // mission = new CurvatureDrive("LEFT", 360, 60, 0.7);
        // mission = secondLoop;

    }
    public void run() {

        mission.run();
    }
    public boolean isFinished() {
        return false;
    }
}