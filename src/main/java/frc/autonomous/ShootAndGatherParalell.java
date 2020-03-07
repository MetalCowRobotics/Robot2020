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

public class ShootAndGatherParalell implements MCRCommand {
    MCRCommand mission;

    public ShootAndGatherParalell(AutoPosition position) {
            ShootBall shoot = new ShootBall();
            MCRCommand startUp = new SequentialCommands(new SpinUpDrum(), new TurnTurret(-98));
            MCRCommand driveAndShoot = new ParallelCommands(new IntakeDeployRun(), new DriveInches( 1, 144), new ShootBall());
            MCRCommand driveSet = new SequentialCommands(startUp, new TimedCommandSet(driveAndShoot, 12));
            mission = driveSet;
            System.out.println("auto mission shoot and gather paralell");
        // }
        // else {mission = new ShootAndGo(); System.out.println("auto mission defaulted to shoot and go");}
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