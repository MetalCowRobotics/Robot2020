package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.lib14.XboxControllerMetalCow;
import frc.robot.RobotMap;

public class Magazine {
    private static MCR_SRX motor1 = new MCR_SRX(RobotMap.Magazine.LEFT_MAGAZINE_MOTOR);
    private static MCR_SRX motor2 = new MCR_SRX(RobotMap.Magazine.RIGHT_MAGAZINE_MOTOR);
    private static final DigitalInput topLimit = new DigitalInput(RobotMap.Magazine.LIMIT_SWITCH_TOP);
	private static final DigitalInput bottomLimit = new DigitalInput(RobotMap.Magazine.LIMIT_SWITCH_BOTTOM);
    boolean feedMode = false;
    XboxControllerMetalCow controller = new XboxControllerMetalCow(0);
    private static MCR_SRX magazine = new MCR_SRX(RobotMap.Magazine.MAGAZINE_MOTOR);
    private static final Magazine instance = new Magazine();
    private static DigitalInput isThereABallTop = new DigitalInput(RobotMap.Magazine.IS_THERE_A_BALL_TOP);
    private static DigitalInput isThereABallBottom = new DigitalInput(RobotMap.Magazine.IS_THERE_A_BALL_BOTTOM);
    double magazineSpeed = .5;
    private Magazine() {


    }

    public static Magazine getInstance() {
        return instance;
    }

    public void runMagazine() {
        if (ballAtBottom() && !ballAtTop()){
            magazine.set(magazineSpeed);
        }else{
            if (!feedMode){
                stopMagazine();
            }
        }
    }

    public void stopMagazine() {
        magazine.stopMotor();
        feedMode = false;
    }

    public void checkIfLoaded() {
            SmartDashboard.putBoolean("Ball at top", ballAtTop());
            SmartDashboard.putBoolean("Ball at bottom", ballAtBottom());
         }

    public void feedOneBall() {
        if(ballAtTop()){
            SmartDashboard.putBoolean("feeding", true);
            feedMode = true;
            magazine.set(magazineSpeed);
        }else if (!ballAtTop()){
            SmartDashboard.putBoolean("feeding", false);
            feedMode = false;
            stopMagazine();
        }
    }
    private boolean ballAtBottom(){
        return !isThereABallBottom.get();
    }
    private boolean ballAtTop(){
        return !isThereABallTop.get();
    }
}