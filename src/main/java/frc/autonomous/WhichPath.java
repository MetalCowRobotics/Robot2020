/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import frc.commands.DriveInches;
import frc.commands.IntakeDeployRun;
import frc.commands.TurnDegrees;
import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.systems.Intake;
import frc.lib14.TimedCommandSet;

/**
 * Add your docs here.
 */
public class WhichPath implements MCRCommand {
    boolean firstTime = true;
    Intake intake = Intake.getInstance();
    double redDistance = 100;
    boolean runBlueMision = true;
    MCRCommand preMission = new SequentialCommands(new IntakeDeployRun());
    MCRCommand mission1 = new SequentialCommands(new DriveInches(1, redDistance));
    MCRCommand redMission = new SequentialCommands(turn(-90, 1.5), new DriveInches(1, 82), turn(70, 1.25), new DriveInches(1, 175)););
    MCRCommand blueMission = new SequentialCommands(new DriveInches(1, 52), turn(-105, 2), new DriveInches(1, 63), turn((100), 2), new DriveInches(1, 103));
    MCRCommand finalMission = blueMission;

    public void run() {
        if (firstTime) {
            firstTime = false;
        }
        if (!preMission.isFinished()) {
            preMission.run();
        } else {
            if (!mission1.isFinished()) {
                mission1.run();
                if (intake.current() < 3) {
                    runBlueMision = false;
                    finalMission = redMission;
                }
            } else {
                if (!finalMission.isFinished()){
                    finalMission.run();
                }
                // if (runBlueMision) {
                //     if (!blueMission.isFinished())
                //         blueMission.run();
                // } else {
                //     if (!redMission.isFinished())
                //         redMission.run();
                // }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return finalMission.isFinished();
        // return (redMission.isFinished() | blueMission.isFinished());
    }

    private MCRCommand turn(double degrees, double timeout) {
        return new TimedCommandSet(new TurnDegrees(degrees), timeout);
    }

}
