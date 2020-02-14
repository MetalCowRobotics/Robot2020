/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.commands.DriveBackwardsStraight;
import frc.commands.TurnDegrees;

/**
 * Add your docs here.
 */
public class ShootBallVision implements MCRCommand{
    TurnDegrees turn;
    boolean tapeFound = true;
    boolean facingField = true;
    boolean FirstTime = true;
    MCRCommand mission;
    public ShootBallVision() {
        mission = new SequentialCommands(new DriveBackwardsStraight(12), new TurnDegrees(25));
    }
    //init gyro
    public void findTape() {
        while (tapeFound) {
            turn = new TurnDegrees(-1);
            if (true/* vision has found the tape*/) {
                tapeFound = false;
            }
        }
    }
    public void turnToField() {
        while (facingField) {
            turn = new TurnDegrees(1);
            if (true/* gyro.angle is within 5 of 0*/) {
                facingField = false;
            }
        }
    }
    public void moveForward() {

    }

    @Override
    public void run() {
        
    }
    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }
}
