package frc.systems;

import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.lib14.XboxControllerMetalCow;
import frc.systems.Magazine;

public class Shooter {

    // private static MCR_SRX topShooter = new MCR_SRX(RobotMap.Shooter.TOP_MOTOR);
    // private static MCR_SRX bottomShooter = new
    // MCR_SRX(RobotMap.Shooter.BOTTOM_MOTOR);
    // private static final SpeedControllerGroup shooter = new
    // SpeedControllerGroup(topShooter, bottomShooter);
    private static final XboxControllerMetalCow operator = new XboxControllerMetalCow(1);
    private CANSparkMax neo1;
    private CANSparkMax neo2;
    private double targetSpeed;// RPM's
    Magazine magazine = Magazine.getInstance();
    boolean maintainSpeed = false;

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

    public void run() {
        magazine.run();
        if (maintainSpeed) {
            // speed PID loop
        }
    }

    public boolean atSpeed() {
        if (UtilityMethods.between(neo1.getEncoder().getVelocity(), targetSpeed - 50, targetSpeed + 50)) {
            return true;
        } else {
            return false;
        }
    }

    public void prepairToShoot() {
        //get target distance
        //set shooter speed
        runShooter();
        //set hood poistion
        magazine.loadBallInShootingPosition();
    }

    public int ballShots() {
        return magazine.getCounted();
    }

    public void runShooter() {
        double speed = .65;
        shooter.set(speed);
        maintainSpeed = true;

    }

    public void stopShooter() {
        shooter.stopMotor();
        magazine.stopLoadToTop();
        maintainSpeed = false;
    }

    public void checkSpeed() {
        SmartDashboard.putNumber("Shooter Speed", neo1.getEncoder().getVelocity());// RPM

    }

    public void unload() {
        // unload the magazine
    }

    public boolean isReady() {
        if (magazine.isThereABallTopForShooter() && atSpeed()) {
            return true;
        }
        return false;
    }

    public void shootBallWhenReady() {
        if (isReady()) {
            magazine.feedOneBall();
        }
    }

    public void shootBall() {
        magazine.feedOneBall();
    }

}