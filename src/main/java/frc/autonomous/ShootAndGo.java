/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.DriveBackwardsStraight;
import frc.commands.DriveStraightInches;
import frc.commands.ShootBall;
import frc.commands.TurnDegrees;
import frc.lib14.CommandPause;
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
        mission = new SequentialCommands(new TimedCommandSet(commandSet, 9), new DriveBackwardsStraight(36, 6));

    }

    // public ShootAndGo(String position) {
    // MCRCommand commandSet = new SequentialCommands(new ShootBall(), new
    // ShootBall(), new ShootBall());
    // MCRCommand driveset = new SequentialCommands( new TimedCommandSet(new
    // TurnDegrees(167), 3),new CommandPause(.05), new DriveStraightInches(96, 4),
    // new TimedCommandSet( new TurnDegrees(21.5), 2) , new CommandPause(.05), new
    // DriveStraightInches(98, 8));
    // mission = new SequentialCommands(new TimedCommandSet(commandSet, 5), new
    // TimedCommandSet(driveset, 15));
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
