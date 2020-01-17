package frc.systems;

public class Hood {

    private static final Hood instance = new Hood();

    private Hood() {

    }

    public static Hood getInstance() {
        return instance;
    }

    public void moveHood(double degrees) {

    }
}
