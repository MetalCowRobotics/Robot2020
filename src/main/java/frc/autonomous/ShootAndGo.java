/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.commands.DriveStraightInches;
import frc.commands.ShootBall;
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
        mission = new SequentialCommands(new TimedCommandSet(commandSet, 10), new DriveStraightInches(20));

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
