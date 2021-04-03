/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import frc.commands.DriveInches;
import frc.commands.IntakeDeployRun;
import frc.lib14.MCRCommand;
import frc.lib14.SequentialCommands;
import frc.systems.Intake;

/**
 * Add your docs here.
 */
public class WhichPath implements MCRCommand {
    boolean firstTime = true;
    Intake intake = Intake.getInstance();
    double redDistance = 115;
    boolean runBlueMision = true;
    MCRCommand previousMission = new SequentialCommands(new IntakeDeployRun());
    MCRCommand mission1 = new SequentialCommands(new DriveInches(1, redDistance));
    MCRCommand redMission = new SequentialCommands(/* red turn and remaining mision */);
    MCRCommand blueMission = new SequentialCommands(/* subtract red from blue and remaining mission */);
    MCRCommand finalMission = blueMission;

    public void run() {
        if (firstTime) {
            firstTime = false;
        }
        if (!previousMission.isFinished()) {
            previousMission.run();
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
                if (runBlueMision) {
                    if (!blueMission.isFinished())
                        blueMission.run();
                } else {
                    if (!redMission.isFinished())
                        redMission.run();
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return finalMission.isFinished();
        // return (redMission.isFinished() | blueMission.isFinished());
    }

}
