/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.autonomous.NoAuto;
import frc.autonomous.ShootAndGather;
import frc.autonomous.ShootAndGo;
import frc.lib14.MCRCommand;
import frc.systems.Climber;
import frc.systems.ColorWheel;
import frc.systems.DriveTrain;
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
  DriveTrain driveTrain = DriveTrain.getInstance();
  Intake intake = Intake.getInstance();
  Shooter shooter = Shooter.getInstance();
  Climber climber = Climber.getInstance();
  ColorWheel colorWheel = ColorWheel.getInstance();
  MasterControls controls = MasterControls.getInstance();
  RobotDashboard dashboard = RobotDashboard.getInstance();
  Vision vision = Vision.getInstance();
  PowerDistributionPanel pdb = new PowerDistributionPanel(0);

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
    dashboard.initialPush();
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
    //mission = new NoAuto();
    // mission = new WhichPath();
  }

  @Override
  public void autonomousPeriodic() {
    mission.run();
    runSystemsStateMachine();
    SmartDashboard.putNumber("pdb", pdb.getCurrent(0));

  }

  @Override
  public void teleopInit() {
    vision.visionInit();
    driveTrain.initForTeleOp();
  }

  @Override
  public void teleopPeriodic() {
    // System.out.println("hood"+controls.hoodAdjustment());
    controls.changeMode();
    applyOperatorInputs();
    driveTrain.drive();
    runSystemsStateMachine();
    SmartDashboard.putNumber("current", intake.current());

    //testing
    SmartDashboard.putNumber("distance", vision.getTargetDistance());
    SmartDashboard.putNumber("yaw", vision.getYawDegrees());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
  }

  private void applyOperatorInputs() {
    if (controls.colorWheelForward()) {
      colorWheel.override(1);
    } else {
      colorWheel.stopColorWheel();
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
    //shooter
    shooter.adjustToRequestedDistance(controls.requestDistance());
    shooter.manualAdjustHood(controls.hoodAdjustment());
    shooter.rotateTurret(controls.turretAdjustment());
    //check if operator wants to shoot
    if (controls.prepairToShoot()) {
      shooter.prepareToShoot();
    } else if (controls.target()) {
        shooter.beginTargetting();
    } else {
      shooter.stopShooter();
    }
    // shoot ball
    if (controls.shootNow()) {
      shooter.shootBall();
    } else if (controls.shootWhenReady()) {
      shooter.shootBallWhenReady();
    }
    // climber
    climber.raiseClimber(controls.climbSpeed());
  }

  private void runSystemsStateMachine() {
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
