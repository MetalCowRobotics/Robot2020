package frc.systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.PIDController;
import frc.lib14.UtilityMethods;
import frc.robot.RobotDashboard;
import frc.robot.RobotMap;

public class Shooter {
    private static CANSparkMax neo1 = new CANSparkMax(RobotMap.Shooter.TOP_MOTOR, MotorType.kBrushless);
    private static CANSparkMax neo2 = new CANSparkMax(RobotMap.Shooter.BOTTOM_MOTOR, MotorType.kBrushless);
    private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1, neo2);
    private Magazine magazine = Magazine.getInstance();
    private Hood hood = Hood.getInstance();
    private Turret turret = Turret.getInstance();
    private Funnel funnel = Funnel.getInstance();
    private static Vision vision = Vision.getInstance();
    private static RobotDashboard dashboard = RobotDashboard.getInstance();
    private static double P = .00003;
    private static double I = .000025;
    private static double D = 0;
    private static double Iz = 400;
    private static PIDController pidController;
    private static final double SHOOTER_SPEED = .45;
    private boolean firstTime = true;
    private double targetSpeed = 3000;// RPM's
    private boolean readyToShoot = false;

    // singleton instance
    private static final Shooter instance = new Shooter();

    private Shooter() {
        dashboard.pushShooterPIDValues(P, I, D, Iz);
        pidController = new PIDController(0, P, I, D, Iz);
        neo1.restoreFactoryDefaults();
        neo2.restoreFactoryDefaults();
        neo1.setIdleMode(IdleMode.kCoast);        
        neo2.setIdleMode(IdleMode.kCoast);
        turret.resetTurretEncoder();
    }

    public static Shooter getInstance() {
        return instance;
    }

    public void run() {
        hood.run();

        if (readyToShoot) {
            // speed PID loop
            shooter.set(SHOOTER_SPEED + getCorrection());
        } else {
            shooter.stopMotor();
            magazine.stopLoadToTop();
            //hood.resetAdjustment();
        }

        magazine.run();
        funnel.run(magazine.isThereABallBottomForShooter());
        //turret.rotateTurret(-(int)Math.round(vision.getYawDegrees()));
        dashboard.pushShooterVelocity(targetSpeed, neo1.getEncoder().getVelocity());
    }

    public boolean atSpeed() {
        double absTargetSpeed = Math.abs(targetSpeed);
        return UtilityMethods.between(Math.abs(neo1.getEncoder().getVelocity()), absTargetSpeed - 50, absTargetSpeed + 50);
    }

    public void prepairToShoot() {
        readyToShoot = true;
        //get target distance
        //set shooter speed
        // setTargetSpeed(SmartDashboard.getNumber("Set Velocity", 1500));//needs velocity
        setTargetSpeed(dashboard.getShooterTargetVelocity(1500));
        //set hood poistion
        hood.setPosition(vision.getTargetDistance());
        magazine.loadBallInShootingPosition();
    }

    public void stopShooter() {
        shooter.stopMotor();
        readyToShoot = false;
        magazine.stopLoadToTop();
    }

    public void shootBallWhenReady() {
        if (readyToShoot && isReady()) {
            magazine.feedOneBall();
        }
    }

    public void shootBall() {
        if (readyToShoot) {
            magazine.feedOneBall();
        }
    }

    public int ballShots() {
        return magazine.getCounted();
    }

    public double getCorrection() {
        // if (P!=dashboard.getShooterP(P)) {
        //     P = dashboard.getShooterP(P);
        //     pidController.set_kP(P);
        // }
        pidController.set_kP(dashboard.getShooterP(P));
        pidController.set_kI(getI());
        pidController.set_kD(getD());
        pidController.set_Iz(getIz());
        double correction = pidController.calculateAdjustment(neo1.getEncoder().getVelocity());
        System.out.println("target V:"+targetSpeed+"   actual V:"+neo1.getEncoder().getVelocity()+"   correction:"+correction);
        return correction;
    }

    private double getP() {
        // return SmartDashboard.getNumber("SkP", P);
        return dashboard.getShooterP(P);
    }

    private double getI() {
        // return SmartDashboard.getNumber("SkI", I);
        return dashboard.getShooterI(I);
    }

    private double getD() {
        // return SmartDashboard.getNumber("SkD", D);
        return dashboard.getShooterD(D);
    }

    private double getIz() {
        // return SmartDashboard.getNumber("SIz", Iz);
        return dashboard.getShooterIz(Iz);
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

    public void setTargetSpeed(double targetSpeed) {
        this.targetSpeed = targetSpeed;
        pidController.setSetPoint(targetSpeed);
    }

    //testing
    public void setupShooter() {
        setTargetSpeed(SmartDashboard.getNumber("Set Velocity", 1500));//needs velocity
        firstTime = false;
        //shooter.set(SmartDashboard.getNumber("Set Velocity", 1500));
        readyToShoot = true;
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