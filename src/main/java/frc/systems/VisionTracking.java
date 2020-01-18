package frc.systems;

public class VisionTracking {
    
    private static final VisionTracking instance = new VisionTracking();

    private VisionTracking() {

    }

    public static VisionTracking getInstance() {
        return instance;
    }
}
