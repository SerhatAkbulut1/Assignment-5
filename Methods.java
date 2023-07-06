import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Methods {


    /**
     * Checks if the given name is unique among the list of smart devices.
     * @param SmartDevices The list of smart devices to check against.
     * @param name The name to check for uniqueness.
     * @return true if the name is unique, false otherwise.
     */
    public static boolean nameCheck(ArrayList<SmartDevice> SmartDevices, String name) {

        boolean nameCheck = true;
        for (SmartDevice smartDevice : SmartDevices) {
            if (smartDevice.getName().equals(name)) {
                nameCheck = false;
            }
        }
        return nameCheck;
    }
    /**
     * Outputs the given inputs as a command string to the console.
     * @param inputs An array of Strings representing the command inputs.
     * @throws IOException If an error occurs while writing to the output file.
     */
    public static void commandWriter(String[] inputs,String output ) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            System.out.print("COMMAND:");
            writer.write("COMMAND:");
            for(String input : inputs ){
                System.out.print(" "+input+"\t");
                writer.write(" "+input+"\t");
            }
            System.out.println();
            writer.write("\n");
        }
    }

    /**
     Parses a string input representing a date and time in the format "yyyy-MM-dd_HH:mm:ss" and returns a LocalDateTime object.
     @param input A string representing a date and time in the format "yyyy-MM-dd_HH:mm:ss".
     @return A LocalDateTime object representing the parsed date and time.
     @throws DateTimeException If the input string is not in the correct format.
     @throws IOException If an error occurs while writing to the output file.
     */
    public static LocalDateTime setInitialTime(String input,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                return LocalDateTime.parse(input, formatter);
            }
            catch (DateTimeException ex) {
                System.out.println("ERROR: Time format is not correct!");
                writer.write("ERROR: Time format is not correct!\n");
                throw ex;
            }

        }

    }
    /**
     Sets a LocalDateTime object based on the given input string with a specified format.
     @param input the input string to be parsed as a LocalDateTime object.
     @param output the output file path for error messages.
     @return the LocalDateTime object generated from the input string.
     @throws DateTimeException if the input string is not in the correct format.
     @throws RuntimeException if an error occurs while writing to the output file.
     */
    public static LocalDateTime setTime(String input,String output){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                return LocalDateTime.parse(input, formatter);
            }
            catch (DateTimeException ex) {
                System.out.println("ERROR: Time format is not correct!");
                writer.write("ERROR: Time format is not correct!\n");
                throw ex;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     This method skips the specified number of minutes from the given time.
     @param time The initial LocalDateTime object.
     @param minutes The number of minutes to be skipped.
     @return The new LocalDateTime object obtained by adding the specified minutes to the initial time.
     */
    public static LocalDateTime skipMinutes(LocalDateTime time, int minutes) {

        return time.plusMinutes(minutes);
    }

    // nop methodu
    /*public static LocalDateTime nop(LocalDateTime current,ArrayList<SmartDevice> SmartDevices) {


        return ;
    }*/

    /**
     Adds a new SmartPlug device to the list of SmartDevices with given inputs.
     If the given inputs are invalid, returns an error message and does not add the device.
     @param SmartDevices the ArrayList of SmartDevices to add the new SmartPlug device to
     @param inputs the array of inputs containing the device name, starting state, and amperage (if given)
     @param setTime the LocalDateTime object representing the time to set the device to
     @throws IOException If an error occurs while writing to the output file.
     */
    public static void addSmartPlug(ArrayList<SmartDevice> SmartDevices, String[] inputs, LocalDateTime setTime,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            if (inputs.length < 3 || inputs.length > 5) {                          //Checks the validity of the command length.
                System.out.println("ERROR: Erroneous command!");
                writer.write("ERROR: Erroneous command!\n");
                return;
            }
            String deviceName = inputs[2];
            if (nameCheck(SmartDevices, deviceName)) {                            //Checks if the given name is present in the list.
                if (SmartDevices.contains(deviceName)) {
                    System.out.println("ERROR: There is already a smart device with same name!");
                    writer.write("ERROR: There is already a smart device with same name!\n");
                    return;
                }
                boolean state = true;
                if (inputs.length == 3) {
                    SmartPlug smartPlug = new SmartPlug(deviceName);
                    SmartDevices.add(smartPlug);
                    return;
                }
                if (inputs.length == 4) {                                        //Checks the validity of the "On" and "Off" state provided by the input.
                    String startStateString = inputs[3];
                    if (!startStateString.equals("On") && !startStateString.equals("Off")) {
                        System.out.println("Invalid starting state. It must be either ON or OFF.");
                        writer.write("Invalid starting state. It must be either ON or OFF.\n");
                        return;
                    }

                    state = startStateString.equals("On");                      //?
                    SmartPlug smartPlug = new SmartPlug(deviceName, state);
                    smartPlug.setTime(setTime);
                    SmartDevices.add(smartPlug);
                    return;
                }
                double amper = 0;
                if (inputs.length == 5) {
                    try {
                        amper = Double.parseDouble(inputs[4]);
                        if (amper < 0.0) {                                      //Checks whether the current format of the amperage is valid or not.
                            System.out.println("ERROR: Ampere value must be a positive number!");
                            writer.write("ERROR: Ampere value must be a positive number!\n");
                            return;
                        }
                        else {
                            SmartPlug smartPlug = new SmartPlug(deviceName, state, amper);
                            SmartDevices.add(smartPlug);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: Ampere value must be a positive number!");
                        writer.write("ERROR: Ampere value must be a positive number!\n");
                        return;
                    }
                }

            }
            else {
                System.out.println("ERROR: There is already a smart device with same name!");
                writer.write("ERROR: There is already a smart device with same name!\n");
            }
        }

    }

    /**
     Adds a new SmartCamera device to the given list of SmartDevices, based on the given inputs.
     The inputs must contain the name of the device, the number of megabytes per record, and optionally an initial status of "on" or "off".
     If the inputs are invalid or incomplete, an error message is printed and the method returns.
     If there is already a SmartDevice with the same name, an error message is printed and the method returns.
     @param SmartDevices the list of SmartDevices to add the new device to
     @param inputs the inputs containing the name, megabytes per record, and optionally the initial status
     @throws IOException If an error occurs while writing to the output file.
     */
    public static void addSmartCamera(ArrayList<SmartDevice> SmartDevices, String[] inputs,String output) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            if (inputs.length < 4 || inputs.length > 5) {                               //Checks the validity of the command length.
                System.out.println("ERROR: Erroneous command!");
                writer.write("ERROR: Erroneous command!\n");
                return;
            }
            String deviceName = inputs[2];
            double megabytesPerRecord;
            if (nameCheck(SmartDevices, deviceName)) {
                try {
                    megabytesPerRecord = Double.parseDouble(inputs[3]);
                    if (megabytesPerRecord < 0) {                                       //Checks whether the current format of the megabaytsPerRecord is valid or not.
                        System.out.println("ERROR: Megabyte value must be a positive number!");
                        writer.write("ERROR: Megabyte value must be a positive number!\n");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Megabyte value must be a positive number!");
                    writer.write("ERROR: Megabyte value must be a positive number!\n");
                    return;
                }
                if (SmartDevices.contains(deviceName)) {                                //Checks if the given name is present in the list.
                    System.out.println("ERROR: There is already a smart device with same name!");
                    writer.write("ERROR: There is already a smart device with same name!\n");
                    return;
                }
                if (inputs.length == 4) {
                    SmartCamera camera = new SmartCamera(deviceName, megabytesPerRecord); //SmartCamera camera = new SmartCamera(deviceName, initialStatus, megabytesPerRecord);
                    SmartDevices.add(camera);
                }
                if (inputs.length == 5) {
                    boolean initialStatus = inputs[4].equalsIgnoreCase("On");
                    SmartCamera camera = new SmartCamera(deviceName, initialStatus, megabytesPerRecord);
                    SmartDevices.add(camera);
                }
            }
            else {
                System.out.println("ERROR: There is already a smart device with same name!");
                writer.write("ERROR: There is already a smart device with same name!\n");
            }

        }

    }

    /**
     Adds a new SmartLamp device to the given list of SmartDevices, based on the given inputs.
     The inputs must contain the name of the device, and optionally an initial status of "on" or "off",
     and if provided, a Kelvin value and brightness level.
     If the inputs are invalid or incomplete, an error message is printed and the method returns.
     If there is already a SmartDevice with the same name, an error message is printed and the method returns.
     @param SmartDevices the list of SmartDevices to add the new device to
     @param inputs the inputs containing the name, initial status (optional), kelvin value (optional), and brightness level (optional)
     @throws IOException If an error occurs while writing to the output file.
     */

    public static void addSmartLamp(ArrayList<SmartDevice> SmartDevices, String[] inputs,String output ) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            String deviceName = inputs[2];
            if (nameCheck(SmartDevices, deviceName)) {
                if (!deviceName.contains("Lamp")) {
                    System.out.println("ERROR: Erroneous command!");
                    writer.write("ERROR: Erroneous command!\n");
                    return;
                }
                boolean initialStatus = false;
                int kelvinValue = 0;
                int brightness = 0;

                if (inputs.length == 3) {
                    SmartLamp smartLamp = new SmartLamp(deviceName);
                    SmartDevices.add(smartLamp);
                }
                if (inputs.length == 4) {
                    if (inputs[3].equals("On")) {
                        initialStatus = true;
                        SmartLamp smartLamp = new SmartLamp(deviceName, initialStatus);
                        SmartDevices.add(smartLamp);
                    }
                    else if (inputs[3].equals("Off")) {
                        initialStatus = false;
                        SmartLamp smartLamp = new SmartLamp(deviceName, initialStatus);
                        SmartDevices.add(smartLamp);
                    }
                    else {
                        System.out.println("ERROR: Erroneous command!");
                        writer.write("ERROR: Erroneous command!\n");
                    }


                }
                if (inputs.length == 6) {
                    kelvinValue = Integer.parseInt(inputs[4]);
                    brightness = Integer.parseInt(inputs[5]);
                    if (kelvinValue < 2000 || kelvinValue > 6500) {                 //Checks if the value of Kelvin is within the specified range.
                        System.out.println("Kelvin value must be in range of 2000K-6500K!");
                        writer.write("Kelvin value must be in range of 2000K-6500K!\n");
                        return;
                    }
                    else if (brightness < 0 || brightness > 100) {                 //Checks if the value of Brightness is within the specified range.
                        System.out.println("Brightness must be in range of 0%-100%!");
                        writer.write("Brightness must be in range of 0%-100%!\n");
                        return;
                    }
                    else {
                        SmartLamp smartLamp = new SmartLamp(deviceName, initialStatus);
                        SmartDevices.add(smartLamp);
                    }
                }
            }
            else {
                System.out.println("ERROR: There is already a smart device with same name!");
                writer.write("ERROR: There is already a smart device with same name!\n");
            }

        }

    }

    /**
     Adds a new SmartColorLamp object to the given ArrayList of SmartDevices.
     The inputs array contains the necessary parameters for creating a SmartColorLamp object.
     @param SmartDevices the ArrayList of SmartDevices to which the SmartColorLamp object will be added
     @param inputs the input parameters required for creating a SmartColorLamp object
     @throws IOException If an error occurs while writing to the output file.
     */

    public static void addSmartColorLamp(ArrayList<SmartDevice> SmartDevices, String[] inputs,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cikis.txt", true))) {
            String deviceName = inputs[2];
            String colorValue;
            boolean initialStatus = false;
            int kelvinValue = 0;
            int brightness = 0;
            String f = "F";
            char F = f.charAt(0);
            boolean change = false;

            if (nameCheck(SmartDevices, deviceName)) {
                if (inputs.length == 3) {
                    SmartColorLamp smartColorLamp = new SmartColorLamp(deviceName);
                    SmartDevices.add(smartColorLamp);
                }
                if (inputs.length == 4) {
                    SmartColorLamp smartColorLamp = new SmartColorLamp(deviceName, initialStatus);
                    SmartDevices.add(smartColorLamp);
                }
                if (inputs.length == 6) {
                    if (inputs[4].contains("0x")) {                                     //Checks whether the given value is a hexadecimal number or not.
                        colorValue = inputs[4];
                        brightness = Integer.parseInt(inputs[5]);
                        if (brightness < 0 || brightness > 100) {
                            System.out.println("Brightness must be in range of 0%-100%!");
                            writer.write("Brightness must be in range of 0%-100%!\n");
                            return;
                        }
                        if (inputs[1].equals("SmartColorLamp")) {
                            if (inputs[4].length() == 8) {                          //Checks if the values in the entered hexadecimal code are greater than the character 'F'.
                                for (int i = 2; i < inputs[4].length() - 1; i++) {
                                    char c = inputs[4].charAt(i);
                                    if (c <= F) {
                                        change = true;
                                    }
                                    else{
                                        change = false;
                                        break;
                                    }
                                }

                                if(change){                 //??
                                    SmartColorLamp smartColorLamp = new SmartColorLamp(deviceName, initialStatus,
                                            brightness, colorValue);
                                    SmartDevices.add(smartColorLamp);
                                }
                                else {
                                    System.out.println("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
                                    writer.write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                                }
                            }else{
                                System.out.println("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
                                writer.write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                            }
                        }

                    }
                    else {
                        kelvinValue = Integer.parseInt(inputs[4]);
                        brightness = Integer.parseInt(inputs[5]);
                        if (kelvinValue < 2000 || kelvinValue > 6500) {
                            System.out.println("Kelvin value must be in range of 2000K-6500K!");//Checks if the value of Kelvin is within the specified range.
                            writer.write("Kelvin value must be in range of 2000K-6500K!\n");
                            return;
                        }
                        else if (brightness < 0 || brightness > 100) {
                            System.out.println("Brightness must be in range of 0%-100%!");           //Checks if the value of Brightness is within the specified range.
                            writer.write("Brightness must be in range of 0%-100%!\n");
                            return;
                        }
                        SmartColorLamp smartColorLamp = new SmartColorLamp(deviceName, initialStatus, brightness, kelvinValue);
                        SmartDevices.add(smartColorLamp);
                    }
                }
            }
            else {
                System.out.println("ERROR: There is already a smart device with same name!");
                writer.write("ERROR: There is already a smart device with same name!\n");
            }
        }


    }

    /**
     Removes a SmartDevice from the ArrayList of SmartDevices with the given name.
     The device is also turned off before being removed.
     @param Devices the ArrayList of SmartDevices to remove the device from
     @param name the name of the SmartDevice to remove
     @throws IOException If an error occurs while writing to the output file.
     */

    public static void removeDevice(ArrayList<SmartDevice> Devices, String name,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            boolean deviceFound = false;
            for (SmartDevice device : Devices) {
                if (device.getName().equals(name)) {
                    deviceFound = true;
                    device.switchOff(output);
                    Devices.remove(device);
                    break;
                }
            }
            if (!deviceFound) {
                System.out.println("Error: Device not found");
                writer.write("Error: Device not found.\n");
            }
        }

    }

    /**
     Sets a switch time for a specified device in the ArrayList of SmartDevices.
     If the inputs are erroneous, an error message is printed.
     If the device is found, the switch time is set and the device is toggled if the switch time is now.
     If the device is not found, an error message is printed.
     @param Devices the ArrayList of SmartDevices to search for the specified device
     @param inputs an array of String inputs, where inputs[1] is the name of the device and inputs[2] is the switch time
     @param currentTime the current time in LocalDateTime format
     @throws IOException If an error occurs while writing to the output file.
     */

    public static void setSwitchTime(ArrayList<SmartDevice> Devices, String[] inputs, LocalDateTime currentTime,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            if(inputs.length !=3){                                  //Checks the validity of the command length.
                System.out.println("ERROR: Erroneous command!");
                writer.write("ERROR: Erroneous command!\n");
                return;
            }
            String name = inputs[1];
            String dateTime = inputs[2];
            LocalDateTime switchDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
            boolean deviceFound = false;

            for (SmartDevice device : Devices) {
                if (device.getName().equals(name)) {
                    device.setTime(switchDateTime);
                    if (device.getTime() == switchDateTime) {
                        toggleDevice(Devices, device.isOn(), device.getName(), currentTime,output);
                    }
                    deviceFound = true;
                    break;
                }
            }
            if (!deviceFound) {
                System.out.println("There is not an item that has this name on the list");
                writer.write("There is not an item that has this name on the list\n");
            }
        }

    }
    /**
    Toggles the status of a SmartDevice on or off based on the provided status, and updates relevant properties
    such as energy consumption or record time.
    @param Devices the list of SmartDevices to search through
    @param status a String indicating whether the device should be turned on or off
    @param name the name of the device to toggle
    @param currenTime the current time as a LocalDateTime object
     @throws IOException If an error occurs while writing to the output file.
    */
    public static void toggleDevice(ArrayList<SmartDevice> Devices, String status, String name,LocalDateTime currenTime,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            boolean found = false;
            for (SmartDevice smartDevice : Devices) {
                if (smartDevice.getName().equals(name)) {
                    found = true;
                    if(smartDevice instanceof SmartCamera){
                        if (status.equals("Off")) {
                            if (smartDevice.getIsOn()) {
                                smartDevice.setTime(currenTime);
                                smartDevice.switchOff(output);
                            }
                        } else if(status.equals(("On"))){
                            if (!smartDevice.getIsOn()) {
                                ((SmartCamera) smartDevice).setRecordTime(Duration.between(smartDevice.getTime() , currenTime));
                                smartDevice.switchOn(output);
                            }
                            //smartDevice.switchOff();
                        }

                    }else if(smartDevice instanceof SmartPlug){
                        if (status.equals("Off")) {
                            if (smartDevice.getIsOn()) {
                                ((SmartPlug) smartDevice).setEnergyConsumption(Duration.between(smartDevice.getTime(),currenTime));
                                smartDevice.switchOff(output);
                            }
                        } else {
                            if (!smartDevice.getIsOn()) {
                                smartDevice.setTime(currenTime);
                                smartDevice.switchOn(output);
                            }
                        }
                    }
                }
            }
            if (!found) {
                System.out.println("There is not an item that has this name on the list");
                writer.write("There is not an item that has this name on the list\n");
            }
        }

    }

    /**
     Renames a device with the specified old name to the new name.
     @param Devices an ArrayList of SmartDevice objects representing all devices in the system
     @param oldName a String representing the current name of the device to be renamed
     @param newName a String representing the desired new name for the device
     @throws IOException If an error occurs while writing to the output file.
     */
    public static void renameDevice(ArrayList<SmartDevice> Devices, String oldName, String newName,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            boolean found = false;
            boolean rename = true;
            if(oldName.equals(newName)){
                System.out.println("ERROR: Both of the names are the same, nothing changed!");
                writer.write("ERROR: Both of the names are the same, nothing changed!\n");
                return;
            }
            for (SmartDevice smartDevice: Devices) {
                if(smartDevice.getName().equals(newName)){
                    System.out.println("ERROR: There is already a smart device with same name!");
                    writer.write("ERROR: There is already a smart device with same name!\n");
                    rename=false;
                    break;
                }
            }
            if(rename){
                for (SmartDevice smartDevice : Devices) {
                    if (smartDevice.getName().equals(oldName)) {
                        smartDevice.setName(newName);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Error: Device not foundrenmae");
                    writer.write("Error: Device not found.\n");
                }
            }

        }

    }

    /**
     This method plugs in a device to a SmartPlug if it is not already plugged in, sets the ampere value of the plug,
     and sets the plug status to "plugged in". If the device is already plugged in, it updates the ampere value of the plug.
     @param Devices an ArrayList of SmartDevice objects representing the devices in the system
     @param name a String representing the name of the SmartPlug to plug the device in
     @param amper a double representing the amper value of the device being plugged in
     @param currentTime a LocalDateTime representing the current time when the device is being plugged in
     @throws IOException If an error occurs while writing to the output file.
     */

    public static void plugInDevice(ArrayList<SmartDevice> Devices, String name, double amper, LocalDateTime currentTime,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            boolean found = false;
            boolean plugedchanged = false;
            for (SmartDevice smartDevice : Devices) {
                if (smartDevice.getName().equals(name)) {
                    if (!(smartDevice instanceof SmartPlug)) {
                        System.out.println("Error: The device is not a plug");
                        writer.write("Error: The device is not a plug.\n");
                        found = true;
                        return;
                    } else {
                        if (!((SmartPlug) smartDevice).isPlugIn()) {
                            smartDevice.setTime(currentTime);
                            found = true;
                            if (amper <= 0) {                       //Checks whether the current format of the amperage is valid or not.
                                System.out.println("Error: Amper value should be positive");
                                writer.write("Error: Amper value should be positive.\n");
                            } else {
                                if (((SmartPlug) smartDevice).getAmpere() == 0) {
                                    ((SmartPlug) smartDevice).setAmpere(amper);
                                    ((SmartPlug) smartDevice).setPlugIn(true);
                                }
                            }
                        }
                        else if (((SmartPlug) smartDevice).getAmpere() == 0) {
                            ((SmartPlug) smartDevice).setAmpere(amper);
                            found = true;
                            plugedchanged = true;
                        }
                        else {
                            if (!plugedchanged) {
                                System.out.println("ERROR: There is already an item plugged in to that plug!");
                                writer.write("ERROR: There is already an item plugged in to that plug!\n");
                                found = true;
                            }
                        }
                    }
                }
            }
            if (!found) {
                System.out.println("Error: Device not foundplugin");
                writer.write("Error: Device not found.\n");
            }
        }

    }

    /**
     This method is used to unplug a device from a smart plug.
     If the device is not a SmartPlug, an error message is displayed.
     If the device is already unplugged, an error message is displayed.
     If the device is plugged in, its energy consumption is updated and the plug is marked as unplugged.
     @param Devices ArrayList of SmartDevices containing all devices in the system
     @param name String representing the name of the SmartPlug to unplug the device from
     @param currentTime LocalDateTime representing the current time
     @throws IOException If an error occurs while writing to the output file.
     */
    public static void plugOutDevice(ArrayList<SmartDevice> Devices, String name, LocalDateTime currentTime,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            boolean found = false;
            boolean plugedin = true;
            for (SmartDevice smartDevice : Devices) {
                if (smartDevice.getName().equals(name)) {
                    found = true;
                    if (!(smartDevice instanceof SmartPlug)) {
                        System.out.println("Error: The device is not a plug");
                        writer.write("Error: The device is not a plug\n");
                        return;
                    } else {
                        if (((SmartPlug) smartDevice).isPlugIn()) {
                            if(smartDevice.isOn().equals("On")){
                                ((SmartPlug) smartDevice).setEnergyConsumption(Duration.between(smartDevice.getTime(),currentTime));
                            }
                            ((SmartPlug) smartDevice).setPlugIn(false);
                            found = true;
                            plugedin = false;

                        }
                    }
                }
            }
            if (plugedin) {
                System.out.println("ERROR: This plug has no item to plug out from that plug!");
                writer.write("ERROR: This plug has no item to plug out from that plug!\n");
            }
            if (!found) {
                System.out.println("Error: Device not foundplugout");
                writer.write("Error: Device not found.\n");
            }
        }

    }

/**
 Sets the kelvin value of a SmartLamp or SmartColorLamp object with the given name.
 If the object is not found or is not a SmartLamp or SmartColorLamp, an error message is printed.
 If the given kelvin value is outside of the valid range of 2000K-6500K, an error message is printed.
 @param SmartDevices An ArrayList of SmartDevice objects to search through
 @param name The name of the SmartLamp or SmartColorLamp object to set the kelvin value of
 @param kelvin The kelvin value to set
 @throws IOException If an error occurs while writing to the output file.
  */
    public static void setKelvin(ArrayList<SmartDevice> SmartDevices, String name, int kelvin,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            boolean found = false;
            for (SmartDevice device : SmartDevices) {
                if (device.getName().equals(name) && device instanceof SmartLamp) {
                    if (kelvin < 2000 || kelvin > 6500) {                               //Checks if the value of Kelvin is within the specified range.
                        System.out.println("ERROR: Kelvin value must be in range of 2000K-6500K!");
                        writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                    }
                    else {
                        ((SmartLamp) device).setKelvin(kelvin);
                    }
                    found = true;
                    break;
                } else if (device.getName().equals(name) && device instanceof SmartColorLamp) {

                    if (kelvin < 2000 || kelvin > 6500) {                       //Checks if the value of Kelvin is within the specified range.
                        System.out.println("ERROR: Kelvin value must be in range of 2000K-6500K!");
                        writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                    }
                    else {
                        ((SmartColorLamp) device).setKelvin(kelvin);
                    }
                    found = true;
                    break;
                }
            }
        }

    }

    /**
     Sets the brightness level of a SmartLamp with the given name to the specified value.
     @param SmartDevices list of SmartDevices
     @param name name of the SmartLamp to set the brightness of
     @param brightness brightness level to set the SmartLamp to (0-100)
     @throws IOException If an error occurs while writing to the output file.
     */
    public static void setBrightness(ArrayList<SmartDevice> SmartDevices, String name, int brightness,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            boolean found = false;
            for (SmartDevice device : SmartDevices) {
                if (device instanceof SmartLamp && device.getName().equals(name)) {
                    if (brightness < 0 || brightness > 100) {                   //Checks if the value of Brightness is within the specified range.
                        System.out.println("ERROR: Brightness must be in range of 0%-100%!");
                        writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                    } else {
                        ((SmartLamp) device).setBrightness(brightness);

                    }
                    found = true;
                    break;
                }
            }
        }

    }

    /**
     Sets the color code of a SmartColorLamp device with the given name in the given list of SmartDevices.
     @param SmartDevices the list of SmartDevices
     @param name the name of the device to set the color code of
     @param colorCode the color code to set in hexadecimal format (e.g. "0x00FF00" for green)
     @return true if the color code value is not in the range of 0x0-0xFFFFFF, false otherwise
     @throws IOException If an error occurs while writing to the output file.
     */

    public static boolean setColorCode(ArrayList<SmartDevice> SmartDevices, String name, String colorCode,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            boolean found = false;
            String f = "F";
            boolean change = false;
            char F = f.charAt(0);
            for (SmartDevice device : SmartDevices) {
                if (device.getName().equals(name))
                {
                    if (device instanceof SmartColorLamp) {
                        if (colorCode.length() == 8) {
                            for (int i = 2; i < colorCode.length() - 1; i++) {
                                char c = colorCode.charAt(i);
                                if (c < F) {
                                    ((SmartColorLamp)device).setColor(colorCode); //device.setColorCode(colorCode) şeklindeydi
                                } else {
                                    change = true;
                                }
                            }
                        }else{
                            change = true;
                        }
                    }
                }
            }
            if (!found) {
                System.out.println("ERROR: This device is not a smart color lamp!");
                writer.write("ERROR: This device is not a smart color lamp!\n");
            }
            if (change) {
                System.out.println("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
                writer.write("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
            }
            return change;
        }

    }
    /**
     Sets the kelvin and brightness of a smart lamp or smart color lamp with the given name.
     @param SmartDevices An ArrayList of SmartDevice objects.
     @param name The name of the device to set the kelvin and brightness for.
     @param kelvin The kelvin value to set for the device.
     @param brightness The brightness value to set for the device.
     @throws IOException If an error occurs while writing to the output file.
     */
    public static void setWhite(ArrayList<SmartDevice> SmartDevices, String name, int kelvin, int brightness,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cikis.txt", true))) {
            for (SmartDevice device : SmartDevices) {
                if (device.getName().equals(name)) {
                    if(device instanceof SmartLamp){
                        if(kelvin >=2000 || kelvin <= 6500){
                            if(brightness>= 0|| brightness<= 100){
                                setKelvin(SmartDevices,device.getName(),kelvin,output);
                                setBrightness(SmartDevices, device.getName(), brightness,output);
                            }else{
                                System.out.println("ERROR: Brightness must be in range of 0%-100%!");
                                writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                            }
                        }else{
                            System.out.println("ERROR: Kelvin value must be in range of 2000K-6500K!");
                            writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                        }
                    }else{
                        System.out.println("ERROR: This device is not a smart lamp!");
                        writer.write("ERROR: This device is not a smart lamp!\n");
                    }
                }
            }
        }

    }

    /**
     Sets the color and brightness of a SmartColorLamp device with the given name.
     @param SmartDevices An ArrayList of SmartDevice objects representing all the devices.
     @param name A String representing the name of the device to set the color and brightness for.
     @param brightness An int representing the brightness value, in range of 0%-100%, to set for the device.
     @param colorCode A String representing the color code to set for the device.
     @param output A String representing the file path to output any error messages to.
     @throws IOException If an error occurs while writing to the output file.
     */

    public static void setColor(ArrayList<SmartDevice> SmartDevices, String name, int brightness, String colorCode,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            for (SmartDevice device : SmartDevices) {
                if (device.getName().equals(name)) {
                    if( device instanceof SmartColorLamp){
                        String tempColorCode = ((SmartColorLamp) device).getColorCode();
                        if(!setColorCode(SmartDevices,device.getName(),colorCode,output)){
                            if(brightness>= 0|| brightness<= 100){
                                setColorCode(SmartDevices,device.getName(),colorCode,output);
                                setBrightness(SmartDevices, device.getName(), brightness,output);
                            }else{
                                setColorCode(SmartDevices,device.getName(),tempColorCode,output);
                                System.out.println("ERROR: Brightness must be in range of 0%-100%!");
                                writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                            }
                        }
                    }else{
                        System.out.println("ERROR: This device is not a smart lamp!");
                        writer.write("ERROR: This device is not a smart lamp!\n");
                    }
                }
            }
        }

    }

    /**
     Generates a Z report for a list of Smart Devices.
     @param Devices An ArrayList of SmartDevice objects representing the devices to generate the Z report for.
     @param currentTime A LocalDateTime object representing the current date and time.
     @param output A String representing the file path to output the Z report to.
     @throws IOException If an error occurs while writing to the output file.
     */
    public static void ZReport(ArrayList<SmartDevice> Devices,LocalDateTime currentTime,String output) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            System.out.println("Time is: "+currentTime);
            writer.write("Time is: "+currentTime+"\n");

            for (SmartDevice smartDevice : Devices) {
                if(smartDevice instanceof SmartPlug){
                    System.out.println("Smart Plug "+smartDevice.getName()+" is "+smartDevice.isOn()+" and consumed "+((SmartPlug) smartDevice).getEnergyConsumption()+"W so far (excluding current device), and its time to switch its status is "+smartDevice.getTime());
                    writer.write("Smart Plug "+smartDevice.getName()+" is "+smartDevice.isOn()+" and consumed "+((SmartPlug) smartDevice).getEnergyConsumption()+"W so far (excluding current device), and its time to switch its status is "+smartDevice.getTime()+"\n");
                }else if(smartDevice instanceof  SmartCamera){
                    System.out.println("Smart Camera "+smartDevice.getName()+" is "+smartDevice.isOn()+" and used "+((SmartCamera) smartDevice).getMegabaytOfVideo()+" MB of storage so far (excluding current status),and its time to switch its status is "+smartDevice.getTime());
                    writer.write("Smart Camera "+smartDevice.getName()+" is "+smartDevice.isOn()+" and used "+((SmartCamera) smartDevice).getMegabaytOfVideo()+" MB of storage so far (excluding current status),and its time to switch its status is "+smartDevice.getTime()+"\n");
                } else if(smartDevice instanceof SmartLamp){
                    System.out.println("Smart Lamp "+smartDevice.getName()+" is "+smartDevice.isOn()+" and its kelvin value is "+((SmartLamp) smartDevice).getKelvin()+"K with "+((SmartLamp) smartDevice).getBrightness()+"% brightness, and its time to switch its status is "+ smartDevice.getTime());
                    writer.write(("Smart Lamp "+smartDevice.getName()+" is "+smartDevice.isOn()+" and its kelvin value is "+((SmartLamp) smartDevice).getKelvin()+"K with "+((SmartLamp) smartDevice).getBrightness()+"% brightness, and its time to switch its status is "+ smartDevice.getTime())+"\n");
                }else if (smartDevice instanceof SmartColorLamp) {
                    if(((SmartColorLamp) smartDevice).getColorCode().startsWith("0x")){
                        System.out.println("Smart Color Lamp"+smartDevice.getName()+" is " +smartDevice.isOn()+" and its color value is "+((SmartColorLamp)smartDevice).getColorCode()+" with "+((SmartColorLamp)smartDevice).getBrightness()+"% brightness, and its time to switch its status is "+smartDevice.getTime());
                        writer.write("Smart Color Lamp"+smartDevice.getName()+" is " +smartDevice.isOn()+" and its color value is "+((SmartColorLamp)smartDevice).getColorCode()+" with "+((SmartColorLamp)smartDevice).getBrightness()+"% brightness, and its time to switch its status is "+smartDevice.getTime()+"\n");
                    }else{
                        System.out.println("Smart Color Lamp"+smartDevice.getName()+" is" +smartDevice.isOn()+" and its color value is "+((SmartColorLamp)smartDevice).getKelvin()+"K with "+((SmartColorLamp)smartDevice).getBrightness()+"% brightness, and its time to switch its status is "+smartDevice.getTime());
                        writer.write("Smart Color Lamp"+smartDevice.getName()+" is" +smartDevice.isOn()+" and its color value is "+((SmartColorLamp)smartDevice).getKelvin()+"K with "+((SmartColorLamp)smartDevice).getBrightness()+"% brightness, and its time to switch its status is "+smartDevice.getTime()+"\n");

                    }
                }
            }
        }
        }


}