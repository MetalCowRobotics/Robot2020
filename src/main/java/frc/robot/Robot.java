/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.XboxControllerMetalCow;

/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */
public class Robot extends RobotBase {

  private CANSparkMax leftMotor;
  //private CANSparkMax rightMotor;
  // private DifferentialDrive m_myRobot;
  private static final int leftDeviceID = 1;
  //private static final int rightDeviceID = 2;
  private static final XboxControllerMetalCow driver = new XboxControllerMetalCow(0);

  public void robotInit() {
    leftMotor = new CANSparkMax(leftDeviceID, MotorType.kBrushless);
    //rightMotor = new CANSparkMax(rightDeviceID, MotorType.kBrushless);
   
  }

  public void disabled() {
  }

  public void autonomous() {
  }

  public void teleop() {
    SmartDashboard.putString("Here", "Here");
    double speed = driver.getLT();
      leftMotor.set(speed);
      //rightMotor.set(speed);
    if (driver.getAButton()){
      leftMotor.stopMotor();
      //rightMotor.stopMotor();
    }
  }

  public void test() {

  }

  private volatile boolean m_exit;

  @SuppressWarnings("PMD.CyclomaticComplexity")
  @Override
  public void startCompetition() {
    robotInit();

    // Tell the DS that the robot is ready to be enabled
    HAL.observeUserProgramStarting();

    while (!Thread.currentThread().isInterrupted() && !m_exit) {
      if (isDisabled()) {
        m_ds.InDisabled(true);
        disabled();
        m_ds.InDisabled(false);
        while (isDisabled()) {
          m_ds.waitForData();
        }
      } else if (isAutonomous()) {
        m_ds.InAutonomous(true);
        autonomous();
        m_ds.InAutonomous(false);
        while (isAutonomous() && !isDisabled()) {
          m_ds.waitForData();
        }
      } else if (isTest()) {
        LiveWindow.setEnabled(true);
        Shuffleboard.enableActuatorWidgets();
        m_ds.InTest(true);
        test();
        m_ds.InTest(false);
        while (isTest() && isEnabled()) {
          m_ds.waitForData();
        }
        LiveWindow.setEnabled(false);
        Shuffleboard.disableActuatorWidgets();
      } else {
        m_ds.InOperatorControl(true);
        teleop();
        m_ds.InOperatorControl(false);
        while (isOperatorControl() && !isDisabled()) {
          m_ds.waitForData();
        }
      }
    }
  }

  @Override
  public void endCompetition() {
    m_exit = true;
  }
}
