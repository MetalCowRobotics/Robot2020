/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import frc.lib14.MCRCommand;
import frc.systems.Shooter;

/**
 * Add your docs here.
 */
public class ShootBall implements MCRCommand{ 
    Shooter shooter = Shooter.getInstance();

    @Override
    public void run() {
        shooter.runShooter();
        
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }
}
