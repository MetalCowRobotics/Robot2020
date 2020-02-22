/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.autonomous.NoAuto;
import frc.autonomous.ShootAndGather;
import frc.autonomous.ShootAndGo;
import frc.commands.NewTurn;
import frc.lib14.MCRCommand;
import frc.robot.RobotMap.Magazine;
import frc.systems.Climber;
import frc.systems.DriveTrain;
import frc.systems.Hood;
import frc.systems.Intake;
import frc.systems.MasterControls;
import frc.systems.Shooter;
import frc.systems.Vision;

/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */

public class Robot extends TimedRobot {
  // systems
  DriveTrain driveTrain;// = DriveTrain.getInstance();
  Intake intake;// = Intake.getInstance();
  Shooter shooter;// = Shooter.getInstance();
  Climber climber;// = Climber.getInstance();
  MasterControls controls = MasterControls.getInstance();
  RobotDashboard dashboard = RobotDashboard.getInstance();

  // class variables
  MCRCommand mission;

  // testing only
  Magazine magazine;// = Magazine.getInstance();
  Vision vision = Vision.getInstance();
  // Turret turret = Turret.getInstance();
  Hood hood = Hood.getInstance();
  boolean firstTime = true;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  @Override
  public void robotInit() {
    final UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
    vision.visionInit();
    //driveTrain.calibrateGyro();
    dashboard.pushAuto();
    dashboard.pushTurnPID();
  }

  @Override
  public void autonomousInit() {
    if (RobotDashboard.AutoMission.AUTOMODE_SHOOT_N_GO == dashboard.getAutoMission()) {
      mission = new ShootAndGo();
    } else if (RobotDashboard.AutoMission.AUTOMODE_SHOOT_N_GATHER == dashboard.getAutoMission()) {
      mission = new ShootAndGather();
    } else {
      mission = new NoAuto();
    }
    // testing
    mission = new NewTurn(90);
  }

  @Override
  public void autonomousPeriodic() {
    mission.run();
    runSystemsStateMachine();
  }

  @Override
  public void teleopInit() {
    hood.resetEncoder();
    //shooter.setTargetSpeed(SmartDashboard.getNumber("Set Velocity", 1500));// needs velocity
  }

  @Override
  public void teleopPeriodic() {
    //controls.changeMode();
    //applyOperatorInputs();
    // runSystemsStateMachine();

    // testing
    // shooter.shooterTest();
    // shooter.runShooter();
    // shooter.run();

    if (vision.getTargetDistance() > 25) {
      hood.calculateTicks();
      hood.setFarShot();
    } else if (vision.getTargetDistance() > 5) {
      hood.calculateTicks();
      hood.setTenFoot();
    } else if (vision.getTargetDistance() > 1) {
      hood.calculateTicks();
      hood.setSafeZone();
    } else {
      Hood.hood.set(0);
    }

    SmartDashboard.putNumber("distance", vision.getTargetDistance());
    SmartDashboard.putNumber("yaw", vision.getYawDegrees());

    // if (firstTime) {
    // shooter.runShooter();
    // firstTime = false;
    // }
    // shooter.run();
  }

  private void applyOperatorInputs() {
    // intake
    if (controls.lowerIntake()) {
      intake.lowerIntake();
    } else if (controls.raiseIntake()) {
      intake.retractIntake();
    }
    // shooter
    if(controls.intakeOnOff()){
      intake.toggleIntakeState();
    }
    //shooter
    if (controls.spinUpAndShoot()) {
     // shooter.shootBallWhenReady();
    }
    //climber
    climber.raiseClimber(controls.climbSpeed());
  } 

  private void runSystemsStateMachine() {
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
