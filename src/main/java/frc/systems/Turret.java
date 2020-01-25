package frc.systems;

import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Turret {
    private static MCR_SRX turret = new MCR_SRX(RobotMap.Turret.TURRET_MOTOR);
    private static final Turret instance = new Turret();
    double turretSpeed = .2;
    int deadZone = 20;

    private Turret() {

    }

    public static Turret getInstance() {
        return instance;
    }
    //4050tics = 360 degrees      11.25tics = 1 degree
    public void rotateTurret(int degrees) {
        int tics = 0; 
        tics = degrees * 11;
        if (tics >= turret.getSelectedSensorPosition() + deadZone){
            turret.set(turretSpeed);
        }
        if (tics <= turret.getSelectedSensorPosition() - deadZone){
            turret.set(-turretSpeed);
        }
        if (tics == turret.getSelectedSensorPosition()){
            turret.stopMotor();
        }
        // turret.set();
        turret.setSelectedSensorPosition(tics);
        
    }
    public void resetTurretEncoder (){

    }
}
