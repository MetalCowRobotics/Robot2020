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
    final double turretSpeed = .4;
    final int leftBound = 127; // not final num
    final int rightBound = -273; // not final num
    int deadZone = 30;
    int startPos = 0;
    boolean targeting = false;
    int targetTics = 0;
    boolean onTarget = false;

    //singletom
    private static final Turret instance = new Turret();

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
        double yaw = vision.getYawDegrees();
        if (targeting) {
            if (UtilityMethods.between(yaw, -4, 4)) {
                turret.stopMotor();
            } else {
                // if (yaw > 0 && encoder.getTics() > -50) { //left
                // turret.set(0.2);
                // } else if (yaw< 0 && encoder.getTics() < 50){ //right
                // turret.set(-0.2);
                // } else {
                // stopTurret();
                // }
                // set turrent power can deal with the limits
                if (yaw > 0) {
                    setTurretPower(.2);
                } else if (yaw < 0) {
                    setTurretPower(-.2);
                } else {
                    stopTurret();
                }
            }
        }
        SmartDashboard.putNumber("turret encoder", encoder.getTics());
        SmartDashboard.putNumber("yaw", yaw);
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
        setTargetTics((int) (encoder.getTics() + (degrees * 1.4)));
    }

    public void turnTurret(double power) { // for human control
        SmartDashboard.putNumber("power", power);
        if (!targeting) {
            setTurretPower(power);
        }
    }

    private void setTurretPower(double power) {
        if (power < 0 && encoder.getTics() < leftBound) {
            turret.set(UtilityMethods.absMin(power, turretSpeed));
        } else if (power > 0 && encoder.getTics() > rightBound) {
            turret.set(UtilityMethods.absMin(power, turretSpeed));
        } else {
            stopTurret();
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

    public int getTurretPosition() {
        return encoder.getTics();
    }
}