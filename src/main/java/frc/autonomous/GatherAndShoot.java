/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.commands.DriveInches;
import frc.commands.IntakeDeployRun;
import frc.commands.IntakeStow;
import frc.commands.ShootBall;
import frc.commands.SpinUpDrum;
import frc.commands.TurnDegrees;
import frc.lib14.MCRCommand;
import frc.lib14.ParallelCommands;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;

/**
 * Add your docs here.
 */
public class GatherAndShoot implements MCRCommand{
    MCRCommand mission;

        public GatherAndShoot(){// this mission will start with the intake facing the opponents color wheel headed for the two balls, then drive and tun then drive and shoot al 5(hopefully) balls
            MCRCommand stepOne = new ParallelCommands(new IntakeDeployRun(), new TimedCommandSet(new DriveInches(1, 145), 4));
            MCRCommand stepTwo = new ParallelCommands(new TimedCommandSet(new DriveInches(-1, 175), 4));
            MCRCommand stepThree = new ParallelCommands(new IntakeStow(), new TimedCommandSet (new TurnDegrees(90), 3), new SpinUpDrum());
            MCRCommand stepFour = new SequentialCommands(new TimedCommandSet(new DriveInches(-1, 270), 4));
            MCRCommand stepFive = new SequentialCommands(new ShootBall());
            mission = new SequentialCommands(stepOne, stepTwo, stepThree, stepFour, stepFive);
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
