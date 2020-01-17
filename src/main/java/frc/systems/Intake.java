package frc.systems;

public class Intake {

    private static final Intake instance = new Intake();

    private Intake() {

    }

    public static Intake getInstance() {
        return instance;
    }
}