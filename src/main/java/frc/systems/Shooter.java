package frc.systems;

import frc.robot.RobotDashboard;
import frc.robot.RobotMap;

import com.revrobotics.CANPIDController;
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
    // private CANSparkMax neo3 = new CANSparkMax(2, MotorType.kBrushless);
    // private CANSparkMax neo4 = new CANSparkMax(3, MotorType.kBrushless);
    // private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1, neo2);
    private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1, neo2);
    private Magazine magazine = Magazine.getInstance();
    private Turret turret = Turret.getInstance();
    private double targetSpeed;// RPM's
    private boolean maintainSpeed = false;
    private static PIDController pidController;
    private static CANPIDController canPID;
    private static double P = .000015;
    private static double I = .00003;
    private static double D = 0;
    private static double Iz = 1000;
    private static double MinOutput = -1;
    private static double MaxOutput = 1;

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
        //canPID = neo1.getPIDController();
        pidController = new PIDController(0, P, I, D, Iz);
        RobotDashboard.getInstance().pushShooterPIDValues(P, I, D, Iz);
        // set PID coefficients
        // canPID.setP(P);
        // canPID.setI(I);
        // canPID.setD(D);
        // canPID.setIZone(Iz);
        // canPID.setFF(0);
        // canPID.setOutputRange(MinOutput, MaxOutput);

    }

    public static Shooter getInstance() {
        return instance;
    }

    public void run() {
        magazine.run();
        turret.run();
        if (maintainSpeed) {
            shooter.set(SHOOTER_SPEED + getCorrection());
            SmartDashboard.putNumber("Shooter_Velocity", neo1.getEncoder().getVelocity());
            // speed PID loop
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
        shooter.set(.35);
        // if (operator.getRT() > 0) {
        //     getCorrection();
        //     shooter.set(correction);
        // } else {
        //     stopShooter();
        // }

        // shooter.set(SmartDashboard.getNumber("ShooterPercent", .45));
        // maintainSpeed = true;
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
        // canPID.setP(getP());
        // canPID.setI(getI());
        // canPID.setD(getD());

        double correction = pidController.calculateAdjustment(neo1.getEncoder().getVelocity());
        System.out.println(targetSpeed+" vel:"+neo1.getEncoder().getVelocity()+" correction:"+correction);
        return 0;//correction
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

}