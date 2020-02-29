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
    private static final Turret instance = new Turret();
    double turretSpeed = .4;
    int deadZone = 30;
    int startPos = 0;
    

    private Turret() {
        turret.configFactoryDefault();
		turret.setNeutralMode(NeutralMode.Brake);
    }

    public static Turret getInstance() {
        return instance;
    }

    // 4096tics = 360 degrees 11.25tics = 1 degree
    public void rotateTurret(double degrees) {
        double targetTics = 0;
        targetTics = degrees * 11;
        double totalTicsDiff = (targetTics - encoder.getTics()) * .003;
        //double error = UtilityMethods.copySign(totalTicsDiff, UtilityMethods.absMax(totalTicsDiff, .05));
        //error = UtilityMethods.copySign(totalTicsDiff, UtilityMethods.absMin(error, .4));
        //System.out.println("Target" + targetTics + "; current " + encoder.getTics() + "; error" + error
                //+ ": TotalTics" + totalTicsDiff);
        if (Math.abs(targetTics - encoder.getTics()) < deadZone) {
            turret.stopMotor();
        } else {
            if (totalTicsDiff > .4) {
                totalTicsDiff = .4;
            }
            if (totalTicsDiff < -.4) {
                totalTicsDiff = -.4;
            }
            //turret.set(totalTicsDiff);
        }
        SmartDashboard.putNumber("Encoder Tics", encoder.getTics());
    }

    public void resetTurretEncoder() {
        encoder.reset();
        startPos = encoder.getTics();
    }

}