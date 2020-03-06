/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import frc.commands.DriveStraightInches;
import frc.commands.IntakeDeployRun;
import frc.commands.ShootBall;
import frc.commands.TurnDegrees;
import frc.lib14.CommandPause;
import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;
import frc.robot.RobotDashboard;
import frc.robot.RobotDashboard.AutoPosition;

public class ShootAndGather implements MCRCommand {
    MCRCommand mission;

    public ShootAndGather(AutoPosition position) {

        if(position.equals(RobotDashboard.AutoPosition.AUTOMODE_RIGHT_OF_TARGET)) {
            
             MCRCommand commandSet = new SequentialCommands(new ShootBall(), new ShootBall(), new ShootBall());
           MCRCommand driveSet = new SequentialCommands(new TimedCommandSet(new TurnDegrees(170), 5),
                    new CommandPause(.02), new DriveStraightInches(90, 4), new TimedCommandSet(new TurnDegrees(18), 4),
                    new CommandPause(.02), new IntakeDeployRun(), new DriveStraightInches(75, 4));
             mission = new SequentialCommands(new TimedCommandSet(commandSet, 5), new TimedCommandSet(driveSet, 30));
            System.out.println("auto mission shoot and gather right");
        }
        else {mission = new ShootAndGo(); System.out.println("auto mission defaulted to shoot and go");}
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