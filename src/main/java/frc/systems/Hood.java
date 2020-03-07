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
    private static MCR_SRX hood = new MCR_SRX(RobotMap.Hood.HOOD_MOTOR);
    private static AnalogPotentiometer pot = new AnalogPotentiometer(0, 10000, -2378);
    private static FC_JE_0149Encoder encoder = new FC_JE_0149Encoder(3, 2);
    private static RobotDashboard dashboard = RobotDashboard.getInstance();

    private static final double REVS_PER_INCH = 12.7;//11.8;
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
        startingPosition = (pot.get() * 1.64);//1.95
        targetTics = startingPosition;
    }

    public static Hood getInstance() {
        return instance;
    }

    public void run() {
        pushPosition();
        //TODO what is pushHoodPosition trying to do
        dashboard.pushHoodPositionText(1); 
        double currentTics = getCurrentTics();
        double target = targetTics + adjustment + dashboard.hoodCorrection();
        double error = (target - currentTics) / 100;
        error = UtilityMethods.absMin(error, .5);
        if (Math.abs(target - currentTics) < 5) {
            hood.stopMotor();
        } else {
            //TODO check upper and lower bounds
            hood.set(UtilityMethods.copySign(error, .4));
        }

        SmartDashboard.putNumber("Current Tics", encoder.getTics());
        SmartDashboard.putNumber("Encoder Tics with Starting Position", encoder.getTics() + startingPosition);
        SmartDashboard.putNumber("PotTics", pot.get());
        SmartDashboard.putNumber("Target Tics", targetTics);
    }

    private double inchesToTics(double inches) {
        return (inches - 1) * REVS_PER_INCH * TICS_PER_REV;

    }

    public void manualAdjustment(double y) {
        if (y > .1) {
            if (targetTics + adjustment + dashboard.hoodCorrection() -1 > (LOWER_BOUND)) {
                adjustment -= 2;
            }
        } else if (y < -.1) {
            if (targetTics + adjustment + dashboard.hoodCorrection() + 1 < (UPPER_BOUND)) {
                adjustment += 2;
            }
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
        targetTics = inchesToTics(4);
    }

    public void setTenFoot() {
        targetTics = inchesToTics(3.5);
    }

    public void setSafeZone() {
        targetTics = inchesToTics(1);
    }

    private int getCurrentTics() {
        return (int) (encoder.getTics() + startingPosition);
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
