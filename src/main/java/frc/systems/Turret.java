package frc.systems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.lib14.UtilityMethods;
import frc.robot.RobotMap;

public class Turret {
    private static MCR_SRX turret = new MCR_SRX(RobotMap.Turret.TURRET_MOTOR);
    private static final Turret instance = new Turret();
    double turretSpeed = .4;
    int deadZone = 30;
    int startPos = 0;
    

    private Turret() {
        
    }

    public static Turret getInstance() {
        return instance;
    }

    // 4096tics = 360 degrees 11.25tics = 1 degree
    public void rotateTurret(int degrees) {
        int targetTics = 0;
        targetTics = degrees * 11;
        double totalTicsDiff = (targetTics - turret.getSelectedSensorPosition()) * .003;
        double error = UtilityMethods.copySign(totalTicsDiff, UtilityMethods.absMax(totalTicsDiff, .05));
        error = UtilityMethods.copySign(totalTicsDiff, UtilityMethods.absMin(error, .4));
        System.out.println("Target" + targetTics + "; current " + turret.getSelectedSensorPosition() + "; error" + error
                + ": TotalTics" + totalTicsDiff);
        if (Math.abs(targetTics - turret.getSelectedSensorPosition()) < deadZone) {
            turret.stopMotor();
        } else {
            turret.set(error);
        }
        // if(turret.getSelectedSensorPosition() < targetTics - deadZone){
        // turret.set(error);
        // System.out.println("Turret Increasing" + targetTics + "; " +
        // turret.getSelectedSensorPosition());
        // }else if(turret.getSelectedSensorPosition() > targetTics + deadZone){
        // turret.set(error);
        // System.out.println("Turret decreasing" + turret.getSelectedSensorPosition());
        // }else{
        // turret.stopMotor();
        // System.out.println("Stoping turret" + turret.getSelectedSensorPosition());
        // }

        SmartDashboard.putNumber("Encoder Tics", turret.getSelectedSensorPosition());
    }

    public void resetTurretEncoder() {
        turret.setSelectedSensorPosition(0);
        // turret.getSensorCollection().setQuadraturePosition(0, 10);
        startPos = turret.getSelectedSensorPosition();
    }

}