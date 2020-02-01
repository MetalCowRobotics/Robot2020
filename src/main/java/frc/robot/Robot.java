/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.autonomous.ShootAndGo;
import frc.lib14.MCRCommand;
import frc.lib14.MCR_SRX;
import frc.lib14.XboxControllerMetalCow;
import frc.systems.Climber;
import frc.systems.DriveTrain;
import frc.systems.FC_JE_0149Encoder;
import frc.systems.Intake;
import frc.systems.Shooter;

/**
 * The VM is configured to automatically run this class. If you change the name
 * of this class or the package after creating this project, you must also
 * update the build.gradle file in the project.
 */

public class Robot extends TimedRobot {
  DriveTrain driveTrain = DriveTrain.getInstance();
  // Intake intake;// = Intake.getInstance();
  // Shooter shooter = Shooter.getInstance();
  // Climber climber;// = Climber.getInstance();
  XboxControllerMetalCow controller = new XboxControllerMetalCow(0);
  // RobotDashboard dashboard = RobotDashboard.getInstance();
  // MCRCommand mission;
  // FC_JE_0149Encoder turretMotor = new FC_JE_0149Encoder();
  // private static MCR_SRX TurretMotor = new MCR_SRX(7);
  double speed = .6;
  int target = 88;
  double tSpeed = .1;
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
  //   SmartDashboard.putNumber("TargetTics", 88);
  //   SmartDashboard.putNumber("Speed", .1);
  // // drive.calibrateGyro();
  //   // dashboard.pushTurnPID();
  // driveTrain.calibrateGyro();
  // SmartDashboard.putNumber("Target Angle", 90);
  }

  @Override
  public void autonomousInit() {
    // mission = new ShootAndGo();

  }

  @Override
  public void autonomousPeriodic() {
    // mission.run();
    // SmartDashboard.putNumber("DriveEncoder", driveTrain.getEncoderTics());

  }

  @Override
  public void teleopInit() {
    // target = (int) SmartDashboard.getNumber("TargetTics", 88);
    // tSpeed = SmartDashboard.getNumber("Speed", .1);
    // turretMotor.reset();
  }

  @Override
  public void teleopPeriodic() {
    driveTrain.drive();
    // TurretMotor.set(controller.getRY());
    // SmartDashboard.putNumber("Encoder tics", turretMotor.getTics());
    // SmartDashboard.putNumber("Encoder rate", turretMotor.getRate());

    // if (turretMotor.getTics() < target){
    //     TurretMotor.set(tSpeed);
    // }
    // if (turretMotor.getTics() > target){
    //   TurretMotor.set(-tSpeed);
    // }
    // if (turretMotor.getTics() == target){
    //   TurretMotor.stopMotor();
    // }

    // SmartDashboard.putNumber("Gyro", drive.getAngle());
    // intake.lowerIntake();
  //  SmartDashboard.putNumber("DriveEncoder", drive.getEncoderTics());
    // intake.retractIntake();
    // drive.arcadeDrive(controller.getRY(), controller.getRX());
  //   SmartDashboard.putNumber("Gyro", driveTrain.getAngle());
  //   // intake.lowerIntake();
  //  SmartDashboard.putNumber("DriveEncoder", driveTrain.getEncoderTics());
  //   // intake.retractIntake();
  //   driveTrain.arcadeDrive(controller.getRY(), controller.getRX());
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    // if (controller.getAButton()) {
    //   climber.lowerClimber();
    // } else if (controller.getBButton()) {
    //   climber.raiseClimber();
    // } else {
    //   climber.stopClimber();
    // }
  }

  public void test() {
  }

}
