package frc.systems;

import frc.robot.RobotMap;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.XboxControllerMetalCow;

public class Shooter {
    private static final XboxControllerMetalCow operator = new XboxControllerMetalCow(1);
    private CANSparkMax neo1;
    private CANSparkMax neo2;
    // private CANSparkMax neo3;
    // private CANSparkMax neo4;
    private static SpeedControllerGroup shooter;
    // private static SpeedControllerGroup leftShooter;
    private static final Shooter instance = new Shooter();

    private Shooter() {
        neo1 = new CANSparkMax(RobotMap.Shooter.TOP_MOTOR_ID, MotorType.kBrushless);
        neo2 = new CANSparkMax(RobotMap.Shooter.BOTTOM_MOTOR_ID, MotorType.kBrushless);

        // neo3 = new CANSparkMax(2, MotorType.kBrushless);
        // neo4 = new CANSparkMax(3, MotorType.kBrushless);

        shooter = new SpeedControllerGroup(neo1, neo2);
        // leftShooter = new SpeedControllerGroup(neo3, neo4);
        // neoTwo.follow(neoOne);
        // newOne.getEncoder
        // newTwo.clone()
        // neoOne.set(speed);
        // SpeedControllerGroup shooterR = new SpeedControllerGroup(neoOne, neoTwo);
        // shooterL.follow(shooterR);

    }

    public static Shooter getInstance() {
        return instance;
    }

    public void runShooter() {
        double speed = operator.getLT();
        shooter.set(speed);

    }

    public void stopShooter() {
        shooter.stopMotor();
    }

    public void checkSpeed() {
        SmartDashboard.putNumber("Shooter Speed", neo1.getEncoder().getVelocity());// RPM

    }
    public void unload(){
        // unload the magazine
    }
}