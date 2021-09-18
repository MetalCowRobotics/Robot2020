package frc.systems;

import com.ctre.phoenix.Util;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.FC_JE_0149Encoder;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.RobotDashboard;
import frc.robot.RobotMap;

public class Hood {
    public MCR_SRX hood = new MCR_SRX(RobotMap.Hood.HOOD_MOTOR);
    private static AnalogPotentiometer pot = new AnalogPotentiometer(0, 10000, -2200);
    private static FC_JE_0149Encoder encoder = new FC_JE_0149Encoder(3, 2);
    private static RobotDashboard dashboard = RobotDashboard.getInstance();

    private static final double REVS_PER_INCH = 11.8;//12.7;
    private static final double TICS_PER_REV = 44.4;
    // private static final double VOLTS_PER_INCH = 211.5;
    private static final int UPPER_BOUND = (int) (4.2*REVS_PER_INCH*TICS_PER_REV);
    private static final int LOWER_BOUND = 0;
    private static double adjustment = 0;
    private static double targetTics = 0;

    private double startingPosition = 0;

    // Singleton
    private static final Hood instance = new Hood();

    private Hood() {
        hood.configFactoryDefault();
        hood.setNeutralMode(NeutralMode.Coast);
        startingPosition = (pot.get() / 100 * 0.33 + 0.5)*REVS_PER_INCH*TICS_PER_REV;//1.64  1.95
        targetTics = startingPosition;
    }

    public static Hood getInstance() {
        return instance;
    }

    public void run() {
        double currentTics = getCurrentTics();
        //dashboard.pushHoodPositionText((int) currentTics); 
        double target = targetTics + adjustment + dashboard.hoodCorrection();
        double error = (target - currentTics) / 100;
        error = UtilityMethods.absMin(error, 1);
        error = UtilityMethods.absMax(error, .3);
        if (Math.abs(target - currentTics) < 4) {
            hood.stopMotor();
        } else {
            //TODO why setting speed to 0.4?
            hood.set(UtilityMethods.copySign(error, .4));
            System.out.println("tracking to target");
        }

        SmartDashboard.putNumber("HoodEncoder", encoder.getTics());
        SmartDashboard.putNumber("HoodEncoderPlusOffset", encoder.getTics() + startingPosition);
        SmartDashboard.putNumber("PotTics", pot.get());
        SmartDashboard.putNumber("HoodTarget", targetTics);
        SmartDashboard.putNumber("HoodInches", getCurrentTics() / TICS_PER_REV / REVS_PER_INCH);
    }

    private double inchesToTics(double inches) {
        //return (inches - 1) * REVS_PER_INCH * TICS_PER_REV;
        return inches * REVS_PER_INCH * TICS_PER_REV;

    }

    public void manualAdjustment(double y) {
        if (y > .1) {
            if (targetTics + adjustment + dashboard.hoodCorrection() -1 > (LOWER_BOUND)) {
                adjustment -= 2;
            }
        } else if (y < -.1) {
            // if (targetTics + adjustment + dashboard.hoodCorrection() + 1 < (UPPER_BOUND)) {
                adjustment += 2;
            // }
        }
        // System.out.println("targetTics" + targetTics);
        // System.out.println("adjustment" + adjustment);
    }

    private void setHoodSpeed(double speed) {
        if (speed > 0) {
            if (targetTics + adjustment + dashboard.hoodCorrection() > (LOWER_BOUND)) {
                hood.set(speed);
            } else {
                hood.stopMotor();
            }
        } else if (speed < 0) {
            if (targetTics + adjustment + dashboard.hoodCorrection() < (UPPER_BOUND)) {
                hood.set(speed);
            } else {
                hood.stopMotor();
            }
        } else {
            hood.stopMotor();
        }
    }

    // automated set hood position
    public void setPosition(double distance) {
        if (distance > 20) {
            setFarShot();
        } else if (distance > 7) {
            setTenFoot();
        } else {
            setSafeZone();
        }
        resetAdjustment();
    }

    public void setRequiredPosition(double dist) {
        adjustment = 0;
        targetTics = inchesToTics(dist);
    }

    public void setFarShot() {
        targetTics = inchesToTics(3.9);
    }

    public void setTenFoot() {
        targetTics = inchesToTics(3.45);
    }

    public void setSafeZone() {
        targetTics = inchesToTics(1);
    }

    public int getCurrentTics() {
        //return (int) ((pot.get() + 200 / 3) * REVS_PER_INCH * TICS_PER_REV);
        return (int) encoder.getTics();
    }

    private void resetAdjustment() {
        adjustment = 0;
    }

}
