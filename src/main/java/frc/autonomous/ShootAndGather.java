package frc.autonomous;

import frc.commands.DriveInches;
import frc.commands.DriveStraightInches;
import frc.commands.IntakeDeployRun;
import frc.commands.ShootBall;
import frc.commands.SpinUpDrum;
import frc.commands.TurnDegrees;
import frc.commands.TurnTurret;
import frc.lib14.CommandPause;
import frc.lib14.MCRCommand;
import frc.lib14.ParallelCommands;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;
import frc.robot.RobotDashboard;
import frc.robot.RobotDashboard.AutoPosition;

public class ShootAndGather implements MCRCommand {
    MCRCommand mission;

    public ShootAndGather(AutoPosition position) {

        // if(position.equals(RobotDashboard.AutoPosition.AUTOMODE_RIGHT_OF_TARGET)) {
            ShootBall shoot = new ShootBall();
            //  MCRCommand commandSet = new SequentialCommands(shoot, shoot, shoot);
            MCRCommand startUp = new ParallelCommands(new SpinUpDrum(), new TurnTurret(-98));
            MCRCommand collect = new ParallelCommands(new IntakeDeployRun(), new DriveInches( 1, 144));
           MCRCommand driveSet = new SequentialCommands(
               startUp,
                new TimedCommandSet(shoot, 3.5),
               collect,
               //new TimedCommandSet(new TurnDegrees(170), 5),
                    //new CommandPause(.02), new DriveStraightInches(90, 4), 
                    //new TimedCommandSet(new TurnDegrees(18), 4),
                    // new CommandPause(.02), 
                    //new DriveInches( 1, 174)
                    new TimedCommandSet(new ShootBall(), 5));
             mission = driveSet;//new SequentialCommands(new TimedCommandSet(shoot, 5), new TimedCommandSet(driveSet, 30));
            System.out.println("auto mission shoot and gather right");
        // }
        // else {mission = new ShootAndGo(); System.out.println("auto mission defaulted to shoot and go");}
    }

    public ShootAndGather(String position) {
        MCRCommand commandSet = new SequentialCommands(new ShootBall(), new ShootBall(), new ShootBall());
        MCRCommand driveset = new SequentialCommands( new TimedCommandSet(new TurnDegrees(170), 4),new CommandPause(.02), new DriveStraightInches(98, 3), new TimedCommandSet(new TurnDegrees(22), 3), new CommandPause(.02), new DriveStraightInches(36, 2),new CommandPause(.02), new DriveStraightInches(36, 2));
        //MCRCommand driveSet = new TurnDegrees(90);
        mission = new SequentialCommands(new TimedCommandSet(commandSet, 5), new TimedCommandSet(driveset, 30));
        //mission = new SequentialCommands(driveSet);
    }



    @Override
    public void run() {
        mission.run();
    }

    @Override
    public boolean isFinished() {
        return mission.isFinished();
    }
}