/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.commands;

import frc.lib14.MCRCommand;
import frc.systems.Intake;


/**
 * Add your docs here.
 */
public class IntakeStow implements MCRCommand {
    private Intake intake = Intake.getInstance();
    private boolean done = false;

    public IntakeStow() {
    }

    @Override
    public void run() {
        intake.stopIntake();;
        if (intake.intakeStowed()) {
            done = true;
        } else {
            intake.retractIntake();;
        }
    }

    @Override
    public boolean isFinished() {
        return done;
    }
}
