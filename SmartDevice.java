import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 SmartDevice is a class that holds the basic properties of a smart device.
 */
public class SmartDevice {
    private LocalDateTime time;     // recent information of the device
    private String name;            // device name
    private boolean isOn;           // the status of the device (whether it is on or off)
    private int brightness;         // the brightness level of the device
    private int kelvin;             // the color temperature (in Kelvin) of the device
    private String colorCode;       // the color code of the device
    private double ampere;         // the current value of the device (in amperes or amps)

    /**
     Constructor that sets the name of the SmartDevice class.
     @param name The name of the device.
     */
    public SmartDevice(String name) {
        this.name = name;
    }

    /**
     Constructor that sets the name and on/off status of the SmartDevice class.
     @param name The name of the device.
     @param isOn The on/off status of the device.
     */
    public SmartDevice(String name, boolean isOn) {
        this.name = name;
        this.isOn = isOn;
    }

    /**
     Constructor that sets the name, on/off status, brightness level, and color temperature (in Kelvin) of the SmartDevice class.
     @param name The name of the device.
     @param isOn The on/off status of the device.
     @param brightness The brightness level of the device.
     @param kelvin The color temperature (in Kelvin) of the device.
     */
    public SmartDevice(String name, boolean isOn, int brightness, int kelvin) {
        this.name = name;
        this.isOn = isOn;
        this.brightness = brightness;
        this.kelvin = kelvin;
    }

    /**
     Constructor that sets the name, on/off status, brightness level, and color code of the SmartDevice class.
     @param name The name of the device.
     @param isOn The on/off status of the device.
     @param brightness The brightness level of the device.
     @param colorCode The color code of the device.
     */
    public SmartDevice(String name, boolean isOn, int brightness, String colorCode) {
        this.name = name;
        this.isOn = isOn;
        this.brightness = brightness;
        this.colorCode = colorCode;
    }

    /**
     Returns the name of the device.
     @return The name of the device.
     */
    public String getName() {
        return name;
    }

    /**
     Sets the name of the device.
     @param name The name of the device.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     Method that determines whether the device is on or off.
     @return The on/off status of the device.
     */

    public String isOn() {
        if (isOn) {
            return "On";
        } else {
            return "Off";
        }
    }

    /**
     Sets the temperature level of the device.
     @param kelvin The temperature level of the device.
     */

    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }
    /**
     Sets the color code of the SmartDevice.
     @param colorCode the color code to be set
     */
    public void setColorCode(String colorCode) {
        System.out.println();
    }
    /**
     Returns the time of the SmartDevice.
     @return the time of the SmartDevice
     */

    public LocalDateTime getTime() {
        return time;
    }

    /**
     Sets the time of the SmartDevice.
     @param time the time to be set
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    /**

     Returns the current state of the SmartDevice (on or off).
     @return the current state of the SmartDevice
     */
    public boolean getIsOn() {
        return isOn;
    }

    /**
     Returns the current ampere of the SmartDevice.
     @return the current ampere of the SmartDevice
     */
    public double getAmpere() {
        return ampere;
    }

    /**
     Sets the current ampere of the SmartDevice.
     @param ampere the current ampere to be set
     */
    public void setAmpere(double ampere) {
        this.ampere = ampere;
    }

    /**
     Switches on the device if it is off.
     If the device is already switched on, an error message is printed.
     */
    public void switchOn(String Output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Output, true))) {
            if (isOn == false) {
                isOn = true;
            }
            else {
                System.out.println("ERROR: This device is already switched on!");
                writer.write("ERROR: This device is already switched on!\n");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     Switches off the device if it is on.
     If the device is already switched off, an error message is printed.
     */
    public void switchOff(String output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            if (isOn == true) {
                isOn = false;
            }
            else {
                System.out.println("ERROR: This device is already switched off!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
