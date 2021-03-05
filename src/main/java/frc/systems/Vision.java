package frc.systems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {
    double focalWidth = 778.8679;
    double focalHeight = 827.9805;
    double targetWidth = 3.3125;
    double targetHeight = 1.4167;

    double distanceWidth = 0;
    double distanceHeight = 0;
    double distance = 0;
    double pythagHeight = 2.5;
    double distanceSquared = 0;
    double heightSquared = pythagHeight * pythagHeight;
    double horizontal = 0;
    double targetYaw = 0;
    double targetTolerance = 3;
    double turnAdjustment = 60;
    double turnDelay = 180;

    double cameraFOV = 60;
    double maxYaw = 25;
    double yawDegrees = 0;
    boolean targetMode = false;

    private static final Vision instance = new Vision();
    
    // MCRCommand turnCW = new SequentialCommands(new TimedCommandSet(new
    // TurnDegrees(turnAdjustment), 2),new CommandPause(.2));
    // MCRCommand turnCCW = new SequentialCommands(new TimedCommandSet(new
    // TurnDegrees(-turnAdjustment), 2),new CommandPause(.2));
    //TurnDegrees turnCW = new TurnDegrees(70);
    //TurnDegrees turnCCW = new TurnDegrees(-70);

    public NetworkTableEntry yaw;
    public NetworkTableEntry isDriverMode;
    public NetworkTableEntry pitch;
    public NetworkTableEntry width;
    public NetworkTableEntry height;

    public static Vision getInstance() {
        return instance;
    }

    public void visionInit() {
        NetworkTableInstance table = NetworkTableInstance.getDefault();
        NetworkTable myCam = table.getTable("chameleon-vision").getSubTable("Mindstone");
        yaw = myCam.getEntry("targetYaw");
        pitch = myCam.getEntry("targetPitch");
        isDriverMode = myCam.getEntry("driverMode");
        width = myCam.getEntry("targetBoundingWidth");
        height = myCam.getEntry("targetBoundingHeight");

       /* 
        width = myCam.getEntry("targetFittedWidth");
        height = myCam.getEntry("targetFittedHeight");
        setTargetMode(false);
        */
    }

    // Periodic function
    public double getTargetDistance() {
        //distanceWidth = focalWidth * targetWidth / width.getDouble(0.0);
        distanceHeight = focalHeight * targetHeight / height.getDouble(0.0);
        //distance = (distanceHeight + distanceWidth) / 2;
        distance = distanceHeight;
        distanceSquared = distance * distance;
        horizontal = Math.sqrt(distanceSquared - heightSquared);
        return horizontal;
    }

    public double getYawDegrees() {
        yawDegrees = cameraFOV * 0.5 / maxYaw * yaw.getDouble(0.0) + width.getDouble(0.0)/640*50;
        return yawDegrees; 

        /*
        yawDegrees = cameraFOV * 0.5 / maxYaw * yaw.getDouble(0.0);
        return yawDegrees;
        */
    }

    public void setTargetMode(boolean cameraMode) {
        isDriverMode.setBoolean(!cameraMode);
        System.out.println("   cameraMode:" + cameraMode);
    }

    public boolean getTargetMode() {
        isDriverMode.getDouble(0.0);
        targetMode = isDriverMode.getDouble(0.0) != 1.0;
        return targetMode;
    }


}
