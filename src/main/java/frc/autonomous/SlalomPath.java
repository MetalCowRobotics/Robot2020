/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.autonomous;

import frc.commands.CurvatureDrive;
import frc.commands.TurnDegrees;
import frc.commands.DriveInches;
import frc.lib14.SequentialCommands;
import frc.lib14.MCRCommand;

/**
 * Add your docs here.
 */
public class SlalomPath implements MCRCommand{

    MCRCommand mission;

    public SlalomPath() {
        MCRCommand step1 = new SequentialCommands( new DriveInches(1, 4), new CurvatureDrive("LEFT", 60, 42, .9), new CurvatureDrive("RIGHT", 60, 48, .9), new DriveInches(1, 85));
        MCRCommand step2 = new SequentialCommands(new CurvatureDrive("RIGHT", 80, 28, .9), new CurvatureDrive("LEFT", 160, 40, .9),new DriveInches(1,12), new CurvatureDrive("LEFT", 155, 40, .9), new CurvatureDrive("RIGHT", 60, 36, .9));
        MCRCommand step3 = new SequentialCommands(new DriveInches(1, (6*12)), new CurvatureDrive("RIGHT", 60, 42, .9), new DriveInches(1, 12), new CurvatureDrive("LEFT", 50, 48, .9));
         mission = new SequentialCommands(step1, step2, step3);
    }

    public void run() { 

        mission.run();
    }
    public boolean isFinished() {
        return false;
    }
}
