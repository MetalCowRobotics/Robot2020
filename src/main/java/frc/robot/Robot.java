/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.lib14.XboxControllerMetalCow;
import frc.systems.Climber;

/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */

 
public class Robot extends TimedRobot {
  Climber climber = Climber.getInstance();
  XboxControllerMetalCow controller = new XboxControllerMetalCow(0);
  RobotDashboard dashboard = RobotDashboard.getInstance();
  
  public void robotInit() {

  }
  @Override
  public void teleopInit() {
    System.out.println("Default teleopInit() method... Override me!");
  }
  public void disabled() {
  }

  // public void autonomous() {/
    
  

  @Override
  public void teleopPeriodic() {
     if (controller.getAButton()) {
       climber.lowerClimber();
     } else if (controller.getBButton()) {
       climber.raiseClimber();
     } else {
       climber.stopClimber();
     }
  }

  public void test() {
  }

  
}

