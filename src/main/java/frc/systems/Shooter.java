package frc.systems;

import frc.robot.RobotDashboard;
import frc.robot.RobotMap;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
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
    // private static CANSparkMax neo2 = new CANSparkMax(RobotMap.Shooter.BOTTOM_MOTOR_ID, MotorType.kBrushless);
    // private CANSparkMax neo3 = new CANSparkMax(2, MotorType.kBrushless);
    // private CANSparkMax neo4 = new CANSparkMax(3, MotorType.kBrushless);
    // private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1, neo2);
    // private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1, neo2);
    private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1);
    private Magazine magazine;// = Magazine.getInstance();
    private Turret turret;// = Turret.getInstance();
    private double targetSpeed;// RPM's
    private boolean maintainSpeed = false;
    private static PIDController pidController;
    private static CANPIDController canPID;
    private static double P = .000015;
    private static double I = .00003;
    private static double D = 0;
    private static double Iz = 200;
    private static double MinOutput = -.75;
    private static double MaxOutput = .75;


    // singleton instance
    private static final Shooter instance = new Shooter();

    private Shooter() {
        // neo1.restoreFactoryDefaults();
        // neo1.setOpenLoopRampRate(.25);
        // neo1.setClosedLoopRampRate(.25);
        // neo2.restoreFactoryDefaults();
        // neo2.setOpenLoopRampRate(.25);
        // neo2.setClosedLoopRampRate(.25);

        // leftShooter = new SpeedControllerGroup(neo3, neo4);
        // neoTwo.follow(neoOne);
        // newOne.getEncoder
        // newTwo.clone()
        // neoOne.set(speed);
        // SpeedControllerGroup shooterR = new SpeedControllerGroup(neoOne, neoTwo);
        // shooterL.follow(shooterR);
        canPID = neo1.getPIDController();
        //pidController = new PIDController(0, P, I, D, Iz);
        RobotDashboard.getInstance().pushShooterPIDValues(P, I, D, Iz);
        //set PID coefficients
        canPID.setP(P);
        canPID.setI(I);
        canPID.setD(D);
        canPID.setIZone(Iz);
        canPID.setFF(0);
        canPID.setOutputRange(MinOutput, MaxOutput);
        
        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", P);
        SmartDashboard.putNumber("I Gain", I);
        SmartDashboard.putNumber("D Gain", D);
        SmartDashboard.putNumber("I Zone", Iz);
        SmartDashboard.putNumber("Max Output", MaxOutput);
        SmartDashboard.putNumber("Min Output", MinOutput);
        SmartDashboard.putNumber("Set Velocity", 0);

    }

    public static Shooter getInstance() {
        return instance;
    }

    public void run() {
        // magazine.run();
        // turret.run();

        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("P Gain", P);
        double i = SmartDashboard.getNumber("I Gain", I);
        double d = SmartDashboard.getNumber("D Gain", D);
        double iz = SmartDashboard.getNumber("I Zone", Iz);
        double max = SmartDashboard.getNumber("Max Output", MaxOutput);
        double min = SmartDashboard.getNumber("Min Output", MinOutput);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
        if((p != P)) { canPID.setP(p); P = p; }
        if((i != I)) { canPID.setI(i); I = i; }
        if((d != D)) { canPID.setD(d); D = d; }
        if((iz != Iz)) { canPID.setIZone(iz); Iz = iz; }
        if((max != MaxOutput) || (min != MinOutput)) { 
            canPID.setOutputRange(min, max); 
            MinOutput = min; MaxOutput = max; 
        }

        if (maintainSpeed) {
            //shooter.set(SHOOTER_SPEED + getCorrection());
            // speed PID loop
        }
        SmartDashboard.putNumber("Shooter_Velocity", neo1.getEncoder().getVelocity());
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
        double velocity = SmartDashboard.getNumber("Set Velocity", 1500);
        canPID.setReference(velocity, ControlType.kVelocity);
        //neo2.follow(neo1);
        SmartDashboard.putNumber("Shooter_Velocity", neo1.getEncoder().getVelocity());


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
        // pidController.set_kP(getP());
        // pidController.set_kI(getI());
        // pidController.set_kD(getD());
        canPID.setP(getP());
        canPID.setI(getI());
        canPID.setD(getD());

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