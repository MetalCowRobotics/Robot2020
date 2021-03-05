package frc.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.VictorSP;

public class Magazine {
    private static VictorSP magazineMotor = new VictorSP(RobotMap.Magazine.MAGAZINE_MOTOR);
    private static DigitalInput isBallAtTop = new DigitalInput(RobotMap.Magazine.IS_THERE_A_BALL_TOP);
    private static DigitalInput isBallAtBottom = new DigitalInput(RobotMap.Magazine.IS_THERE_A_BALL_BOTTOM);
    private double magazineSpeed = 1;
    private boolean feedMode = false;
    private boolean loadToTop = false;
    private int counted = 0;

    //singleton instance
    private static final Magazine instance = new Magazine();

    private Magazine() {
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
            if (!isThereABallTopForShooter()) {
                feedMode=false;
                loadToTop=true;
            }
        } else if (loadToTop) { //need to get out of load mode after shooting all balls
            advanceBallToTop();
        } else {
            maintainMagazine();
        }
    }

    public boolean isThereABallTopForShooter() {
        return isBallAtTop.get();
    }

    public boolean isThereABallBottomToLoad() {
        return isBallAtBottom.get();
    }

    private void advanceBallToTop() {
        if (!ballAtTop()) {
            magazineMotor.set(magazineSpeed);
        } else {
            magazineMotor.stopMotor();
            // loadToTop = false; // how do i get out of load mode?
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
            magazineMotor.set(magazineSpeed);
    }

    private void runMagazine() {
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
        //TODO i do not think this should be here
        feedMode = false;
    }

    public void checkIfLoaded() {
        SmartDashboard.putBoolean("Ball at top", ballAtTop());
        SmartDashboard.putBoolean("Ball at bottom", ballAtBottom());
    }

    public void feedOneBall() {
        feedMode = true;
    }

    private boolean ballAtBottom() {
        return !isBallAtBottom.get();
    }

    private boolean ballAtTop() {
        return !isBallAtTop.get();
    }

    public boolean isReady() {
        if (ballAtTop()) {
            return true;
        }
        //TODO i do not think this should be here
        runMagazine();
        return false;
    }

    public void loadBallInShootingPosition() {
        loadToTop = true;
    }

    public int getCounted() {
        return counted;
    }

	public void stopLoadToTop() {
        loadToTop = false;
        feedMode = false;
	}

}