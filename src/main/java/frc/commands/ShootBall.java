/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCRCommand;
import frc.systems.Shooter;

public class ShootBall implements MCRCommand{ 
    Shooter shooter = Shooter.getInstance();
    boolean firstTime = true;
    boolean shotOne = false;
    boolean done = false;
    double targetDistance = 144; //inches

    public ShootBall() {

    }

    public ShootBall(double inchesFromTarget) {
        targetDistance = inchesFromTarget;
    }

    @Override
    public void run() {
        if(firstTime){
            firstTime = false;
            shooter.prepareToShoot(targetDistance);
        }
        if (shotOne) {
            shooter.shootBall();
        } else {
            if(shooter.atSpeed()){
                shooter.shootBall();
                shotOne = true;
            } 
        }
            //TODO how will we know when we are done
            // done = true;
    }

    private void end() {
        shooter.stopShooter();
    }

    @Override
    public boolean isFinished() {
        return done;
    }
}
