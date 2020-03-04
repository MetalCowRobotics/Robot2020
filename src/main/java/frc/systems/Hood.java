package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.FC_JE_0149Encoder;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.RobotMap;

public class Hood {

    private static final int STARTING_POS = 1833;
    private static final double REVS_PER_INCH = 11.8;
    private static final double TICS_PER_REV = 44.4;
    private static final int UPPER_BOUND = 2465;
    private static final int LOWER_BOUND = 0;
    private static MCR_SRX hood = new MCR_SRX(RobotMap.Hood.HOOD_MOTOR);
    public static FC_JE_0149Encoder encoder = new FC_JE_0149Encoder(3, 2);

    private static double adjustment = 0;
    private static double totalRevs = 0;
    private static double targetTics = 0;

    private static final Hood instance = new Hood();

    private Hood() {
        hood.configFactoryDefault();
        hood.setNeutralMode(NeutralMode.Coast);
        resetEncoder();
        targetTics = getCurrentTics();
    }

    public static Hood getInstance() {
        return instance;
    }

    private void calculateTicks(double inches) {
        totalRevs = inches * REVS_PER_INCH;
        targetTics = TICS_PER_REV * totalRevs + adjustment;
        targetTics = UtilityMethods.absMax(targetTics, LOWER_BOUND);
        targetTics = UtilityMethods.absMin(targetTics, UPPER_BOUND);
    }

    public void run() {
        int currentTics = getCurrentTics();
        System.out.println("EncoderTics:" + currentTics);
        double error = ((targetTics + 3) - currentTics) / 100;
        error = UtilityMethods.absMin(error, .5);
        if (Math.abs((targetTics + 3) - currentTics) < 5) {
            hood.stopMotor();
        } else {
            hood.set(error);
        }

        System.out.println("HoodTics:" + currentTics);
        SmartDashboard.putNumber("Current Tics", currentTics);
        SmartDashboard.putNumber("Encoder Tics", encoder.getTics());

    }

    public void manualAdjustment(double y) {
        if (y > .1) {
            if (targetTics > (LOWER_BOUND + 2)) {
                adjustment -= 2;
            }
        } else if (y < -.1) {
            if (targetTics < ( UPPER_BOUND - 2)) {
                adjustment += 2;
            }
        }
    }

    // automated set hood position
    public void setPosition(double distance) {
        if (distance > 25) {
            setFarShot();
        } else if (distance > 5) {
            setTenFoot();
        } else {
            setSafeZone();
        }
    }

    public void setFarShot() {
        calculateTicks(3.9);
    }

    public void setTenFoot() {
        calculateTicks(3.5);
    }

    public void setSafeZone() {
        calculateTicks(2.8);
    }

    public void resetEncoder() {
        encoder.reset();
    }

    private int getCurrentTics() {
        return encoder.getTics() + STARTING_POS;
    }

    public void resetAdjustment() {
        adjustment = 0;
    }
}
