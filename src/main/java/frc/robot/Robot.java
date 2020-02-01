/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.autonomous.ShootAndGo;
import frc.lib14.MCRCommand;
import frc.lib14.XboxControllerMetalCow;
import frc.systems.Climber;
import frc.systems.DriveTrain;
import frc.systems.Intake;
import frc.systems.Shooter;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C.Port;
/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */

public class Robot extends TimedRobot {
  DriveTrain drive;// = DriveTrain.getInstance();
  Intake intake;// = Intake.getInstance();
  Shooter shooter;// = Shooter.getInstance();
  Climber climber;// = Climber.getInstance();
  XboxControllerMetalCow controller;// = new XboxControllerMetalCow(0);
  RobotDashboard dashboard;// = RobotDashboard.getInstance();
  MCRCommand mission;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
  }

  @Override
  public void autonomousInit() {
    mission = new ShootAndGo();

  }

  @Override
  public void autonomousPeriodic() {
    mission.run();
  }
  I2C.Port port = I2C.Port.kOnboard;
  ColorSensorV3 sensor = new ColorSensorV3(port);
  ColorMatch color = new ColorMatch();

  final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  @Override
  public void teleopInit() {

    color.addColorMatch(kBlueTarget);
    color.addColorMatch(kGreenTarget);
    color.addColorMatch(kRedTarget);
    color.addColorMatch(kYellowTarget);
  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("red", sensor.getRed());
    SmartDashboard.putNumber("green", sensor.getGreen());
    SmartDashboard.putNumber("blue", sensor.getBlue()); 
    SmartDashboard.putNumber("proximity", sensor.getProximity()); 
    ColorMatchResult result = color.matchClosestColor(sensor.getColor());
    SmartDashboard.putNumber("confidence", result.confidence); 
    
    if (result.color == kRedTarget) {
      SmartDashboard.putString("color", "red");
    }
    else if (result.color == kGreenTarget) {
      SmartDashboard.putString("color", "green");
    }
    else if (result.color == kBlueTarget) {
      SmartDashboard.putString("color", "blue");
    }
    else if (result.color == kYellowTarget) {
      SmartDashboard.putString("color", "yellow");
    }
    // intake.retractIntake();
  }
  
  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
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
