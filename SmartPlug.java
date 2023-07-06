import java.time.Duration;
import java.time.LocalDateTime;

/**

 A class representing a smart plug device.
 */
public class SmartPlug extends SmartDevice {
    private LocalDateTime time;         //The date and time of when the device was plugged in or unplugged.
    private boolean plugIn = false;     //Whether the device is currently plugged in or not, default value is false.
    private double ampere;              //The electric current flowing through the plug, measured in amperes.
    private Duration timePass;          //The duration of time the plug has been in use.
    private double energyConsumption;   //The amount of energy consumed by the plug, measured in kilowatt hours (kWh).
    /**
     Creates a new SmartPlug object with the given name.
     @param name The name of the plug.
     */
    public SmartPlug(String name) {
        super(name);
    }
    /**
     Creates a new SmartPlug object with the given name and on/off status.
     @param name The name of the plug.
     @param isOn The initial on/off status of the plug.
     */
    public SmartPlug(String name, boolean isOn) {
        super(name, isOn);
    }

    /**
     Creates a new SmartPlug object with the given name, initial on/off status, and maximum ampere rating.
     @param name The name of the plug.
     @param isOn The initial on/off status of the plug.
     @param ampere The maximum ampere rating of the plug.
     */
    public SmartPlug(String name, boolean isOn, double ampere) {
        super(name, isOn);
        this.ampere = ampere;
        this.energyConsumption = 0;
    }
    /**
     Returns the electric current flowing through the plug.
     @return the electric current flowing through the plug, measured in amperes.
     */
    public double getAmpere() {
        return ampere;
    }
    /**
     Sets the electric current flowing through the plug.
     @param ampere the electric current flowing through the plug, measured in amperes.
     */
    public void setAmpere(double ampere) {
        this.ampere = ampere;
    }

    /**
     Returns the current date and time of the plug.
     @return the current date and time of the plug.
     */

    public LocalDateTime getTime() {
        return time;
    }
    /**
     Sets the current date and time of the plug.
     @param time the current date and time of the plug.
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     Returns whether the device is currently plugged in or not.
     @return true if the device is currently plugged in, false otherwise.
     */
    public boolean isPlugIn() {
        return plugIn;
    }
    /**
     Sets whether the device is currently plugged in or not.
     @param plugIn true if the device is currently plugged in, false otherwise.
     */
    public void setPlugIn(boolean plugIn) {
        this.plugIn = plugIn;
    }
    /**
     Returns the amount of energy consumed by the plug.
     @return the amount of energy consumed by the plug, measured in kilowatt hours (kWh).
     */
    public double getEnergyConsumption() {
        return energyConsumption;
    }
    /**
     Sets the amount of energy consumed by the plug.
     @param timePass the amount of energy consumed by the plug, measured in kilowatt hours (kWh).
     */
    public void setEnergyConsumption(Duration timePass) {
        this.energyConsumption += this.ampere * 220 *(timePass.getSeconds()/60)/60;
    }
}