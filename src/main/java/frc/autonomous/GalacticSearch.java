package frc.autonomous;

import frc.lib14.MCRCommand;
import frc.commands.CurvatureDrive;
import frc.commands.TurnDegrees;
import frc.commands.DriveInches;
import frc.lib14.SequentialCommands;

public class GalacticSearch implements MCRCommand {
    MCRCommand mission;

    public GalacticSearch() {
        MCRCommand firstTwoBalls = new MCRCommand(new TurnDegrees(-20.79), new DriveInches(84.5), new TurnDegrees(110.79), new DriveInches(30));
        MCRCommand secondTwoBalls = new MCRCommand(new TurnDegrees(-63.43), new DriveInches(67.08), new TurnDegrees(22.08), new DriveInches(42.43));
        MCRCommand thirdTwoBalls = new MCRCommand(new TurnDegrees(-135), new DriveInches(120));
        MCRCommand forthTwoBalls = new MCRCommand(new TurnDegrees(135), new DriveInches(42.43), new TurnDegrees(-45), new DriveInches(30));
        MCRCommand fifthTwoBalls = new MCRCommand(new TurnDegrees(45), new DriveInches(84.85), new TurnDegrees(-45), new DriveInches(30));
    }
    public void run() {

        mission.run();
    }
    public boolean isFinished() {
        return false;
    }
}