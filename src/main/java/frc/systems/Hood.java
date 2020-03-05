package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.FC_JE_0149Encoder;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.RobotMap;

public class Hood {
    private static MCR_SRX hood = new MCR_SRX(RobotMap.Hood.HOOD_MOTOR);
    //public static FC_JE_0149Encoder encoder = new FC_JE_0149Encoder(3, 2);
    public static AnalogPotentiometer encoder = new AnalogPotentiometer(0, 10000, -2378);

    private static final int STARTING_POS = 0;
    private static final double REVS_PER_INCH = 11.8;
    private static final double TICS_PER_REV = 225.5;
    private static final int UPPER_BOUND = 1015;
    private static final int LOWER_BOUND = 0;

    private static double adjustment = 0;
    private static double targetTics = 0;
    private static double lastRememberedInches = 3.5;

    //Singleton
    private static final Hood instance = new Hood();

    private Hood() {
        hood.configFactoryDefault();
        hood.setNeutralMode(NeutralMode.Coast);
        targetTics = getCurrentTics();
    }

    public static Hood getInstance() {
        return instance;
    }

    public void run() {
        calculateTicks(lastRememberedInches);
        int currentTics = getCurrentTics();
        double target = targetTics + adjustment;
        System.out.println("EncoderTics:" + currentTics);
        double error = (target - currentTics) / 100;
        // double error = ((targetTics + 3) - currentTics) / 100;
        error = UtilityMethods.absMin(error, .5);
        if (Math.abs(target - currentTics) < 5) {
            hood.stopMotor();
        } else {
            // do i need a min to at least turn the motor
            hood.set(error);
        }
        // if (Math.abs((targetTics + 3) - currentTics) < 5) {
        //     hood.stopMotor();
        // } else {
        //     hood.set(error);
        // }

        System.out.println("HoodTics:" + currentTics);
        SmartDashboard.putNumber("Current Tics", currentTics);
        SmartDashboard.putNumber("Encoder Tics", encoder.get());
    }

    private void calculateTicks(double inches) {
        double totalRevs = inches * REVS_PER_INCH;
        targetTics = TICS_PER_REV * totalRevs + adjustment;
        targetTics = UtilityMethods.absMax(targetTics, LOWER_BOUND);
        targetTics = UtilityMethods.absMin(targetTics, UPPER_BOUND);
    }

    private double inchesToTics(double inches) {
        return inches * REVS_PER_INCH * TICS_PER_REV;
    }

    //untested
    private void setTarget(double gotoTics) {
        if (UtilityMethods.between(gotoTics, LOWER_BOUND, UPPER_BOUND))
            targetTics = gotoTics;
    }
    
    public void manualAdjustment(double y) {
        if (y > .1) {
            if (targetTics > (LOWER_BOUND + .5)) {
                adjustment -= .5;
            }
        } else if (y < -.1) {
            if (targetTics < ( UPPER_BOUND - .5)) {
                adjustment += .5;
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
        resetAdjustment();
    }

    public void setFarShot() {
        // setTarget(inchesToTics(3.9));
        calculateTicks(4.5);
        lastRememberedInches = 4.5;
    }

    public void setTenFoot() {
        // setTarget(inchesToTics(3.5));
        calculateTicks(3.9);
        lastRememberedInches = 3.9;
    }

    public void setSafeZone() {
        // setTarget(inchesToTics(2.8));
        calculateTicks(2.8);
        lastRememberedInches = 2.8;
    }

    private int getCurrentTics() {
        return (int)encoder.get() + STARTING_POS;
    }

    private void resetAdjustment() {
        adjustment = 0;
    }
}
