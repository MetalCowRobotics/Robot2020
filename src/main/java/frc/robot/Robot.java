/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.autonomous.NoAuto;
import frc.autonomous.ShootAndGather;
import frc.autonomous.ShootAndGo;
import frc.lib14.MCRCommand;
import frc.systems.Climber;
import frc.systems.DriveTrain;
import frc.systems.Hood;
import frc.systems.Intake;
import frc.systems.MasterControls;
import frc.systems.Shooter;
import frc.systems.Turret;
import frc.systems.Vision;

/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */

public class Robot extends TimedRobot {
  // systems
  DriveTrain driveTrain = DriveTrain.getInstance();
  Intake intake = Intake.getInstance();
  Shooter shooter = Shooter.getInstance();
  Climber climber = Climber.getInstance();
  Hood hood = Hood.getInstance();
  MasterControls controls = MasterControls.getInstance();
  RobotDashboard dashboard = RobotDashboard.getInstance();
  Vision vision = Vision.getInstance();

  // Turret turret;// = Turret.getInstance();

  // class variables
  MCRCommand mission;


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture(0);
    driveTrain.calibrateGyro();
    dashboard.pushAuto();
    dashboard.pushTurnPID();
  }

  @Override
  public void autonomousInit() {
    vision.visionInit();
    if (RobotDashboard.AutoMission.AUTOMODE_SHOOT_N_GO == dashboard.getAutoMission()) {
      mission = new ShootAndGo();
    } else if (RobotDashboard.AutoMission.AUTOMODE_SHOOT_N_GATHER == dashboard.getAutoMission()) {
      mission = new ShootAndGather(dashboard.getStartingPosition());
    } else {
      mission = new NoAuto();
    }
  }

  @Override
  public void autonomousPeriodic() {
    mission.run();
    runSystemsStateMachine();
  }

  @Override
  public void teleopInit() {
    vision.visionInit();
  }

  @Override
  public void teleopPeriodic() {
    controls.changeMode();
    applyOperatorInputs();
    runSystemsStateMachine();

    //testing
    SmartDashboard.putBoolean("limit deployed", intake.intakeDeployed());
    SmartDashboard.putBoolean("limit stowed", intake.intakeStowed());
    // if (controls.turretAdjustment() > .1) {
    //   turret.rotateTurret(10);
    // } else if (controls.turretAdjustment() < -.1) {
    //   turret.rotateTurret(-10);
    // }
  }

  private void applyOperatorInputs() {
    hood.calculateAdjustment(controls.hoodAdjustment());
    //check if operator wants to shoot
    if (controls.prepairToShoot()) {
      shooter.prepareToShoot();
    } else {
      shooter.stopShooter();
    }
    // shoot ball
    if (controls.shootNow()) {
      shooter.shootBall();
    } else if (controls.shootWhenReady()) {
      shooter.shootBallWhenReady();
    }
    // intake
    if (controls.lowerIntake()) {
      intake.lowerIntake();
    } else if (controls.raiseIntake()) {
      intake.retractIntake();
    }
    if (controls.intakeOnOff()) {
      intake.toggleIntakeState();
    }
    // climber
    climber.raiseClimber(controls.climbSpeed());
    //turret
    shooter.rotateTurret(controls.turretAdjustment());
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
