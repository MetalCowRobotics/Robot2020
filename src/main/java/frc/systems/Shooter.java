package frc.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.lib14.XboxControllerMetalCow;
import frc.robot.RobotMap;

public class Shooter {
    private static final XboxControllerMetalCow driver = new XboxControllerMetalCow(0);
    private CANSparkMax neo1;
    private CANSparkMax neo2;
    // private static MCR_SRX topShooter = new MCR_SRX(RobotMap.Shooter.TOP_MOTOR);
    // private static MCR_SRX bottomShooter = new
    // MCR_SRX(RobotMap.Shooter.BOTTOM_MOTOR);
    private static SpeedControllerGroup shooter;
    private static final Shooter instance = new Shooter(); // TODO: What does this do?

    private Shooter() {
        neo1 = new CANSparkMax(0, MotorType.kBrushless);
        neo2 = new CANSparkMax(1, MotorType.kBrushless);
        // neo3 = new CANSparkMax(2, MotorType.kBrushless);
        // neo4 = new CANSparkMax(3, MotorType.kBrushless);

        shooter = new SpeedControllerGroup(neo1, neo2);

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
        double speed = driver.getLT();
        shooter.set(speed);

    }

    public void stopShooter() {
        shooter.set(0);
    }

    public void checkSpeed() {
        SmartDashboard.putNumber("Shooter Speed", neo1.getEncoder().getVelocity());// RPM

    }
}