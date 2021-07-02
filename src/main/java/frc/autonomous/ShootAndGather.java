package frc.autonomous;

import frc.commands.AutoTarget;
import frc.commands.DriveInches;
import frc.commands.IntakeDeployRun;
import frc.commands.ShootBall;
import frc.commands.SpinUpDrum;
import frc.commands.TurnTurret;
import frc.commands.CurvatureDrive;
import frc.commands.TurnDegrees;
import frc.commands.DriveStraightInches;
import frc.lib14.CommandPause;
import frc.lib14.MCRCommand;
import frc.lib14.ParallelCommands;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;

import frc.robot.RobotDashboard.AutoPosition;

public class ShootAndGather implements MCRCommand {
    MCRCommand mission;

    public ShootAndGather(AutoPosition position) {
        if (position == AutoPosition.AUTOMODE_RIGHT_OF_TARGET) {
            MCRCommand shoot = new ShootBall(10); //need to test and find best distance value
            MCRCommand driveToGatherPosition = new SequentialCommands(new TurnDegrees(13.438), new DriveStraightInches(141.658), new TurnDegrees(20.296));
            MCRCommand gather = new SequentialCommands(new CurvatureDrive("Right", 90.5, 67.6, 0.7), new TurnDegrees(11.74), new DriveStraightInches(40));
            mission = new SequentialCommands(shoot, driveToGatherPosition, gather);
        } else if (position == AutoPosition.AUTOMODE_CENTER) {
            MCRCommand shoot = new ShootBall(10); //need to test and find best distance value
            MCRCommand driveToGatherPosition = new SequentialCommands(new DriveStraightInches(131.91), new TurnDegrees(22.5));
            MCRCommand gather = new SequentialCommands(new DriveStraightInches(20), new TurnDegrees(-11.74), new CurvatureDrive("left", 90.5, 67.6, 0.8));
            mission = new SequentialCommands(shoot, driveToGatherPosition, gather);
        } else if (position == AutoPosition.AUTOMODE_LEFT_OF_TARGET) {
            MCRCommand shoot = new ShootBall(10); //need to test and find best distance value
            MCRCommand driveAndGather = new SequentialCommands(new DriveStraightInches(106.9), new TurnDegrees(20.556), new DriveStraightInches(85.24));
            // MCRCommand gather = new SequentialCommands(new CurvatureDrive("Right", 90.5, 67.6, 0.7), new TurnDegrees(11.74), new DriveStraightInches(40));
            mission = new SequentialCommands(shoot, driveAndGather);
        }
    }

    // public ShootAndGather(String position) {
    //     MCRCommand commandSet = new SequentialCommands(new ShootBall(), new ShootBall(), new ShootBall());
    //     MCRCommand driveset = new SequentialCommands( new TimedCommandSet(new TurnDegrees(170), 4),new CommandPause(.02), new DriveStraightInches(98, 3), new TimedCommandSet(new TurnDegrees(22), 3), new CommandPause(.02), new DriveStraightInches(36, 2),new CommandPause(.02), new DriveStraightInches(36, 2));
    //     //MCRCommand driveSet = new TurnDegrees(90);
    //     mission = new SequentialCommands(new TimedCommandSet(commandSet, 5), new TimedCommandSet(driveset, 30));
    //     //mission = new SequentialCommands(driveSet);
    // }

    @Override
    public void run() {
        mission.run();
    }

    @Override
    public boolean isFinished() {
        return mission.isFinished();
    }
}