package frc.autonomous;

import frc.lib14.MCRCommand;
import frc.commands.CurvatureDrive;
import frc.commands.DriveBackwardsStraight;
import frc.commands.TurnDegrees;
import frc.commands.DriveInches;
import frc.commands.DriveStraightInches;
import frc.lib14.SequentialCommands;


public class BouncePath implements MCRCommand {
    MCRCommand mission;


    public BouncePath() {
        MCRCommand firstPeak = new CurvatureDrive("LEFT", 109, 66, 0.8);
        MCRCommand firstValley = new SequentialCommands(new TurnDegrees(153.435), new DriveInches(1, 22.361), new TurnDegrees(26.565), new DriveInches(1, 50), new TurnDegrees(-45), new DriveInches(1, 70.711));
        MCRCommand secondPeak = new SequentialCommands(new TurnDegrees(-45), new CurvatureDrive("left", 90, 30, 0.8), new DriveInches(1, 90), new TurnDegrees(180), new DriveInches(1, 90));
        MCRCommand secondValley = new SequentialCommands(new CurvatureDrive("left", 90, 30, 0.8), new DriveInches(1, 30), new CurvatureDrive("left", 90, 30, 0.8));
        MCRCommand thirdPeak = new SequentialCommands(new DriveInches(1, 90), new TurnDegrees(180), new CurvatureDrive("left", 90, 60, 0.8));
        mission = new SequentialCommands(firstPeak, firstValley, secondPeak, secondValley, thirdPeak);
        mission = new SequentialCommands(firstPeak, new DriveBackwardsStraight(156), new TurnDegrees(80), new CurvatureDrive("LEFT", 42, 108, 0.8), new DriveStraightInches(54));
       // mission = new CurvatureDrive("LEFT", 42, 108, 0.8);
        //new TurnDegrees(150), new CurvatureDrive("LEFT", 5, 144, 0.8)
    }
    public void run() {

        mission.run();
    }
    public boolean isFinished() {
        return false;
    }
}