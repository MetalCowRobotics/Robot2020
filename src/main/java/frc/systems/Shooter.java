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
    private static double P = .00008;
    private static double I = .00001;
    private static double D = .0001;
    private static double Iz = 450;
    private static PIDController pidController;
    private static final double SHOOTER_SPEED = .45;
    private boolean firstTime = true;
    private double targetSpeed = 3000;// RPM's
    private boolean readyToShoot = false;
    private boolean prepDrum = false;

    // singleton instance
    private static final Shooter instance = new Shooter();

    private Shooter() {
        dashboard.pushShooterPIDValues(P, I, D, Iz);
        pidController = new PIDController(0, P, I, D, Iz);
        // sets motors to ccoast
        neo1.restoreFactoryDefaults();
        neo2.restoreFactoryDefaults();
        neo1.setIdleMode(IdleMode.kCoast);
        neo2.setIdleMode(IdleMode.kCoast);

        // sets motors to ramp
        neo1.setOpenLoopRampRate(.25);
        neo2.setOpenLoopRampRate(.25);
    }

    public static Shooter getInstance() {
        return instance;
    }

    public void run() {
        if (readyToShoot || prepDrum) {
            shooter.set(SHOOTER_SPEED + getCorrection());
        } else {
            shooter.stopMotor();
            magazine.stopLoadToTop();
        }
        hood.run();
        turret.run();
        magazine.run();
        funnel.run(magazine.isThereABallBottomToLoad());
        dashboard.pushShooterVelocity(neo1.getEncoder().getVelocity());
    }

    public boolean atSpeed() {
        double absTargetSpeed = Math.abs(targetSpeed);
        return UtilityMethods.between(Math.abs(neo1.getEncoder().getVelocity()), absTargetSpeed - 20, absTargetSpeed + 20);
    }

    public void prepareToShoot() {
        if (!readyToShoot) {
            prepDrum = false;
            readyToShoot = true;
            turret.startTargeting();
            // get target distance
            // set shooter speed
            // setTargetSpeed(SmartDashboard.getNumber("Set Velocity", 1500));//needs
            // velocity
            // setTargetSpeed(dashboard.getShooterTargetVelocity(2800));
            setSpeed();
            // set hood poistion
            hood.setPosition(10);
            magazine.loadBallInShootingPosition();
        }
    }

    public void beginTargetting() {
        turret.startTargeting();
        hood.setPosition(2);
    }

    public void runDrum(double speed) {
        setTargetSpeed(speed);
        // setSpeed();
        prepDrum = true;
    }

    public void stopShooter() {
        readyToShoot = false;
        shooter.stopMotor();
        magazine.stopLoadToTop();
        turret.stopTargeting();
    }

    public void shootBallWhenReady() {
        if (readyToShoot && atSpeed()) {
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
        pidController.set_kP(getP());
        pidController.set_kI(getI());
        pidController.set_kD(getD());
        pidController.set_Iz(getIz());
        double correction = pidController.calculateAdjustment(neo1.getEncoder().getVelocity());
        System.out.println("target V:"+targetSpeed+"   actual V:"+neo1.getEncoder().getVelocity()+"   correction:"+correction);
        return correction;
    }

    private double getP() {
        return dashboard.getShooterP(P);
    }

    private double getI() {
        return dashboard.getShooterI(I);
    }

    private double getD() {
        return dashboard.getShooterD(D);
    }

    private double getIz() {
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
        this.targetSpeed = targetSpeed + dashboard.speedCorrection();
        pidController.setSetPoint(targetSpeed);
        dashboard.pushShooterTargetVelocity(targetSpeed);
    }

    // testing
    public void setupShooter() {
        setTargetSpeed(SmartDashboard.getNumber("Set Velocity", 1500));// needs velocity
        // setSpeed();
        firstTime = false;
        readyToShoot = true;
    }

    public void shooterTest() {
        if (firstTime) {
            setTargetSpeed(SmartDashboard.getNumber("Set Velocity", 1500));// needs velocity
            // setSpeed();
            firstTime = false;
        }
        shooter.set(SHOOTER_SPEED + getCorrection());
        SmartDashboard.putNumber("Correction", getCorrection());
        SmartDashboard.putNumber("Actual Velocity", neo1.getEncoder().getVelocity());
    }

    public void rotateTurret(double turretAdjustment) {
        turret.turnTurret(turretAdjustment);
    }

    public void manualAdjustHood(double hoodAdjustment) {
        hood.manualAdjustment(hoodAdjustment);
    }

    private void setSpeed() {
        if (vision.getTargetDistance() > 20) {
            setTargetSpeed(3000);
        } else if (vision.getTargetDistance() > 5) {
            setTargetSpeed(2500);
        } else {
            setTargetSpeed(1750);
        }
    }

    private double determineSpeedFromDistance(double distance) {
        double adjustment = (3050 - 2700) / (22 - 5);
        return Math.min((distance - 5) * adjustment, 3500);
        // if (distance > 5) {
        //     return 3000;
        // } else {
        //     return 2700;
        // }
    }

}