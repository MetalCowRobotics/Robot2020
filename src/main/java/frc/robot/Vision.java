package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;

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

    public Joystick joystick;
    public NetworkTableEntry yaw;
    public NetworkTableEntry isDriverMode;
    public NetworkTableEntry pitch;    
    public NetworkTableEntry width;
    public NetworkTableEntry height;
    public void robotInit() { 
          //  joystick = new Joystick(1);
            NetworkTableInstance table = NetworkTableInstance.getDefault();
            NetworkTable myCam = table.getTable("chameleon-vision").getSubTable("Mindstone");
            yaw = myCam.getEntry("targetYaw");
            pitch = myCam.getEntry("targetPitch");
            isDriverMode = myCam.getEntry("driver_mode");
            width = myCam.getEntry("targetFittedWidth");
            height = myCam.getEntry("targetFittedHeight");
            
    }
    //Periodic function
    public void teleopPeriodic() {
        distanceWidth = focalWidth * targetWidth / width.getDouble(0.0);
        distanceHeight = focalHeight * targetHeight / height.getDouble(0.0);
        distance = (distanceHeight + distanceWidth) / 2;
        distanceSquared = distance * distance;
        horizontal = Math.sqrt(distanceSquared - heightSquared);
        //System.out.println("hello world");
        //System.out.println("Yaw: " + yaw.getDouble(0.0) + "  Pitch: " + pitch.getDouble(0.0)+ "  targetFittedWidth " + width.getDouble(0.0));//trys to print yaw, if it doesnt exist it will print 0
        System.out.println("Distance: " + distance + "  distanceHeight: " + distanceHeight + "distanceWidth" + distanceWidth);
  /*if (yaw.getDouble(0.0) > 1) { 

    }
  if (yaw.getDouble(0.0) < -1) { 
      
    } */   
    }
}
