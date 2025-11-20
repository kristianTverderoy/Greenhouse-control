# Communication protocol
This document describes the protocol designed
for communication between clients (Control nodes)
and servers (holding greenhouses with sensors and appliances).


## Terminology
- **Client**: A device that sends requests to the server to create greenhouses, retrieve sensor
data or control appliances.
- **Server**: A device that hosts sensors and appliances, responding to client requests.
- **GreenHouse**: A virtual representation of a physical greenhouse containing sensors and appliances.
- **Sensor**: A device that measures environmental parameters (e.g., temperature, humidity).
- **Appliance**: A device that can be controlled (e.g., heater, sprinkler). (renamed from actuator)
-**DTO**: Data Transfer Object


## Underlying transport protocol
The underlying transport protocol used for communication is TCP (Transmission Control Protocol). This protocol was chosen, due to the nature of how the program should work. There is no need for continual information flow, like in a video stream. Instead we want all messages to be reliably sent to the server and handled.


## Port number
The default port number for the server is `5000`. However, this port number can be changed to anything by passing it as an argument while starting the server, as long as it is over ‘1024’ and under ‘65535’.


## Architechture
The client gets commands to use, and sends messages to change the state of the server/greenhouses or to get information back. The server handles these messages and makes changes / replies with information that was requested.
Due to the connection only being between the server and client, they are the only two actors.

## Flow of information
A string is encrypted and sent between the client and server. It is decrypted on both ends also. The message protocol used is TCP.

## Application layer protocol

The application layer protocol that has been implemented is: The client side program takes a string input from the terminal, encrypts it with the AES algorithm and sends it to the server it connects to based on port connection. After the string has been sent over a TCP connection to the server, it is decrypted and interpreted as a command in the MenuSystem in the server. There are mainly two types of commands in the MenuSystem: navigation and control. 

The navigation commands are used to navigate between the different submenus in the MenuSystem, and are passed without arguments with the format “command”.
The control commands are used to control the greenhouses in the server, whether by creating new greenhouses or controlling the sensors and appliances in the greenhouses. They are passed with arguments and have the format “command -argument”. The “-” before the argument are used to signify the start of the argument part of the command when the server interprets the message.

After the string passed to the client is decrypted it is simply printed out to the terminal. In that way the client protocol is simple. It takes whatever is written in the terminal, encrypts it and sends it to the server. It then listens to the server, decrypts whatever is sent to it and prints it out in the terminal.

One flaw in this system is with the AES encryption. Due to inexperience in implementing encryption, the design choice was to make a secret key beforehand, which both the client and server holds statically. If this key is found, the encryption and decryption is compromised. However, it is still better than sending String unencrypted at all.


## Protocol type
The system uses a connection-oriented protocol due to the TCP connection, and uses a state-full protocol, which is a protocol which remembers information from previous client requests.



## Constants
The class Clock:
ArrayList<> speeds, which holds on the int variables NORMAL, MODERATE and FAST which determines how often the simulation of sensor data should update. The values 10, 5 and 1 represents how many minutes between each update (can be easily changed to seconds in code if needed).

The class Sensor and Appliances:
String type is used when creating or handling which type of sensor/appliance is being used.


## Error types
The protocol defines several custom exceptions:
-**SensorNotAddedToGreenhouseException**: This exception is thrown when a client tries to add a sensor to the greenhouse and it for whatever reason doesn't work. 

-*ApplianceNotAddedToGreenhouseException*: This exception is thrown when a client tries to add an appliance to the greenhouse and it for some reason doesn’t work.

-*NoExistingGreenhouseException*: This exception is thrown if a client tries to do something with a greenhouse, but it doesn’t actually exist.


## Scenario
In a scenario there is a client Lise with two greenhouses she wants to monitor. One of the greenhouses has sensors that monitor light, temperature and ph, and appliances that affect light temperature and ph. The other greenhouse has sensors that monitor humidity and moisture, and appliances that affect humidity and moisture. She wants to monitor these two greenhouses simultaneously. 

She runs the ServerApp to start a TCPServer and the ClientApp to start a TCPClient, both in different terminals.

She runs the “greenhouses” command to get to the submenu that controls the greenhouses. She runs the “addgreenhouse” command two times to add the two greenhouses she wants to monitor. To check that she has added the greenhouses she runs the “listgreenhouses” command and the following is printed in the terminal:

Available greenhouses:
greenhouse 0
greenhouse 1

To get into the submenu that controls the first greenhouse she runs the command  “0” to control the greenhouse with id 0. To add the desired sensors she runs the commands “addsensor -lightsensor”, “addsensor -temperaturesensor” and  “addsensor -phsensor”.

To create the desired actuators she runs the commands “addappliance -lamp”, “addappliance -aircondition” and “addappliance -limer”.
Then she runs the command “monitor” to receive continuous updates from greenhouse 0.

She also wants to monitor greenhouse 1, so she runs ClientApp again in another terminal, and navigates to the submenu of greenhouse 1 by running the commands “greenhouse” and “1”. 
In this submenu she adds her desired sensors by running the commands “addsensor -humiditysensor” and “addsensor -moisturesensor”. She also adds her desired actuators by running the commands “addappliance -humidifier” and “addsensor -sprinkler”.
She then runs the command “monitor” to get continuous updates from greenhouse 1.

She now has two terminals open that monitor two different greenhouses. If she wants to change the values in any of the greenhouses she can stop the monitoring with the “stop” command and activate an appliance with the “toggle -applianceID” command.


We can expand the scenario by adding a second customer Stian with his own greenhouse to monitor. Since he is unconnected with Lise he shouldn't have access to her greenhouses, and vice versa. He can then activate the ServerApp and ClientApp while passing the argument “6000”. 6000 is then the port that the TCPServer and TCPClient connect through. He can then command his own greenhouse without affecting or gaining access to Lise’s greenhouses.


## Handling network errors
Network errors are printed out to the console for debugging purposes.
When the last user disconnects, the server saves all greenhouses.
The clients are able to reconnect with the server in the case of a temporary shutdown. After a shutdown the client can still type in the terminal and will be prompted to type reconnect. The client will try 5 times to reconnect, with exponential delay between each attempt. If no connection is established the user will be notified that max attempts have been reached and can then choose if they want to try again. 


## Security mechanisms
We encrypt messages using AES (Advanced Encryption Standard) method both ways between the server and client and decrypt before using the message. This method requires a key that both client and server hold as a static field variable. Refer to the application layer protocol description’s last paragraph for limitations with the security. 
