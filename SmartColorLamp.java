/**
 A subclass of SmartLamp representing a smart color lamp with adjustable brightness, kelvin temperature, and color code.
 */
    public class SmartColorLamp extends SmartLamp {
    private int kelvin = 4000;          //The kelvin temperature of the lamp, default value is 4000.
    private int brightness = 100;       //The brightness of the lamp, default value is 100.
    private String colorCode ; //The color code of the lamp.
   ;

    /**
     Constructs a SmartColorLamp object with a given name, status, brightness, and kelvin temperature.
     @param name the name of the lamp
     @param status the status of the lamp, true for on and false for off
     @param brightness the brightness of the lamp
     @param kelvin the kelvin temperature of the lamp
     */
    public SmartColorLamp(String name, boolean Status, int brightness, int kelvin) {
        super(name, brightness);
        this.kelvin = kelvin;
    }

    /**
     Constructs a SmartColorLamp object with a given name, status, brightness, and color code.
     @param name the name of the lamp
     @param status the status of the lamp, true for on and false for off
     @param brightness the brightness of the lamp
     @param colorCode the color code of the lamp
     */
    public SmartColorLamp(String name, boolean Status, int brightness, String colorCode) {
        super(name, brightness);
        this.colorCode = colorCode;
    }

    /**

     Constructs a new SmartColorLamp object with specified name and status.
     @param name the name of the lamp
     @param status the initial on/off status of the lamp
     */
    public SmartColorLamp(String name,boolean Status) {
        super(name,Status);
    }
    /**

     Constructs a new SmartColorLamp object with specified name.
     @param name the name of the lamp
     */
    public SmartColorLamp(String name) {
        super(name);
    }
    /**
     Returns the Kelvin value of the lamp.
     @return the Kelvin value of the lamp
     */
    public int getKelvin() {
        return kelvin;
    }
    /**
     Sets the Kelvin value of the lamp.
     @param kelvin the new Kelvin value of the lamp
     */
    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }
    /**
     Gets the brightness of the lamp.
     @return the brightness of the lamp
     */
    public int getBrightness() {
        return brightness;
    }
    /**
     Sets the brightness of the lamp.
     @param brightness the brightness to set
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getColorCode(){return this.colorCode;}
    /**
     Sets the color of the lamp.
     @param colorCode the color code to set
     */
    @Override
    public void setColor(String colorCode) {
        this.colorCode = colorCode;
    }


}