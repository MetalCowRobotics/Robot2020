package frc.systems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Vision {
    double focalWidth = 773.7660;
    double focalHeight = 741.1590;
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
        width = myCam.getEntry("targetFittedWidth");
        height = myCam.getEntry("targetFittedHeight");

    }

    // Periodic function
    public double getTargetDistance() {
        distanceWidth = focalWidth * targetWidth / width.getDouble(0.0);
        distanceHeight = focalHeight * targetHeight / height.getDouble(0.0);
        distance = (distanceHeight + distanceWidth) / 2;
        distanceSquared = distance * distance;
        horizontal = Math.sqrt(distanceSquared - heightSquared);
        return horizontal;
    }

    public double getYawDegrees() {
        yawDegrees = cameraFOV * 0.5 / maxYaw * yaw.getDouble(0.0);
        return yawDegrees;
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

/* 
    public void teleopPeriodic() {
        
        //System.out.println("hello world");
        //System.out.println("Yaw: " + yaw.getDouble(0.0) + "  Pitch: " + pitch.getDouble(0.0)+ "  targetFittedWidth " + width.getDouble(0.0));//trys to print yaw, if it doesnt exist it will print 0
        System.out.println("Distance: " + distance + "  distanceHeight: " + distanceHeight + "   distanceWidth" + distanceWidth + "   yaw" + yaw.getDouble(0.0));
        targetYaw = yaw.getDouble(0.0);
       if (targetYaw > targetTolerance) { 
           //turn clockwise
           targetYaw = yaw.getDouble(0.0);
           turnCW.updateDegrees(targetYaw);
           turnCW.run();
           System.out.println("New Target Yaw: " + targetYaw);
           turnDelay=1;
          }
         if (targetYaw < -targetTolerance) { 
          //turn counter-clockwise
          targetYaw = yaw.getDouble(0.0);
          turnCCW.updateDegrees(targetYaw);
          turnCCW.run();
          System.out.println("New Target Yaw: " + targetYaw);
          turnDelay=1;
          }   
          turnDelay--; 
    }*/
}
