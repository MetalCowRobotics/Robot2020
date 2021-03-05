package frc.systems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.lib14.MCR_SPX;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Funnel {
    private static MCR_SPX agitator = new MCR_SPX(RobotMap.Funnel.Agitator_Motor);
    private static MCR_SRX magIntake = new MCR_SRX(RobotMap.Funnel.Magazine_Funnel_Motor);
    private static final Funnel instance = new Funnel();

    private Funnel() {
        magIntake.configFactoryDefault();
        magIntake.setNeutralMode(NeutralMode.Coast);
    }

    public static Funnel getInstance() {
        return instance;
    }

    public void run(boolean bottomLimit) {
        if (bottomLimit) {
            runMotors();
        } else {
            stopMotors();
        }
    }

    private void runMotors() {
        agitator.set(RobotMap.Funnel.motorASpeed);
        magIntake.set(RobotMap.Funnel.motorSpeed);
    }

    private void stopMotors() {
        agitator.stopMotor();
        magIntake.stopMotor();
    }
}
