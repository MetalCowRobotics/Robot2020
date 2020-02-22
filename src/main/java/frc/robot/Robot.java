/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.autonomous.NoAuto;
import frc.autonomous.ShootAndGather;
import frc.autonomous.ShootAndGo;
import frc.lib14.MCRCommand;
import frc.lib14.XboxControllerMetalCow;
import frc.systems.Climber;
import frc.systems.DriveTrain;
import frc.systems.Intake;
import frc.systems.Magazine;
import frc.systems.MasterControls;
import frc.systems.Shooter;

/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */

public class Robot extends TimedRobot {
  // systems
  DriveTrain driveTrain = DriveTrain.getInstance();
  Intake intake;// = Intake.getInstance();
  Shooter shooter = Shooter.getInstance();
  Climber climber;// = Climber.getInstance();
  MasterControls controls = MasterControls.getInstance();
  RobotDashboard dashboard = RobotDashboard.getInstance();

  // class variables
  MCRCommand mission;

  // testing only
  Magazine magazine = Magazine.getInstance();
  //  Turret turret = Turret.getInstance();
  // Hood hood = Hood.getInstance();
  boolean firstTime = true;
  XboxControllerMetalCow controller = new XboxControllerMetalCow(0);


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  @Override
  public void robotInit() {
    final UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
    driveTrain.calibrateGyro();
    dashboard.pushAuto();
    dashboard.pushTurnPID();
  }

  @Override
  public void autonomousInit() {
    /*if (RobotDashboard.AutoMission.AUTOMODE_SHOOT_N_GO == dashboard.getAutoMission()) {
      mission = new ShootAndGo();
    } else if (RobotDashboard.AutoMission.AUTOMODE_SHOOT_N_GATHER == dashboard.getAutoMission()) {
      mission = new ShootAndGather();
    } else {
      mission = new NoAuto();
    }*/
  }

  @Override
  public void autonomousPeriodic() {
    /*mission.run();
    runSystemsState();*/
    SmartDashboard.putNumber("LT", controls.climbSpeed());
    if (controls.climbSpeed() > 0) {
      climber.raiseClimber(controls.climbSpeed());
    }
  }

  @Override
  public void teleopInit() {
    //testing
    shooter.setTargetSpeed(.65*5874);
  }

  @Override
  public void teleopPeriodic() {
    controls.changeMode();
    applyInputs();
    runSystemsState();

    //testing
    if (firstTime) {
      shooter.runShooter();
      firstTime = false;
    }
    shooter.run();
  }

  private void applyInputs() {
    if (controls.lowerIntake()) {
      intake.lowerIntake();
    } else if (controls.raiseIntake()) {
      intake.retractIntake();
    }
    if (controls.spinUpAndShoot()) {
      shooter.shootBallWhenReady();
    }
  } 

  private void runSystemsState() {
    driveTrain.drive();
    intake.run();
    shooter.run();
    climber.run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}
