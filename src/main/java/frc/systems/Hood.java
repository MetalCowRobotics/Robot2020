package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.VictorSP;
import frc.lib14.FC_JE_0149Encoder;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Hood {
    private static MCR_SRX hood = new MCR_SRX(RobotMap.Hood.HOOD_MOTOR);
    //public static VictorSP hood = new VictorSP(RobotMap.Hood.HOOD_MOTOR);
    public static FC_JE_0149Encoder encoder = new FC_JE_0149Encoder(3,2);
    private static final Hood instance = new Hood();

    private final double TICKS_PER_REV = 44.4;
    private double target_inches = 1;
    private double TOTAL_REVS = target_inches * 10;
    private double TARGET_TICKS = TICKS_PER_REV * TOTAL_REVS;
    private double adjustment = 0;

    private Hood() {
        hood.configFactoryDefault();
		hood.setNeutralMode(NeutralMode.Coast);
    }

    public static Hood getInstance() {
        return instance;
    }

    public void calculateTicks() {
        TOTAL_REVS = target_inches * 10;
        TARGET_TICKS = TICKS_PER_REV * TOTAL_REVS + adjustment;
    }

    public void run(double distance) {
        setDistance(distance);
        System.out.println("HoodTics:"+encoder.getTics()+"  adjustment: "+adjustment );
    }

    public void calculateAdjustment(double y) {
        if (y > .1) {
            adjustment += 1;
        } else if (y < -.1){
            adjustment -= 1;
        }
    }

    public void resetAdjustment() {
        adjustment = 0;
    }

    //automated set hood position
    public void setDistance(double distance) {
        if (distance > 25) {
            calculateTicks();
            setFarShot();
        } else if (distance > 5) {
            calculateTicks();
            setTenFoot();
        } else if (distance > 1) {
            calculateTicks();
            setSafeZone();
        } else {
            hood.stopMotor();
        }
    }

    //manual set hood position
    public void setDistance(boolean farShot) {
        if (farShot) {
            setFarShot();
        } else {
            setSafeZone();
        }
    }


    public void setFarShot() {
        target_inches = 1.4;
        System.out.println("EncoderTicks:" + encoder.getTics());
        double error = ((TARGET_TICKS+3) - encoder.getTics()) / 100;
        if (error > .5) {
            error = .5;
        }
        if (error < -.5) {
            error = -.5;
        }
        hood.set(error);
    }

    public void setTenFoot() {
        target_inches = 1.8;
        System.out.println("EncoderTicks:" + encoder.getTics());
        double error = ((TARGET_TICKS+3) - encoder.getTics()) / 100;
        if (error > .5) {
            error = .5;
        }
        if (error < -.5) {
            error = -.5;
        }
        hood.set(error);
    }

    public void setSafeZone() {
        target_inches = 3.4;
        System.out.println("EncoderTicks:" + encoder.getTics());
        double error = ((TARGET_TICKS+3) - encoder.getTics()) / 100;
        if (error > .5) {
            error = .5;
        }
        if (error < -.5) {
            error = -.5;
        }
        hood.set(error);
    }

    public void resetEncoder() {
        encoder.reset();
    }

}
