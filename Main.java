import java.io.*;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.DocFlavor.INPUT_STREAM;

public class Main {

    public static void main(String[] args)  {
        String output = args[1];
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(args[0])))) {
                ArrayList<SmartDevice> SmartDevices = new ArrayList<SmartDevice>();
                LocalDateTime initialTime = Methods.setInitialTime("2023-03-26_14:00:00",output);
                LocalDateTime currentTime = Methods.setInitialTime("2023-03-31_14:00:00",output);
                boolean failed = false;
                //Checks whether the first command is SetInitialTime or not.
                do {
                    String line = scanner.nextLine();

                    if (line.contains("SetInitialTime")) {
                        try{
                            String[] lineList = line.split("\t");
                            Methods.commandWriter(lineList,output);
                            if(lineList.length != 2){
                                System.out.println("ERROR: First command must be set initial time! Program is going to terminate!");
                                writer.write("ERROR: First command must be set initial time! Program is going to terminate!\n");
                                return;
                            }
                            initialTime = Methods.setInitialTime(lineList[1],output);
                            currentTime = initialTime;
                            break;
                        }catch (DateTimeException d){
                            System.out.println("ERROR: Format of the initial date is wrong! Program is going to terminate!");
                            writer.write("ERROR: Format of the initial date is wrong! Program is going to terminate!\n");
                        }

                    }
                    if (!line.equals("")) {
                        System.out.println("ERROR: First command must be set initial time! Program is going to terminate!");
                        writer.write("ERROR: First command must be set initial time! Program is going to terminate!");
                        failed = true;
                        break;
                    }
                } while (!scanner.nextLine().contains("SetInitialTime"));
                if (!failed) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");         //Ensures that the Time state is written correctly.
                    System.out.println("SUCCESS: Time has been set to "+dtf.format(initialTime));
                    writer.write("SUCCESS: Time has been set to "+dtf.format(initialTime)+"\n");
                    while (scanner.hasNextLine()) {                                                     //Ensures that the given commands stay in the loop correctly.
                        String[] inputs = scanner.nextLine().split("\t");
                        switch (inputs[0]){      //It selects which command to give for the first input.
                            case "SetInitialTime":
                                try{
                                    Methods.commandWriter(inputs,output);
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }catch (DateTimeException d){}
                                break;
                            /**
                             Sets the current time to the given time if it is after the current time.
                             @param inputs an array of strings containing the command and the time to be set
                             @param output the output stream to write the command result to
                             */
                            case "SetTime":
                                Methods.commandWriter(inputs,output);
                                try {
                                    LocalDateTime tempTime = Methods.setTime(inputs[1],output);
                                    if(tempTime.isEqual(currentTime)){
                                        System.out.println("ERROR: There is nothing to change!");
                                        writer.write("ERROR: There is nothing to change!");
                                        return;
                                    }
                                    if(tempTime.isAfter(currentTime)){
                                        currentTime = tempTime;
                                    }else{
                                        System.out.println("ERROR: Time cannot be reversed!");
                                        writer.write("ERROR: Time cannot be reversed!\n");
                                        return;
                                    }
                                } catch (Exception e) {
                                    System.out.println("ERROR: Time format is not correct!");
                                    writer.write("ERROR: Time format is not correct!\n");
                                }
                                break;
                            /**
                             This case is responsible for skipping the given number of minutes in the current time.
                             It receives the user input, checks if it is a valid command, and extracts the number of minutes to skip.
                             If the number of minutes is less than zero, it throws an error.
                             If the number of minutes is equal to zero, it throws an error.
                             Otherwise, it calls the "skipMinutes" method from the "Methods" class to update the current time by skipping the given number of minutes.
                             @param inputs The user input as an array of strings.
                             @param output The output stream to write the results of the command.
                             @throws IOException if there is an error writing to the output stream.
                             */
                            case "SkipMinutes":
                                try{
                                    Methods.commandWriter(inputs,output);
                                    if (inputs.length != 2) {
                                        System.out.println("ERROR: Erroneous command!");
                                        writer.write("ERROR: Erroneous command!\n");
                                    }
                                    else {
                                        int minutes = Integer.parseInt(inputs[1]);
                                        if(minutes < 0){
                                            System.out.println("ERROR: Time cannot be reversed!");
                                            writer.write("ERROR: Time cannot be reversed!\n");
                                        }else if(minutes == 0){
                                            System.out.println("ERROR: There is nothing to skip!");
                                            writer.write("ERROR: There is nothing to skip!\n");
                                        }else{
                                            currentTime=Methods.skipMinutes(currentTime,minutes);
                                        }
                                    }
                                }catch (Exception e){
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                break;
                            case "Nop":
                                break;
                            /**
                             Executes the "Add" command to add a new smart device to the system.
                             @param inputs the user inputs containing the command and device information
                             @param SmartDevices the list of current smart devices in the system
                             @param currentTime the current time of the system
                             @param output the output stream to write the result of the command
                             */
                            case "Add":
                                Methods.commandWriter(inputs,output);
                                if(inputs[1].equals("SmartPlug")){
                                    Methods.addSmartPlug(SmartDevices, inputs , currentTime,output);;
                                }
                                else if(inputs[1].equals("SmartCamera")){
                                    Methods.addSmartCamera(SmartDevices, inputs,output);
                                }
                                else if(inputs[1].equals("SmartLamp")){
                                    Methods.addSmartLamp(SmartDevices, inputs,output);
                                }
                                else if(inputs[1].equals("SmartColorLamp")){
                                    Methods.addSmartColorLamp(SmartDevices, inputs,output);
                                }
                                break;
                            /**
                             Executes the "Remove" command to remove a smart device from the list of smart devices.
                             @param inputs an array of Strings representing the user inputs
                             @param SmartDevices the list of current smart devices in the system
                             @param output a BufferedWriter object to write the output to a file
                             */
                            case "Remove":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 2) {
                                    Methods.removeDevice(SmartDevices, inputs[1],output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                break;
                            /**
                             Sets the switch time for a specified smart device.
                             @param inputs an array of string inputs containing the device ID and the switch time
                             @param currentTime the current time
                             @param SmartDevices the list of current smart devices in the system
                             @param output the output stream to write the response to
                             */
                            case "SetSwitchTime":
                                Methods.commandWriter(inputs,output);
                                Methods.setSwitchTime(SmartDevices, inputs, currentTime,output);
                                break;
                            /**
                             This case handles the "Switch" command. It toggles the state of a device on or off at a specific time, according to the input parameters.
                             If the input parameters are incorrect or incomplete, it outputs an error message.
                             @param inputs The array of input parameters.
                             @param currentTime The current time of the system.
                             @param SmartDevices The list of smart devices in the system.
                             @param output The output stream to write the results.
                             @throws IOException If there is an error writing to the output stream.
                             */
                            case "Switch":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 3) {
                                    Methods.toggleDevice(SmartDevices, inputs[2], inputs[1], currentTime,output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                break;
                            /**
                             Executes the ChangeName command with the given inputs.
                             @param inputs the command and its parameters as an array of Strings.
                             @param output the PrintStream object to output the result/error messages.
                             */
                            case "ChangeName":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 3) {
                                    Methods.renameDevice(SmartDevices, inputs[1], inputs[2],output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                break;
                            /**
                             This case handles the "PlugIn" command which adds a new device to the SmartHomeSystem with the given name and power consumption value.
                             If the command format is not correct, an error message is displayed.
                             @param inputs the array of inputs provided by the user
                             @param output the BufferedWriter object used to write output to the log file
                             @throws IOException if an I/O error occurs
                             */
                            case "PlugIn":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 3) {
                                    Methods.plugInDevice(SmartDevices, inputs[1], Integer.parseInt(inputs[2]), currentTime,output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                break;
                            /**
                             This method is called when the "PlugOut" command is entered. It writes the command to the output file and checks if the input is in the correct format. If the input is correct, it calls the "plugOutDevice" method from the "Methods" class to remove the device with the given name from the list of currently plugged in devices. If the input is not correct, it prints an error message to the console and output file.
                             @param inputs an array of Strings that contains the command and the name of the device to be plugged out
                             @param output a BufferedWriter object used to write to the output file
                             */
                            case "PlugOut":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 2) {
                                    Methods.plugOutDevice(SmartDevices, inputs[1], currentTime,output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                break;
                            /**
                             * Sets the kelvin value of a smart color lamp.
                             * @param inputs array of command inputs, where inputs[1] is the name of the device, and inputs[2] is the kelvin value
                             @param output a BufferedWriter object used to write to the output file
                             */
                            case "SetKelvin":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 3) {
                                    Methods.setKelvin(SmartDevices, inputs[1], Integer.parseInt(inputs[2]),output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                /**
                                 * Sets the brightness value of a smart color lamp.
                                 * @param inputs array of command inputs, where inputs[1] is the name of the device, and inputs[2] is the kelvin value
                                 @param output a BufferedWriter object used to write to the output file
                                 */
                                break;
                            case "SetBrightness":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 3) {
                                    Methods.setBrightness(SmartDevices, inputs[1], Integer.parseInt(inputs[2]),output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                /**
                                 * Sets the colorcode value of a smart color lamp.
                                 * @param inputs array of command inputs, where inputs[1] is the name of the device, and inputs[2] is the kelvin value
                                 @param output a BufferedWriter object used to write to the output file
                                 */
                                break;
                            case "SetColorCode":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 3) {
                                    Methods.setColorCode(SmartDevices, inputs[1], inputs[2],output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                /**
                                 * Sets the White value of a smart color lamp.
                                 * @param inputs array of command inputs, where inputs[1] is the name of the device, and inputs[2] is the kelvin value
                                 @param output a BufferedWriter object used to write to the output file
                                 */
                                break;
                            case "SetWhite":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 4) {
                                    Methods.setWhite(SmartDevices, inputs[1], Integer.parseInt(inputs[2]),
                                            Integer.parseInt(inputs[3]),output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                                /**
                                 * Sets the color value of a smart color lamp.
                                 * @param inputs array of command inputs, where inputs[1] is the name of the device, and inputs[2] is the kelvin value
                                 @param output a BufferedWriter object used to write to the output file
                                 */
                                break;
                            case "SetColor":
                                Methods.commandWriter(inputs,output);
                                if (inputs.length == 4) {
                                    Methods.setColor(SmartDevices, inputs[1], Integer.parseInt(inputs[3]), inputs[2],output);
                                }
                                else {
                                    System.out.println("ERROR: Erroneous command!");
                                    writer.write("ERROR: Erroneous command!\n");
                                }

                                break;
                            /**
                             Generates a Z report for a list of Smart Devices.
                             @param Devices An ArrayList of SmartDevice objects representing the devices to generate the Z report for.
                             @param currentTime A LocalDateTime object representing the current date and time.
                             @param output A String representing the file path to output the Z report to.
                             */
                            case "ZReport":
                                try{
                                    Methods.commandWriter(inputs,output);
                                    Methods.ZReport(SmartDevices,currentTime,output);
                                    break;
                                }catch (IOException I){
                                }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

