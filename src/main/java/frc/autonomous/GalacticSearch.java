package frc.autonomous;

import frc.lib14.MCRCommand;
import frc.commands.TurnDegrees;
import frc.commands.DriveInches;
import frc.lib14.TimedCommandSet;
import frc.commands.IntakeDeployRun;
import frc.lib14.SequentialCommands;

public class GalacticSearch implements MCRCommand {
    MCRCommand mission;
    MCRCommand mission1;

    public GalacticSearch() {
        // mission = new SequentialCommands(new IntakeDeployRun(), new DriveInches(1, 100), turn(-90, 1.5), new DriveInches(1, 82), turn(70, 1.25), new DriveInches(1, 175));//RED RUNS
        mission = new SequentialCommands(new IntakeDeployRun(), new DriveInches(1, 152), turn(-105, 2), new DriveInches(1, 63), turn((100), 2), new DriveInches(1, 103));//BLUE RUNS


        // MCRCommand firstTwoBalls = new SequentialCommands(new ParallelCommands(new DriveInches(1, 72), new IntakeDeployRun()), new ParallelCommands(new CurvatureDrive("RIGHT", 90, 60, .8)));
        // // MCRCommand firstTwoBalls = new SequentialCommands(turn(-20.79, 2), new DriveInches(1, 84.5), turn(110.79,1), new DriveInches(1, 30));
        // MCRCommand secondTwoBalls = new SequentialCommands(turn(-63.43, 2), new DriveInches(1, 67.08),  turn(22.08,1), new DriveInches(1, 42.43));
        // MCRCommand thirdTwoBalls = new SequentialCommands(turn(-135,1), new DriveInches(1, 120));
        // MCRCommand forthTwoBalls = new SequentialCommands(turn(135,1), new DriveInches(1, 42.43),  turn(-45,1 ), new DriveInches(1, 30));
        // MCRCommand fifthTwoBalls = new SequentialCommands(turn(45,1), new DriveInches(1, 84.85), turn(-45, 1), new DriveInches(1, 30));
        // mission = new SequentialCommands(firstTwoBalls, secondTwoBalls, thirdTwoBalls, forthTwoBalls, fifthTwoBalls);
    }
    public void run() {

        mission.run();
    }
    public boolean isFinished() {
        return false;
    }

    private MCRCommand turn(double degrees, double timeout) {
        return new TimedCommandSet(new TurnDegrees(degrees), timeout);
    }
}