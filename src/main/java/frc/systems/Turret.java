package frc.systems;

public class Turret {

    private static final Turret instance = new Turret();

    private Turret() {

    }

    public static Turret getInstance() {
        return instance;
    }

    public void rotateTurret(double degrees) {
        
    }
}
