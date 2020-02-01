/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.DriveBackwardsStraight;
import frc.commands.DriveStraightInches;
import frc.commands.ShootBall;
import frc.commands.TurnDegrees;
<<<<<<< Updated upstream
=======
import frc.commands.DriveStraightInches.DRIVE_DIRECTION;
import frc.lib14.CommandPause;
>>>>>>> Stashed changes
import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;

/**
 * Add your docs here.
 */
public class ShootAndGo implements MCRCommand {
    MCRCommand mission;

    public ShootAndGo() {
        MCRCommand commandSet = new SequentialCommands(new ShootBall(), new ShootBall(), new ShootBall());
<<<<<<< Updated upstream
        // mission = new SequentialCommands(new TimedCommandSet(commandSet, 9), new DriveStraightInches(48, 6), new TurnDegrees(45));
        mission = new SequentialCommands(new TimedCommandSet(commandSet, 9), new TurnDegrees(SmartDashboard.getNumber("Target Angle", 90)));
        // mission = new SequentialCommands(new TimedCommandSet(commandSet, 9), new DriveBackwardsStraight(48, 10));

=======
        // mission = new SequentialCommands(new TimedCommandSet(commandSet, 9), new DriveStraightInches(DRIVE_DIRECTION.backward, 90));
        // mission = new SequentialCommands(new TimedCommandSet(commandSet, 5), new TurnDegrees(SmartDashboard.getNumber("Target Angle", 90)));
        //MCRCommand driveset = new SequentialCommands( new TimedCommandSet(new TurnDegrees(183), 4), new DriveStraightInches(100, 4), new TimedCommandSet(new TurnDegrees(16), 4), new DriveStraightInches(100, 4));
        //mission = new SequentialCommands(new TimedCommandSet(commandSet, 5), new TimedCommandSet(driveset, 10));
        MCRCommand driveset = new SequentialCommands( new TimedCommandSet(new TurnDegrees(180), 4),new CommandPause(.2), new DriveStraightInches(86, 4), new TimedCommandSet(new TurnDegrees(13), 4), new CommandPause(.2), new DriveStraightInches(34, 4),new CommandPause(0.01), new DriveStraightInches(34, 4));
        mission = new SequentialCommands(new TimedCommandSet(commandSet, 5), new TimedCommandSet(driveset, 10));
>>>>>>> Stashed changes
    }

    public ShootAndGo(String position) {
        MCRCommand commandSet = new SequentialCommands(new ShootBall(), new ShootBall(), new ShootBall());
        MCRCommand driveset = new SequentialCommands( new TimedCommandSet(new TurnDegrees(180), 4),new CommandPause(.2), new DriveStraightInches(70, 4));
        mission = new SequentialCommands(new TimedCommandSet(commandSet, 5), new TimedCommandSet(driveset, 10));
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
