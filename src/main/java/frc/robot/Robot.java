/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.autonomous.ShootAndGo;
import frc.lib14.MCRCommand;
import frc.lib14.XboxControllerMetalCow;
// import frc.robot.RobotMap.Magazine;
import frc.systems.Climber;
import frc.systems.Magazine;
import frc.systems.Turret;
import frc.lib14.MCR_SRX;

import frc.systems.DriveTrain;
import frc.systems.Hood;
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
  // private static MCR_SRX testMotor = new MCR_SRX(RobotMap.Test.BAG_MOTOR);
  DriveTrain drive = DriveTrain.getInstance();
  Hood hood = Hood.getInstance();
  XboxControllerMetalCow controller = new XboxControllerMetalCow(0);
  RobotDashboard dashboard = RobotDashboard.getInstance();
  Intake intake = Intake.getInstance();
  Shooter shooter = Shooter.getInstance();
  Climber climber = Climber.getInstance();
  Magazine magazine = Magazine.getInstance();
  Turret turret = Turret.getInstance();
  MCRCommand mission;

  String shootAndGo = "shoot and go";
  String shootAndGather = "shoot and gather";
  String centerPosition = "center position";
  String leftPosition = "left position";
  String rightPosition = "right position";
  
  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  

  @Override
  public void robotInit() {
   SendableChooser<String> autonomousAction = new SendableChooser<>();
   SendableChooser<String> startingPosition = new SendableChooser<>();
   autonomousAction.setDefaultOption("shoot and go", shootAndGo);
   autonomousAction.addOption("shoot and gather", shootAndGather);
   startingPosition.setDefaultOption("center position", centerPosition);
   startingPosition.addOption("left position", leftPosition);
   startingPosition.addOption("right position", rightPosition);
   dashboard.pushStartingPosition(startingPosition);
   dashboard.pushAutonomousAction(autonomousAction);
   UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
   dashboard.pushAuto();
   
  
  }

  @Override
  public void autonomousInit() {
    mission = new ShootAndGo();

  }

  @Override
  public void autonomousPeriodic() {
    mission.run();
    SmartDashboard.putNumber("DriveEncoder", drive.getEncoderTics());

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
    turret.resetTurretEncoder();
    // Magazine.getInstance();
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
    SmartDashboard.putNumber("Gyro", drive.getAngle());
    drive.arcadeDrive(-controller.getRY(), -controller.getX());

    if (controller.getAButton()) {
      hood.lowerHood();
    }
    if (controller.getBButton()) {
      hood.raiseHood();
    }
    hood.run();
    turret.rotateTurret(30);
    // SmartDashboard.putNumber("Encoder Tics", testMotor.getSelectedSensorPosition());

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
    if (controller.getRB() == true){
        magazine.runMagazine();
        magazine.checkIfLoaded();
    }else{
      magazine.stopMagazine();
      magazine.checkIfLoaded();
    }
    SmartDashboard.putNumber("Gyro", drive.getAngle());
    // intake.lowerIntake();
   SmartDashboard.putNumber("DriveEncoder", drive.getEncoderTics());
    // intake.retractIntake();
    drive.arcadeDrive(controller.getRY(), controller.getRX());
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
