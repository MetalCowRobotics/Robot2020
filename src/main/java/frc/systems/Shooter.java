package frc.systems;

import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.PDController;
import frc.lib14.UtilityMethods;
import frc.lib14.XboxControllerMetalCow;
import frc.systems.Magazine;

public class Shooter {
    /**
     *
     */
    private static final double MOTOR_MAX_RPM = 5874.0;
    private static final double SHOOTER_SPEED = .65;
    private static final XboxControllerMetalCow operator = new XboxControllerMetalCow(1);
    private static CANSparkMax neo1 = new CANSparkMax(RobotMap.Shooter.TOP_MOTOR_ID, MotorType.kBrushless);
    // private static CANSparkMax neo2 = new CANSparkMax(RobotMap.Shooter.BOTTOM_MOTOR_ID, MotorType.kBrushless);
    // private CANSparkMax neo3 = new CANSparkMax(2, MotorType.kBrushless);
    // private CANSparkMax neo4 = new CANSparkMax(3, MotorType.kBrushless);
    // private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1, neo2);
    private static SpeedControllerGroup shooter = new SpeedControllerGroup(neo1);
    private Magazine magazine = Magazine.getInstance();
    private Turret turret = Turret.getInstance();
    private double targetSpeed;// RPM's
    private boolean maintainSpeed = false;

    // singleton instance
    private static final Shooter instance = new Shooter();
    private PDController PD;

    double speed;

    double P = 2.0;
    double I = 1.0;
    double D = 2.5;
    double integral, correction, derivative, error, previous_error = 0;

    private Shooter() {
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
        turret.run();
        if (maintainSpeed) {
            shooter.set(getCorrection());
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
        // if (operator.getRT() > 0) {
        //     getCorrection();
        //     shooter.set(correction);
        // } else {
        //     stopShooter();
        // }

        shooter.set(SHOOTER_SPEED);
        maintainSpeed = true;
    }

    public void stopShooter() {
        shooter.stopMotor();
        maintainSpeed = false;
        magazine.stopLoadToTop();
    }

    public double getCorrection() {
        // PD = new PDController(speed);

        error = speed * MOTOR_MAX_RPM - neo1.getEncoder().getVelocity(); // Error = Target - Actual
        integral += (error * .02); // Integral is increased by the error*time (which is .02 seconds using normal
                                   // IterativeRobot)
        derivative = (error - previous_error);
        correction = (P * error + I * integral + D * derivative) / MOTOR_MAX_RPM;
        previous_error = error;

        SmartDashboard.putNumber("RT", operator.getRT());
        SmartDashboard.putNumber("error", P * error);// RPM
        SmartDashboard.putNumber("integral", I * integral);// RPM
        SmartDashboard.putNumber("derivative", D * derivative);// RPM
        SmartDashboard.putNumber("correction", correction);// RPM
        SmartDashboard.putNumber("previous error", previous_error);// RPM
        SmartDashboard.putNumber("velocity", neo1.getEncoder().getVelocity());// RPM
        return correction;
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
        integral = 0;
    }

}