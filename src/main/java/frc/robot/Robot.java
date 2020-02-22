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
import frc.lib14.XboxControllerMetalCow;
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
  Vision vision = Vision.getInstance();
  XboxControllerMetalCow controller = new XboxControllerMetalCow(0);

  // class variables
  MCRCommand mission;

  // testing only
  Magazine magazine;// = Magazine.getInstance();
  // Turret turret = Turret.getInstance();
  Hood hood = Hood.getInstance();
  boolean firstTime = true;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  @Override
  public void robotInit() {
    //driveTrain.calibrateGyro();
    dashboard.pushAuto();
    dashboard.pushTurnPID();
  }

  @Override
  public void autonomousInit() {
    vision.visionInit();
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
    vision.visionInit();
    hood.resetEncoder();
    //shooter.setTargetSpeed(SmartDashboard.getNumber("Set Velocity", 1500));// needs velocity
  }

  @Override
  public void teleopPeriodic() {
    //controls.changeMode();
    //applyOperatorInputs();
    // runSystemsStateMachine();

    //check if operator wants to shoot
    if (controls.prepairToShoot()) {
      shooter.prepairToShoot();
    } else {
      shooter.stopShooter();
    }

    //set shooting mode
    if (controller.getYButton()) {
      shooter.shootNow = true;
    } else if (controller.getAButton()) {
      shooter.shootNow = false;
    }

    //do on first time
    if (firstTime) {
      shooter.setupShooter();
      firstTime = false;
    }

    //shoot ball
    if (controls.shootNow()) {
      shooter.shootBall();
    } else if (controls.shootWhenReady()) {
      shooter.shootBallWhenReady();
    }

    //run systems
    shooter.run();
    intake.run();

    SmartDashboard.putNumber("distance", vision.getTargetDistance());
    SmartDashboard.putNumber("yaw", vision.getYawDegrees());
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
    if (controls.shootWhenReady()) {
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
