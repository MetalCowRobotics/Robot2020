package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib14.MCR_SRX;
import frc.robot.RobotMap;

public class Magazine {
    boolean feedMode = false;
    private static final DigitalInput topLimit = new DigitalInput(RobotMap.Magazine.LIMIT_SWITCH_TOP);
    private static MCR_SRX magazineMotor = new MCR_SRX(RobotMap.Magazine.MAGAZINE_MOTOR);
    private static final Magazine instance = new Magazine();
    private static DigitalInput isThereABallTop = new DigitalInput(RobotMap.Magazine.IS_THERE_A_BALL_TOP);
    private static DigitalInput isThereABallBottom = new DigitalInput(RobotMap.Magazine.IS_THERE_A_BALL_BOTTOM);
    double magazineSpeed = .5;
    boolean loadToTop = false;
    int counted = 0;

    private Magazine() {

    }

    public boolean isThereABallTopForShooter() {
        return isThereABallTop.get();
    }

    public boolean isThereABallBottomForShooter() {
        return isThereABallBottom.get();
    }

    public static Magazine getInstance() {
        return instance;
    }

    // feedBallToShooter
    // prepareToShoot = move balls to top
    // maintain magazine
    public void run() {
        if (feedMode) {
            feedBallToShooter();
        } else if (loadToTop) { //need to get out of load mode after shooting all balls
            advanceBallToTop();
        } else {
            maintainMagazine();
        }
    }

    private void advanceBallToTop() {
        if (!ballAtTop()) {
            magazineMotor.set(magazineSpeed);
        } else {
            magazineMotor.stopMotor();
            loadToTop = false; // how do i get out of load mode?
        }
    }

    private void maintainMagazine() {
        if (ballAtBottom() && !ballAtTop()) {
            magazineMotor.set(magazineSpeed);
        } else {
            stopMagazine();
        }
    }

    private void feedBallToShooter() {
        if (ballAtTop()) {
            SmartDashboard.putBoolean("feeding", true);
            magazineMotor.set(magazineSpeed);
        } else if (!ballAtTop()) {
            SmartDashboard.putBoolean("feeding", false);
            feedMode = false;
            counted++;
            stopMagazine();
            loadBallInShootingPosition();
        }
    }

    public void runMagazine() {
        if (ballAtBottom() && !ballAtTop()) {
            magazineMotor.set(magazineSpeed);
        } else if (loadToTop) {
            loadBallInShootingPosition();
        } else if (!feedMode) {
            stopMagazine();
        }
    }

    public void stopMagazine() {
        magazineMotor.stopMotor();
        feedMode = false;
    }

    public void checkIfLoaded() {
        SmartDashboard.putBoolean("Ball at top", ballAtTop());
        SmartDashboard.putBoolean("Ball at bottom", ballAtBottom());
    }

    public void feedOneBall() {
        feedMode = true;
        // if (ballAtTop()) {
        // SmartDashboard.putBoolean("feeding", true);
        // feedMode = true;
        // magazineMotor.set(magazineSpeed);
        // } else if (!ballAtTop()) {
        // SmartDashboard.putBoolean("feeding", false);
        // feedMode = false;
        // counted++;
        // stopMagazine();
    }

    private boolean ballAtBottom() {
        return !isThereABallBottom.get();
    }

    private boolean ballAtTop() {
        return !isThereABallTop.get();
    }

    public boolean isReady() {
        if (isThereABallTop.get()) {
            return true;
        }
        runMagazine();
        return false;
    }

    public void loadBallInShootingPosition() {
        loadToTop = true;
        // if (!ballAtTop()) {
        // magazineMotor.set(magazineSpeed);
        // loadToTop = true;
        // } else {
        // magazineMotor.stopMotor();
        // loadToTop = false;
        // }
    }

    public int getCounted() {
        return counted;
    }

	public void stopLoadToTop() {
        loadToTop = false;
	}

}