package frc.autonomous;

import frc.commands.AutoTarget;
import frc.commands.DriveInches;
import frc.commands.IntakeDeployRun;
import frc.commands.ShootBall;
import frc.commands.SpinUpDrum;
import frc.commands.TurnTurret;
import frc.commands.MoveHood;
import frc.lib14.CommandPause;
import frc.lib14.MCRCommand;
import frc.lib14.ParallelCommands;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;
import frc.robot.RobotDashboard.AutoPosition;

public class ShootAndGather implements MCRCommand {
    MCRCommand mission;

    public ShootAndGather(AutoPosition position) {
            ShootBall shoot = new ShootBall(12);
            MCRCommand startUp = new ParallelCommands(new SpinUpDrum(), new TurnTurret(-226));
            MCRCommand secondShoot = new SequentialCommands(new AutoTarget(false), new MoveHood(800), new CommandPause(1.5), new TimedCommandSet(new ShootBall(24), 11));
            MCRCommand collect = new ParallelCommands(new IntakeDeployRun(), new AutoTarget(true), new DriveInches(1, 144), secondShoot);
            // MCRCommand collect = new ParallelCommands(new IntakeDeployRun(), new AutoTarget(true), new DriveInches( 1, 144));
            mission = new SequentialCommands(
               startUp,
               new TimedCommandSet(shoot, 3.5),
               collect
               //new TimedCommandSet(new ShootBall(288), 5)
            );
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