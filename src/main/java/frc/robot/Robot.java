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
import frc.autonomous.ShootAndGather;
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
  XboxControllerMetalCow controller = new XboxControllerMetalCow(0);


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  @Override
  public void robotInit() {
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
    dashboard.pushAuto();
    dashboard.pushTurnPID();
    driveTrain.calibrateGyro();
    SmartDashboard.putNumber("ShooterPercent", .35);
    // autonomous setup
    SendableChooser<String> autonomousAction = new SendableChooser<>();
    SendableChooser<String> startingPosition = new SendableChooser<>();
    // autonomousAction.setDefaultOption("shoot and go", shootAndGo);
    // autonomousAction.addOption("shoot and gather", shootAndGather);
    // startingPosition.setDefaultOption("center position", centerPosition);
    // startingPosition.addOption("left position", leftPosition);
    // startingPosition.addOption("right position", rightPosition);
    dashboard.pushStartingPosition(startingPosition);
    dashboard.pushAutonomousAction(autonomousAction);
    dashboard.pushAuto();
    dashboard.pushShooterSpeed();
  }

  @Override
  public void autonomousInit() {
    mission = new ShootAndGather("test");

  }

  @Override
  public void autonomousPeriodic() {
    mission.run();
    //runSystemsState();
  }

  I2C.Port port = I2C.Port.kOnboard; /*
  ColorSensorV3 sensor = new ColorSensorV3(port);
  ColorMatch color = new ColorMatch();

  final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
*/
  private boolean firstTime = true;
  @Override
  public void teleopInit() {
    //shooter.setTargetSpeed(SmartDashboard.getNumber("Shooter_Speed", .65*5874));
  }
/*
  private void applyInputs() {
    if (controls.lowerIntake()) {
      intake.lowerIntake();
    } else if (controls.raiseIntake()) {
      intake.retractIntake();
    }
    if (controls.spinUpAndShoot()) {
      shooter.shootBallWhenReady();
    }

  } */

  @Override
  public void teleopPeriodic() {
    // controls.changeMode();
    if (firstTime) {
    shooter.runShooter();
    firstTime= false;
    }
    shooter.run();
    /*
    applyInputs();
    driveTrain.drive();
    intake.run();
    shooter.run();
    climber.run();
    //colorwheel.run();
    // drive train testing
    // driveTrain.arcadeDrive(-controller.getRY(), -controller.getX());
    //
    // hood testing
    //
    // if (controller.getAButton()) {
    // hood.lowerHood();
    // }
    // if (testMotor.getSelectedSensorPosition() == 3600){
    // testMotor.stopMotor();
    // }

    // 4050tics = 360 degrees 11.25tics = 1 degree

    // magazine.checkIfLoaded();
    // if (controller.getBButton()){
    // magazine.feedOneBall();
    // }else if (controller.getBButtonReleased()){
    // magazine.stopMagazine();
    // }
    // magazine.runMagazine();
    if (controller.getRB() == true) {
      magazine.runMagazine();
      magazine.checkIfLoaded();
    } else {
      magazine.stopMagazine();
      magazine.checkIfLoaded();
    }
    // SmartDashboard.putNumber("Gyro", drive.getAngle());
    intake.lowerIntake();
    // intake.retractIntake();



    // magazine.checkIfLoaded();
    // } else {
    // magazine.stopMagazine();
    // magazine.checkIfLoaded();
    // }
    // feedback
    SmartDashboard.putNumber("Gyro", driveTrain.getAngle());
    SmartDashboard.putNumber("Drive Encoder", driveTrain.getEncoderTics()); */
  //   if (firstTime) {
  //     shooter.runShooter();
  //     firstTime = false;
  //   }
  //   shooter.run();
  // }

  // private void runSystemsState() {
  //   intake.run();
  //   shooter.run();
  //   climber.run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}
