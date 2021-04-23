package frc.autonomous;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.commands.DriveInches;
import frc.commands.NewTurn;
import frc.commands.TurnDegrees;
import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.lib14.TimedCommandSet;
import frc.lib14.UtilityMethods;

public class PathPlotterTest implements MCRCommand {
    double PIXELS_PER_INCH;
    ArrayList<Point> path = new ArrayList<>();
    ArrayList<MCRCommand> commands = new ArrayList<>();

    MCRCommand mission;

    public PathPlotterTest () {


        // load file
        try {
            // File filePath = new File("home/lvuser/deploy/gather_all_cells");
            File filePath = new File("home/lvuser/deploy/BoxPath.txt"); //open file
            Scanner scanner = new Scanner(filePath);

            String data = scanner.nextLine();

            String[] parsedData = data.split(",");
            String x = "", y = "";

            for (int i=0;i<parsedData.length;i++) {
                if (i == 0) {
                    if (SmartDashboard.getNumber("PP Scale Factor", 0) == 0) {
                        PIXELS_PER_INCH = Double.parseDouble(parsedData[i]);
                    } else {
                        PIXELS_PER_INCH = SmartDashboard.getNumber("PP Scale Factor", 0);
                    }
                } else if (x == "") { //if on x pos item, then we have an incomplete set of coords => continue
                    x = parsedData[i];
                } else { //if on y pos item, then we have a complete set of coords => add set to path & reset
                    y = parsedData[i];

                    path.add(new Point((int) Double.parseDouble(x), (int) Double.parseDouble(y)));
                    x = "";
                    y = "";
                } 
            }
            
            scanner.close();
            System.out.println(path);
            System.out.println("successfully loaded");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem loading file");
        }

        double currentAngle = 0;
        //iterate through pathmarkers
        for (int i=0; i<path.size()-1; i++) {
            //calculate distance and angle
            double distance = Math.sqrt(Math.pow((path.get(i+1).y - path.get(i).y), 2) + Math.pow((path.get(i+1).x - path.get(i).x), 2)) / PIXELS_PER_INCH; //distance to next target
            double atan2Result = Math.atan2(path.get(i+1).y-path.get(i).y, path.get(i+1).x-path.get(i).x) * 180 / Math.PI;
            double angle;

            angle = Math.floorMod( (long) (atan2Result - currentAngle + 540), 360) - 180;

            currentAngle = atan2Result;
            System.out.println("distance: " + distance + "  angle: " + angle);

            //add commands
            commands.add(new TimedCommandSet(new TurnDegrees(angle), 5));
            commands.add(new DriveInches(1, distance));
        }

        mission = new SequentialCommands(commands);
    }

    @Override
    public void run() {
        mission.run();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}