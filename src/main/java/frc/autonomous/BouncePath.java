package frc.autonomous;

import frc.lib14.MCRCommand;
import frc.commands.CurvatureDrive;
import frc.commands.DriveBackwardsStraight;
import frc.commands.TurnDegrees;
import frc.commands.DriveInches;
import frc.commands.DriveStraightInches;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;


public class BouncePath implements MCRCommand {
    MCRCommand mission;


    public BouncePath() {
        MCRCommand firstPeak = new SequentialCommands ( new CurvatureDrive("LEFT", 90, 75, 0.8));//66
        MCRCommand smallTurn = new TimedCommandSet(new TurnDegrees(-20), 1);
        MCRCommand firstValley = new SequentialCommands( new DriveBackwardsStraight(128), new TimedCommandSet(new TurnDegrees(90), 1.3) );
        MCRCommand secondPeak = new SequentialCommands( new CurvatureDrive("LEFT", 70, 92, 0.8), new DriveStraightInches(64));
        MCRCommand thirdPeak = new SequentialCommands(new TimedCommandSet(new TurnDegrees(90), 1.5), new DriveInches(1, 30), new CurvatureDrive("LEFT", 90, 80, 0.8), new DriveInches(1, 40) );
        MCRCommand finalTurns = new SequentialCommands(new DriveBackwardsStraight(20), new TimedCommandSet(new TurnDegrees(-45), 1), new DriveBackwardsStraight(10));
        MCRCommand finalTurns2 = new SequentialCommands(new TimedCommandSet(new TurnDegrees(-10), 1), new DriveBackwardsStraight(20));
        mission = new SequentialCommands(firstPeak, smallTurn, firstValley, secondPeak, new DriveBackwardsStraight(95), thirdPeak, finalTurns2);
       
    }
    public void run() {

        mission.run();
    }
    public boolean isFinished() {
        return false;
    }
}