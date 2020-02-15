package frc.systems;

import frc.robot.RobotDashboard;
import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.PIDController;
import frc.lib14.UtilityMethods;
import frc.lib14.XboxControllerMetalCow;
import frc.systems.Magazine;

public class Shooter {
    private static final double SHOOTER_SPEED = .65;
    private static final XboxControllerMetalCow operator = new XboxControllerMetalCow(1);
    private static CANSparkMax neo1 = new CANSparkMax(RobotMap.Shooter.TOP_MOTOR_ID, MotorType.kBrushless);
    private static CANSparkMax neo2 = new CANSparkMax(RobotMap.Shooter.BOTTOM_MOTOR_ID, MotorType.kBrushless);
    private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1, neo2);
    // private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1);
    private Magazine magazine;// = Magazine.getInstance();
    private Turret turret;// = Turret.getInstance();
    private double targetSpeed;// RPM's
    private boolean maintainSpeed = false;
    private static PIDController pidController;
    private static double P = .00003;
    private static double I = .000025;
    private static double D = 0;
    private static double Iz = 400;
    private boolean firstTime = true;


    // singleton instance
    private static final Shooter instance = new Shooter();

    private Shooter() {

        // leftShooter = new SpeedControllerGroup(neo3, neo4);
        // neoTwo.follow(neoOne);
        // newOne.getEncoder
        // newTwo.clone()
        // neoOne.set(speed);
        // SpeedControllerGroup shooterR = new SpeedControllerGroup(neoOne, neoTwo);
        // shooterL.follow(shooterR);
        pidController = new PIDController(0, P, I, D, Iz);
        RobotDashboard.getInstance().pushShooterPIDValues(P, I, D, Iz);
    }

    public static Shooter getInstance() {
        return instance;
    }

    public void run() {
        //magazine.run();
        //turret.run();
        if (maintainSpeed) {
            shooter.set(SHOOTER_SPEED + getCorrection());
            SmartDashboard.putNumber("Correction", getCorrection());
            SmartDashboard.putNumber("Actual Velocity", neo1.getEncoder().getVelocity());            // speed PID loop
        }
    }

    public boolean atSpeed() {
        return UtilityMethods.between(Math.abs(neo1.getEncoder().getVelocity()), targetSpeed - 50, targetSpeed + 50);
    }

    public void prepairToShoot() {
        runShooter();
        magazine.loadBallInShootingPosition();
    }

    public int ballShots() {
        return magazine.getCounted();
    }

    public void runShooter() {
        if (firstTime) {
            setTargetSpeed(SmartDashboard.getNumber("Set Velocity", 1500));//needs velocity
            firstTime = false;
        }
        //shooter.set(SmartDashboard.getNumber("Set Velocity", 1500));
        maintainSpeed = true;
    }

    public void stopShooter() {
        shooter.stopMotor();
        maintainSpeed = false;
        magazine.stopLoadToTop();
    }

    public double getCorrection() {
        pidController.set_kP(getP());
        pidController.set_kI(getI());
        pidController.set_kD(getD());
        pidController.set_Iz(getIz());
        double correction = pidController.calculateAdjustment(neo1.getEncoder().getVelocity());
        System.out.println(targetSpeed+" vel:"+neo1.getEncoder().getVelocity()+" correction:"+correction);
        return correction;
    }

    private double getP() {
        return SmartDashboard.getNumber("SkP", P);
    }

    private double getI() {
        return SmartDashboard.getNumber("SkI", I);
    }

    private double getD() {
        return SmartDashboard.getNumber("SkD", D);
    }

    private double getIz() {
        return SmartDashboard.getNumber("SIz", Iz);
    }

    public void unload() {
        // unload the magazine
    }

    public boolean isReady() {
        if (atSpeed() && magazine.isThereABallTopForShooter()) {
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

    public void setTargetSpeed(double targetSpeed) {
        this.targetSpeed = targetSpeed;
        pidController.setSetPoint(targetSpeed);
    }

    public void shooterTest() {
        if (firstTime) {
            setTargetSpeed(SmartDashboard.getNumber("Set Velocity", 1500));//needs velocity
            firstTime = false;
        }
        //shooter.set(SmartDashboard.getNumber("Set Velocity", 1500));
        shooter.set(SHOOTER_SPEED + getCorrection());
        SmartDashboard.putNumber("Correction", getCorrection());
        SmartDashboard.putNumber("Actual Velocity", neo1.getEncoder().getVelocity());
    }

}