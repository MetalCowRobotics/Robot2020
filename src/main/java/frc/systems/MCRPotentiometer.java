/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.systems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

/**
 * Add your docs here.
 */
public class MCRPotentiometer {

    // Initializes an AnalogPotentiometer on analog port 0
    // The full range of motion (in meaningful external units) is 0-180 (this could
    // be degrees, for instance)
    // The "starting point" of the motion, i.e. where the mechanism is located when
    // the potentiometer reads 0v, is 30.

    AnalogPotentiometer pot = new AnalogPotentiometer(0, 4.85, 4.8);
    // AnalogInput pot = new AnalogInput(0);

    public double getPosistion() {
        return pot.get();
        // return pot.getVoltage();
        

    }
}
