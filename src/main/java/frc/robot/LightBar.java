package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LightBar{

    AddressableLED addressableLED;

    /**
     * Build and start a new LED LightBar using WS2812 LEDs plugged into the RIO PWM
     * Port.
     *
     * @param portNum the PWM Port Number the WS2812 LED strip is connected to
     * @param length  the lenght of the LED strip as a count of LEDs
     * @see https://docs.wpilib.org/en/latest/docs/software/actuators/addressable-leds.html
     * @see https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/AddressableLED.html
     * @see https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/AddressableLEDBuffer.html
     */
    public LightBar(int portNum, int length) {

        // addressableLED = new AddressableLED(portNum);

        // AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(length); // Length is expensive to set, so only set it once, then just update data
        // addressableLED.setLength(m_ledBuffer.length()); // Length is expensive to set, so only set it once, then just update data

        // // Set the data
        // addressableLED.setData(this);
        // addressableLED.start();
    }

}
