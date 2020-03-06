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
    private static AnalogPotentiometer pot = new AnalogPotentiometer(0, 10000, -2378);
    private static FC_JE_0149Encoder encoder = new FC_JE_0149Encoder(3, 2);

    private static final double REVS_PER_INCH = 11.8;
    private static final double TICS_PER_REV = 44.4;
    private static final int UPPER_BOUND = 1015;
    private static final int LOWER_BOUND = 0;

    private static double adjustment = 0;
    private static double targetTics = 0;

    private double startingPosition = 0;

    // Singleton
    private static final Hood instance = new Hood();

    private Hood() {
        hood.configFactoryDefault();
        hood.setNeutralMode(NeutralMode.Coast);
        startingPosition = (getStartingTics()/211.5)*REVS_PER_INCH*TICS_PER_REV;
    }

    public static Hood getInstance() {
        return instance;
    }

    public void run() {
        pushPosition();
        double currentTics = getCurrentTics();
        double target = targetTics + adjustment;
        double error = (target - currentTics) / 100;
        error = UtilityMethods.absMin(error, .5);
        if (Math.abs(target - currentTics) < 5) {
            hood.stopMotor();
        } else {
            hood.set(error);
        }

        SmartDashboard.putNumber("Current Tics", currentTics);
        SmartDashboard.putNumber("Pot Tics", pot.get());
    }

    // private void calculateTicks(double inches) {
    // double totalRevs = inches * REVS_PER_INCH;
    // targetTics = TICS_PER_REV * totalRevs;
    // targetTics = UtilityMethods.absMax(targetTics, LOWER_BOUND);
    // targetTics = UtilityMethods.absMin(targetTics, UPPER_BOUND);
    // }

    private double inchesToTics(double inches) {
        return inches * REVS_PER_INCH * TICS_PER_REV;

    }

    public void manualAdjustment(double y) {
        if (y > .1) {
            if (targetTics + adjustment > (LOWER_BOUND + 1)) {
                adjustment -= 1;
            }
        } else if (y < -.1) {
            if (targetTics + adjustment < (UPPER_BOUND - 1)) {
                adjustment += 1;
            }
        }
        System.out.println("targetTics" + targetTics);
        System.out.println("adjustment" + adjustment);
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
        targetTics = inchesToTics(3.9);
    }

    public void setTenFoot() {
        targetTics = inchesToTics(3.8);
    }

    public void setSafeZone() {
        targetTics = inchesToTics(2.8);
    }

    private int getCurrentTics() {
        return (int) (encoder.getTics() + startingPosition);
    }

    private double getStartingTics() {
        return pot.get();
    }

    private void resetAdjustment() {
        adjustment = 0;
    }

    private void pushPosition() {
        double currentTics = getCurrentTics();
        if (currentTics - 2043 < currentTics - 1990) {
            SmartDashboard.putString("Hood Position", String.valueOf(currentTics) + " away from Long Shot");
        } else if (currentTics - 1990 < currentTics - 1466) {
            SmartDashboard.putString("Hood Position", String.valueOf(currentTics) + " away from 10 Foot Shot");
        } else {
            SmartDashboard.putString("Hood Position", String.valueOf(currentTics) + " away from Safe Zone Shot");
        }
    }
}
