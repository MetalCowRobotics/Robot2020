package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Magazine {
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
        if (isThereABallBottom.get() && !isThereABallTop.get()){
            magazine.set(magazineSpeed);
        }else{
            stopMagazine();
        }


    }

    public void stopMagazine() {
        magazine.stopMotor();
    }

    public void checkIfLoaded() {
        if(isThereABallTop.get()){
            return; //What do you want this to do?
        }
        if(!isThereABallTop.get()){
            return;// What do you want this to do?
        }

    }
}