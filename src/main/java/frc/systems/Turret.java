package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.FC_JE_0149Encoder;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.RobotMap;

public class Turret {
    private static MCR_SRX turret = new MCR_SRX(RobotMap.Turret.TURRET_MOTOR);
    private static FC_JE_0149Encoder encoder = new FC_JE_0149Encoder(5, 4);
    private static final Vision vision = Vision.getInstance();
    private static final Turret instance = new Turret();
    final double turretSpeed = .4;
    final int leftBound = 127; // not final num
    final int rightBound = -273; // not final num
    int deadZone = 30;
    int startPos = 0;
    boolean targeting = false;
    int targetTics = 0;
    boolean onTarget = false;

    private Turret() {
        turret.configFactoryDefault();
        turret.setNeutralMode(NeutralMode.Brake);
        resetTurretEncoder();
        targetTics = startPos;
        stopTurret();
    }

    public static Turret getInstance() {
        return instance;
    }

    public void run() {
        if (targeting) {
            //rotateTurret(vision.getYawDegrees());
            double totalTicsDiff = targetTics - encoder.getTics();
            if (Math.abs(totalTicsDiff) < deadZone) {
                turret.stopMotor();
                onTarget = true;
            } else {
                onTarget = false;
                if (totalTicsDiff > 0) {
                    turret.set(-turretSpeed);
                }
                if (totalTicsDiff < 0) {
                    turret.set(turretSpeed);
                }
            }
            System.out.println("Target" + targetTics + "; encoder Tics " + encoder.getTics());
        }
        // turret.stopMotor();
    }

    public boolean onTarget() {
        return onTarget;
    }

    public void startTargeting() {
        targeting = true;
    }

    public void stopTargeting() {
        targeting = false;
    }

    // 4096tics = 360 degrees 11.25tics = 1 degree
    public void rotateTurret(double degrees) { // for vision
        setTargetTics((int) (encoder.getTics() + (degrees * 11)));
    }

    public void turnTurret(double power) { // for human control
        SmartDashboard.putNumber("turret encoder", encoder.getTics());
        SmartDashboard.putNumber("power", power);
        if (!targeting) {
            if (power < 0 && encoder.getTics() < leftBound) {
                turret.set(UtilityMethods.absMin(power, 0.4));    
                
            } else if (power > 0 && encoder.getTics() > rightBound) {
                turret.set(UtilityMethods.absMin(power, 0.4));
            
            } else {
                turret.stopMotor();
            }
                // if (UtilityMethods.between(encoder.getTics(), rightBound, leftBound)) {
                //     turret.set(UtilityMethods.absMin(power, 0.4));
                // }
                // else {
                //     turret.stopMotor();
                // }
        }
    }

    public void stopTurret() {
        turret.stopMotor();
    }

    public void resetTurretEncoder() {
        encoder.reset();
        startPos = encoder.getTics();
    }

    private void setTargetTics(int tics) {
        if (tics < 0) {
            targetTics = Math.min(tics, leftBound);
        } else {
            targetTics = Math.min(tics, rightBound);
        }
    }
}