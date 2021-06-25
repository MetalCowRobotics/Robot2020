package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.FC_JE_0149Encoder;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.RobotDashboard;
import frc.robot.RobotMap;

public class Turret {
    private static MCR_SRX turret = new MCR_SRX(RobotMap.Turret.TURRET_MOTOR);
    private static FC_JE_0149Encoder encoder = new FC_JE_0149Encoder(5, 4);
    private static final Vision vision = Vision.getInstance();
    private static final RobotDashboard dashboard = RobotDashboard.getInstance();
    private final double MAX_TURRET_SPEED = 0.5;
    private final double MIN_TURRET_SPEED = 0.1;
    private int leftBound = 127; // not final num
    private int rightBound = -264; // was -273 not final num
    private boolean targeting = false;
    private boolean onTarget = false;
    private double adjustment = 0;
    private double targetTics = 0;
    private boolean firstTime = true;
    private boolean firstTargeting = false;
    private double yawOverride = 0;

    // singleton
    private static final Turret instance = new Turret();

    private Turret() {
        turret.configFactoryDefault();
        turret.setNeutralMode(NeutralMode.Brake);
        resetTurretEncoder();
        stopTurret();
    }

    public static Turret getInstance() {
        return instance;
    }

    public void run() {
        if (firstTime) {
            firstTime = false;
            double offset = dashboard.getTurretOffset();
            leftBound += offset;
            rightBound -= offset;
        }

        // if targeting
        // calculate yawCorrection
        // vision + dashboard + override
        // if on target set to 0
        // else
        // override

        // double yawCorrection = UtilityMethods.map(vision.getTargetDistance(), 6, 30,
        // dashboard.yawCorrectionShort(), dashboard.yawCorrectionLong());
        // double yaw = vision.getYawDegrees() + dashboard.yawCorrectionShort();
        // if (vision.distance > 20) {
        // yawCorrection = vision.getYawDegrees() + dashboard.yawCorrectionLong();
        // } else {
        double yawCorrection = vision.getYawDegrees() + dashboard.yawCorrectionShort();
        // }
        double yaw = vision.getYawDegrees() + yawCorrection + yawOverride;

        // PID
        double currentTics = getTurretPosition();
        double target = targetTics + adjustment;
        double error = (target - currentTics) / 30;
        error = UtilityMethods.absMin(error, MAX_TURRET_SPEED);
        error = UtilityMethods.absMax(error, MIN_TURRET_SPEED);
        if (Math.abs(target - currentTics) < 4) {
            turret.stopMotor();
        } else if (!targeting) {
            turret.set(-error);
        }

        if (targeting) {
            if (firstTargeting) {
                adjustment = 0;
            }
            targetTics = getTurretPosition();

            if (UtilityMethods.between(yaw, -1, 1)) {
                turret.stopMotor();
                onTarget = true;
            } else {
                onTarget = false;
                // rotateTurret(yaw);
                if (Math.abs(yaw) > 5) {
                    normalAdjustment(yaw);
                } else {
                    slowAdjustment(yaw);
                }
            }
            firstTargeting = false;
        } else {
            firstTargeting = true;
        }
        SmartDashboard.putNumber("turret encoder", encoder.getTics());
    }

    private void normalAdjustment(double yaw) {
        if (yaw > 0) {
            setTurretPower(.2);
        } else if (yaw < 0) {
            setTurretPower(-.2);
        } else {
            stopTurret();
        }
    }

    private void slowAdjustment(double yaw) {
        if (yaw > 0) {
            setTurretPower(.1);
        } else if (yaw < 0) {
            setTurretPower(-.1);
        } else {
            stopTurret();
        }
    }

    public boolean onTarget() {
        return onTarget;
    }

    public void startTargeting() {
        targeting = true;
        adjustment = 0;
        yawOverride = 0;
    }

    public void stopTargeting() {
        targeting = false;
        // adjustment = 0;
    }

    // TODO does this work?
    // 4096tics = 360 degrees 11.25tics = 1 degree
    // 360 * 11.25 = 4050 Not sure about this line ^

    private void rotateTurret(double degrees) { // for vision
        setTargetTics((int) (encoder.getTics() + (degrees * 1.4)));
    }

    public void turnTurret(double x) { // for human control
        if (targeting) {
            yawOverride += x/2;
        } else if (x > .1) {
            adjustment -= 1;
        } else if (x < -.1) {
            adjustment += 1;
        }
        // if (!targeting) {
        // setTurretPower(power);
        // } else {
        // if (Math.abs(power) > 0) {
        // adjustment += UtilityMethods.copySign(power, 1);
        // }
        // }
        SmartDashboard.putNumber("turretAdjustment", adjustment);
    }

    public void setTurretPower(double power) {
        if (power < 0 && encoder.getTics() < leftBound) {
            turret.set(UtilityMethods.absMin(power, MAX_TURRET_SPEED));
        } else if (power > 0 && encoder.getTics() > rightBound) {
            turret.set(UtilityMethods.absMin(power, MAX_TURRET_SPEED));
        } else {
            stopTurret();
        }
    }

    public void stopTurret() {
        turret.stopMotor();
    }

    public void resetTurretEncoder() {
        encoder.reset();
    }

    private void setTargetTics(int tics) {
        // TODO check this logic
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