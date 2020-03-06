/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import frc.commands.DriveBackwardsStraight;
import frc.commands.ShootBall;
import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;

public class ShootAndGo implements MCRCommand {
    MCRCommand mission;

    public ShootAndGo() {
        ShootBall shoot = new ShootBall();
        MCRCommand commandSet = new SequentialCommands(shoot, shoot, shoot);
        //  MCRCommand commandSet = new SequentialCommands(new ShootBall(), new ShootBall(), new ShootBall());
         mission = new SequentialCommands(new TimedCommandSet(commandSet, 11), new DriveBackwardsStraight(36, 4));
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
