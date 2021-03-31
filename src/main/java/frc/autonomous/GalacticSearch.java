package frc.autonomous;

import frc.lib14.MCRCommand;
import frc.commands.CurvatureDrive;
import frc.commands.TurnDegrees;
import frc.commands.DriveInches;
import frc.lib14.SequentialCommands;

public class GalacticSearch implements MCRCommand {
    MCRCommand mission;

    public GalacticSearch() {
        MCRCommand firstTwoBalls = new MCRCommand(new TurnDegrees(-20.79), new DriveInches(84.5), new TurnDegrees(-69.21), new DriveBackwardsStraight(30));
        MCRCommand secondTwoBalls = new MCRCommand(new TurnDegrees(-20.79), new DriveInches(84.5), new TurnDegrees(-69.21), new DriveBackwardsStraight(30));
        MCRCommand thirdTwoBalls = new MCRCommand(new TurnDegrees(-20.79), new DriveInches(84.5), new TurnDegrees(-69.21), new DriveBackwardsStraight(30));
        MCRCommand forthTwoBalls = new MCRCommand(new TurnDegrees(-20.79), new DriveInches(84.5), new TurnDegrees(-69.21), new DriveBackwardsStraight(30));
        MCRCommand fifthTwoBalls = new MCRCommand(new TurnDegrees(-20.79), new DriveInches(84.5), new TurnDegrees(-69.21), new DriveBackwardsStraight(30));
    }
    public void run() {

        mission.run();
    }
    public boolean isFinished() {
        return false;
    }
}