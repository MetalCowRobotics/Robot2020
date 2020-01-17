package frc.systems;

public class Shooter {
    
    private static final Shooter instance = new Shooter();

    private Shooter() {

    }

    public static Shooter getInstance() {
        return instance;
    }
}
