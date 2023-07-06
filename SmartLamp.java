/**
 A class representing a smart lamp device.
 */
public class SmartLamp extends SmartDevice {
    private int kelvin = 4000;           //The kelvin temperature of the lamp, default value is 4000.
    private int brightness = 100;        //The brightness of the lamp, default value is 100.

    /**
     Creates a new SmartLamp object with the given name.
     @param name The name of the lamp.
     */
    public SmartLamp(String name) {
        super(name);
    }
    /**
     Creates a new SmartLamp object with the given name and on/off status.
     @param name The name of the lamp.
     @param isOn The initial on/off status of the lamp.
     */
    public SmartLamp(String name,boolean isOn) {
        super(name,isOn);
    }
    /**
     Creates a new SmartLamp object with the given name and brightness.
     @param name The name of the lamp.
     @param brightness The initial brightness value of the lamp.
     */
    public SmartLamp(String name, int brightness) {
        super(name);
        this.brightness = brightness;
    }
    /**
     Creates a new SmartLamp object with the given name, initial on/off status, color temperature, and brightness level.
     @param name the name of the lamp
     @param isOn the initial on/off status of the lamp
     @param kelvin the initial color temperature of the lamp, measured in kelvin
     @param brightness the initial brightness level of the lamp
     */
    public SmartLamp(String name,boolean isOn, int kelvin, int brightness) {
        super(name,isOn);
        this.kelvin = kelvin;
        this.brightness = brightness;
    }
    /**
     Gets the current color temperature of the lamp.
     @return the current color temperature, measured in kelvin
     */
    public int getKelvin() {
        return kelvin;
    }
    /**

     Sets the color temperature of the lamp.
     @param kelvin the new color temperature, measured in kelvin
     */
    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }
    /**
     Returns the brightness level of the lamp.
     @return the brightness level of the lamp.
     */
    public int getBrightness() {
        return brightness;
    }
    /**
     Sets the brightness level of the lamp.
     @param brightness the new brightness level of the lamp.
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }
    /**
     Sets the color of the lamp to the specified color code.
     @param colorCode the color code to set the lamp to.
     */
    public void setColor(String colorCode) {
        System.out.println();
    }


}