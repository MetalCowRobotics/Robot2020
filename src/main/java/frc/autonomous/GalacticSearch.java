package frc.autonomous;

import frc.lib14.MCRCommand;
import frc.commands.CurvatureDrive;
import frc.commands.TurnDegrees;
import frc.commands.DriveInches;
import frc.lib14.SequentialCommands;

public class GalacticSearch implements MCRCommand {
    MCRCommand mission;

    public GalacticSearch() {
        MCRCommand firstTwoBalls = new SequentialCommands(new TurnDegrees(-20.79), new DriveInches(1, 84.5), new TurnDegrees(110.79), new DriveInches(1, 30));
        MCRCommand secondTwoBalls = new SequentialCommands(new TurnDegrees(-63.43), new DriveInches(1, 67.08), new TurnDegrees(22.08), new DriveInches(1, 42.43));
        MCRCommand thirdTwoBalls = new SequentialCommands(new TurnDegrees(-135), new DriveInches(1, 120));
        MCRCommand forthTwoBalls = new SequentialCommands(new TurnDegrees(135), new DriveInches(1, 42.43), new TurnDegrees(-45), new DriveInches(1, 30));
        MCRCommand fifthTwoBalls = new SequentialCommands(new TurnDegrees(45), new DriveInches(1, 84.85), new TurnDegrees(-45), new DriveInches(1, 30));
        mission = new SequentialCommands(firstTwoBalls, secondTwoBalls, thirdTwoBalls, forthTwoBalls, fifthTwoBalls);
    }
    public void run() {

        mission.run();
    }
    public boolean isFinished() {
        return false;
    }
}