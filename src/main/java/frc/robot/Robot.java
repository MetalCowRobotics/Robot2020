/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.XboxControllerMetalCow;
// import frc.robot.RobotMap.Magazine;
import frc.systems.Climber;
import frc.systems.Magazine;
import frc.systems.Turret;
import frc.lib14.MCR_SRX;


/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */

 
public class Robot extends TimedRobot {
  private static MCR_SRX testMotor = new MCR_SRX(RobotMap.Test.BAG_MOTOR);
  Climber climber = Climber.getInstance();
  Magazine magazine = Magazine.getInstance();
  Turret turret = Turret.getInstance();
  XboxControllerMetalCow controller = new XboxControllerMetalCow(0);
  RobotDashboard dashboard = RobotDashboard.getInstance();

  
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

  @Override
  public void teleopInit() {
    testMotor.setSelectedSensorPosition(0);
    // Magazine.getInstance();
  }

  @Override
  public void teleopPeriodic() {
    turret.rotateTurret(360);
    SmartDashboard.putNumber("Encoder Tics", testMotor.getSelectedSensorPosition());
    
    // if (testMotor.getSelectedSensorPosition() < 3600){
    //   testMotor.set(.1);
    // }
    // if(testMotor.getSelectedSensorPosition() > 3600){
    //   testMotor.set(-.1);
    // }
    // if (testMotor.getSelectedSensorPosition() == 3600){
    //   testMotor.stopMotor();
    // }

    //4050tics = 360 degrees      11.25tics = 1 degree

  //   magazine.checkIfLoaded();
  //   if (controller.getBButton()){
  //     magazine.feedOneBall();
  //   }else if (controller.getBButtonReleased()){
  //     magazine.stopMagazine();
  //   }
  //     magazine.runMagazine();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    // testMotor.set(.3);
    
    //  if (controller.getAButton()) {
    //    climber.lowerClimber();
    //  } else if (controller.getBButton()) {
    //    climber.raiseClimber();
    //  } else {
    //    climber.stopClimber();
    //  }
  }

  public void test() {
  }

  
}

