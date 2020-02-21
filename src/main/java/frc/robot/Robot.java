/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.XboxControllerMetalCow;
import frc.systems.ColorWheel;


/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */

public class Robot extends TimedRobot {
  // systems
  
  XboxControllerMetalCow controller = new XboxControllerMetalCow(0);
  Relay lightring = new Relay(0);;


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  @Override
  public void robotInit() {
    
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }
  ColorWheel c = ColorWheel.getInstance();
  @Override
  public void teleopInit() {

 }

  @Override
  public void teleopPeriodic() {
    // c.run(false);
    c.override(controller.getRT());
    // SmartDashboard.putNumber("rt", controller.);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
      if(controller.getRB()){
          lightring.set(Relay.Value.kForward);
          }else{
            lightring.set(Relay.Value.kOff);
          }
        }

        
}