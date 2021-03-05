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
    private static final Vision vision = Vision.getInstance();
/* My code...
    private static final RobotDashboard dashboard = RobotDashboard.getInstance();
    private final double turretSpeed = .4;
    private int leftBound = 127; // not final num
    private int rightBound = -264; // was -273 not final num
    private boolean targeting = false;
    private boolean onTarget = false;
    private double adjustment = 0;
    private double targetTics = 0;
    private boolean firstTime = true;

    //singleton
  */  
    final double turretSpeed = .4;
    final int leftBound = 127; // not final num
    final int rightBound = -273; // not final num
    int deadZone = 30;
    int startPos = 0;
    boolean targeting = false;
    int targetTics = 0;
    boolean onTarget = false;

    //singletom
    private static final Turret instance = new Turret();

    private Turret() {
        turret.configFactoryDefault();
        turret.setNeutralMode(NeutralMode.Brake);
        resetTurretEncoder();
        stopTurret();
    }

    public static Turret getInstance() {
        return instance;
    }

    public void run() {
        /* My code...
        if (firstTime) {
            firstTime = false;
            double offset = dashboard.getTurretOffset();
            leftBound += offset;
            rightBound -= offset;
        }
        // double yawCorrection = UtilityMethods.map(vision.getTargetDistance(), 6, 30, dashboard.yawCorrectionShort(), dashboard.yawCorrectionLong());
        // double yaw = vision.getYawDegrees() + dashboard.yawCorrectionShort();
        double yawCorrection;
        if (vision.distance > 20) {
            yawCorrection = vision.getYawDegrees() + dashboard.yawCorrectionLong();
        } else {
            yawCorrection = vision.getYawDegrees() + dashboard.yawCorrectionShort();
        }
        double yaw = vision.getYawDegrees() + yawCorrection + adjustment;
        if (targeting) {
            if (UtilityMethods.between(yaw, -3, 3)) {
                */
        double yaw = vision.getYawDegrees();
        if (targeting) {
            vision.setTargetMode(true);
            if (UtilityMethods.between(yaw, -4, 4)) {
                turret.stopMotor();
                onTarget = true;
            } else {

              // My Code...
              //  onTarget = false;
              //  if(Math.abs(yaw) > 5) {
              //      normalAdjustment(yaw);
//-------------------------------------------------------
                // if (yaw > 0 && encoder.getTics() > -50) { //left
                // turret.set(0.2);
                // } else if (yaw< 0 && encoder.getTics() < 50){ //right
                // turret.set(-0.2);
                // } else {
                // stopTurret();
                // }
                // set turrent power can deal with the limits
                if (yaw > 0) {
                    setTurretPower(.2);
                } else if (yaw < 0) {
                    setTurretPower(-.2);
                } else {
                    slowAdjustment(yaw);
                }
            }
        } else {
            vision.setTargetMode(false);
        } 
            
        
        SmartDashboard.putNumber("turret encoder", encoder.getTics());
    }
/* my code...
    private void normalAdjustment(double yaw) {
        if (yaw > 0) {
            setTurretPower(.2);
        } else if (yaw < 0) {
            setTurretPower(-.2);
        } else {
            stopTurret();
        }
    }

    private void slowAdjustment(double yaw) {
        if (yaw > 0) {
            setTurretPower(.1);
        } else if (yaw < 0) {
            setTurretPower(-.1);
        } else {
            stopTurret();
        }
    }
*/
    public boolean onTarget() {
        return onTarget;
    }

    public void startTargeting() {
        targeting = true;
        adjustment = 0;
    }

    public void stopTargeting() {
        targeting = false;
    }

    // 4096tics = 360 degrees 11.25tics = 1 degree
    public void rotateTurret(double degrees) { // for vision
        setTargetTics((int) (encoder.getTics() + (degrees * 1.4)));
    }

    public void turnTurret(double power) { // for human control
        // SmartDashboard.putNumber("power", power);
        if (!targeting) {
            setTurretPower(power);
            // My Code...
      //  } else {
        //    if (Math.abs(power) > 0) {
        //        adjustment += UtilityMethods.copySign(power, .25);
        //    }
           
        }
        SmartDashboard.putNumber("turretAdjustment", adjustment);
    }

    public void setTurretPower(double power) {
        if (power < 0 && encoder.getTics() < leftBound) {
            turret.set(UtilityMethods.absMin(power, turretSpeed));
        } else if (power > 0 && encoder.getTics() > rightBound) {
            turret.set(UtilityMethods.absMin(power, turretSpeed));
        } else {
            stopTurret();
        }
    }

    public void stopTurret() {
        turret.stopMotor();
    }

    public void resetTurretEncoder() {
        encoder.reset();
    }

    private void setTargetTics(int tics) {
        if (tics < 0) {
            targetTics = Math.min(tics, leftBound);
        } else {
            targetTics = Math.min(tics, rightBound);
        }
    }

   // public int getTurretPosition() {
      //  return encoder.getTics();
  //  }

}