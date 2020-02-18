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
import frc.commands.DriveStraightInches.DRIVE_DIRECTION;
import frc.lib14.CommandPause;
import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;

public class ShootAndGather implements MCRCommand {
    MCRCommand mission;

    public ShootAndGather() {
        // assumes starting from right position
        // need to shorten distance again from 90 to maybe 86 and increase degrees on
        // 2nd
        MCRCommand commandSet = new SequentialCommands(new ShootBall(), new ShootBall(), new ShootBall());
        MCRCommand driveSet = new SequentialCommands(new TimedCommandSet(new TurnDegrees(170), 5),
                new CommandPause(.02), new DriveStraightInches(90, 4), new TimedCommandSet(new TurnDegrees(18), 4),
                new CommandPause(.02), new DriveStraightInches(75, 4));
        mission = new SequentialCommands(new TimedCommandSet(commandSet, 5), new TimedCommandSet(driveSet, 30));
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