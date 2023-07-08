# Smart Home Automation System

This project aims to simulate a smart home automation system that controls various smart home accessories such as Smart Lamp, Smart Plug, and Smart Camera. The system implements Object-Oriented Programming (OOP) principles, specifically the four pillars of OOP: Inheritance, Polymorphism, Abstraction, and Encapsulation. The program allows users to control the devices and manipulate time flow.

## Introduction

As humans, we have always strived to make our lives easier by inventing technologies and automating tasks. Smart home accessories are one such invention that aims to automate and simplify various aspects of home living. In this project, you will be implementing a smart home automation system to make our lives more convenient, although you won't be paid for this project.

## OOP Principles

The project focuses on implementing good OOP design principles, specifically the four pillars of OOP:

1. Inheritance: The system utilizes inheritance to create different types of smart devices based on common attributes and behaviors.

2. Polymorphism: Polymorphism allows different smart devices to be treated interchangeably, enabling flexible and modular programming.

3. Abstraction: The system uses abstraction to define common interfaces and hide implementation details of the smart devices, allowing users to interact with them without worrying about internal complexities.

4. Encapsulation: Encapsulation is employed to encapsulate the internal state and behavior of the smart devices, ensuring data integrity and providing controlled access through well-defined interfaces.

## Smart Home Accessories

The system includes the following smart home accessories:

1. Smart Lamp: A lamp that allows adjustment of kelvin (color temperature) and brightness values. The kelvin value can range from 2000K to 6500K, and the brightness value ranges from 0% to 100%. The default values are 4000K and 100% respectively.

2. Smart Lamp with Color: Similar to the Smart Lamp, but with an additional color mode. It supports a color code range from 0x000000 to 0xFFFFFF. The lamp can operate in color mode or white mode, and the kelvin value is ignored in color mode. The default values are the same as the Smart Lamp (4000K, 100%).

3. Smart Plug: A wall plug that calculates total energy consumption. It tracks switch on/off events and plugging/unplugging events to calculate energy usage. The plug operates at a default voltage of 220 Volts and allows positive ampere values. The default state is switched off, and no device is initially plugged in.

4. Smart Camera: A camera that provides information about its storage usage. It tracks switch on/off events and calculates the amount of storage consumed per minute. The storage usage is measured in megabytes per minute.

## Program Functionality

The program accepts various commands to control the smart devices and manage time flow. Here are the available commands:

- SetInitialTime: Sets the initial time for the system. The format is YEAR-MONTH-DAY_HOUR:MINUTE:SECOND.

- SetTime: Sets the time to the specified value. The format is YEAR-MONTH-DAY_HOUR:MINUTE:SECOND.

- SkipMinutes: Skips the specified number of minutes.

- Nop: Skips time to the next switch event and performs the necessary switch operations.

- Adding Commands: Allows the addition of different smart devices to the system.

- Removing Commands: Removes a smart device from the system.

- Determining a Switch Time for Device: Sets a specific switch time for a device.

- Switching a Device to On or Off: Switches a device on or off.

- Changing the Name of a Device: Changes the name of a device.

- Plug In/Out Commands for Smart Plug: Plugs in or unplugs a device from the smart plug.

- Setting Kelvin, Brightness, and Color Code: Sets the kelvin, brightness, and color code values for smart lamps.

- Z Report: Prints the Z Report of the system.

## Error Handling

The program includes appropriate error handling to handle various scenarios. It validates input values, checks ranges, and ensures proper command execution. If an error occurs, the program provides an appropriate error message indicating the issue.

---



[BBM104_S23_PA2_v2 (1).pdf](https://github.com/SerhatAkbulut1/Assignment-5/files/11971699/BBM104_S23_PA2_v2.1.pdf)
