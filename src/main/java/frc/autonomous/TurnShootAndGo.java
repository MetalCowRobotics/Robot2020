/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

/**
 * Add your docs here.
 */
public class TurnShootAndGo {
    ShootBall shoot = new ShootBall();
    MCRCommand commandSet = new SequentialCommands(shoot, shoot, shoot);
    //  MCRCommand commandSet = new SequentialCommands(new ShootBall(), new ShootBall(), new ShootBall());
     mission = new SequentialCommands(new TimedCommandSet(shoot, 8), new DriveBackwardsStraight(36, 4));
}
