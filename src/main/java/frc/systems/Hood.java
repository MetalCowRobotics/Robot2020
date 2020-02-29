package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.FC_JE_0149Encoder;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.RobotMap;

public class Hood {

    private static final int STARTING_POS = 2156;
    private static final double REVS_PER_INCH = 11.8;
    private static final int UPPER_BOUND = 2465;
    private static final int LOWER_BOUND = 0;
    private static MCR_SRX hood = new MCR_SRX(RobotMap.Hood.HOOD_MOTOR);
    public static FC_JE_0149Encoder encoder = new FC_JE_0149Encoder(3, 2);

    private static final double TICS_PER_REV = 44.4;
    private static double totalRevs = 0;
    private static double targetTics = 0;

    private static final Hood instance = new Hood();

    private Hood() {
        hood.configFactoryDefault();
        hood.setNeutralMode(NeutralMode.Coast);
        targetTics = getCurrentTics();
    }

    public static Hood getInstance() {
        return instance;
    }

    private void calculateTicks(double inches) {
        totalRevs = inches * REVS_PER_INCH;
        targetTics = TICS_PER_REV * totalRevs;
        targetTics = UtilityMethods.absMax(targetTics, LOWER_BOUND);
        targetTics = UtilityMethods.absMin(targetTics, UPPER_BOUND);
    }

    public void run() {
        int currentTics = getCurrentTics();
        System.out.println("EncoderTics:" + currentTics);
        double error = ((targetTics + 3) - currentTics) / 100;
        error = UtilityMethods.absMin(error, .5);
        if (Math.abs((targetTics + 3) - currentTics) < 5) {
            SmartDashboard.putBoolean("in Deadzone", true);
            hood.stopMotor();
        } else {
            SmartDashboard.putBoolean("in Deadzone", true);
            hood.set(error);
        }

        System.out.println("HoodTics:" + currentTics);
        SmartDashboard.putNumber("Current Tics", currentTics);
        SmartDashboard.putNumber("Encoder Tics", encoder.getTics());

    }

    public void manualAdjustment(double y) {
        if (y > .1) {
            if (targetTics > (LOWER_BOUND + 3)) {
                targetTics -= 3;
            }
        } else if (y < -.1) {
            if (targetTics < ( UPPER_BOUND - 3)) {
                targetTics += 3;
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
        calculateTicks(4.5);
        // System.out.println("EncoderTics:" + currentTics);
        // double error = ((TARGET_TICS+3) - currentTics) / 100;
        // if (error > .5) {
        // error = .5;
        // }
        // if (error < -.5) {
        // error = -.5;
        // }
        // hood.set(error);
    }

    public void setTenFoot() {
        calculateTicks(4.1);
        // System.out.println("EncoderTics:" + currentTics);
        // double error = ((TARGET_TICS+3) - currentTics) / 100;
        // if (error > .5) {
        // error = .5;
        // }
        // if (error < -.5) {
        // error = -.5;
        // }
        // hood.set(error);
    }

    public void setSafeZone() {
        calculateTicks(3.4);
        // System.out.println("EncoderTics:" + currentTics);
        // double error = ((TARGET_TICS+3) - currentTics) / 100;
        // if (error > .5) {
        // error = .5;
        // }
        // if (error < -.5) {
        // error = -.5;
        // }
        // hood.set(error);
    }

    public void resetEncoder() {
        encoder.reset();
    }

    private int getCurrentTics() {
        return encoder.getTics() + STARTING_POS;
    }
}
