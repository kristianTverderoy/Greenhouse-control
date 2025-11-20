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
The underlying transport protocol used for communication is TCP (Transmission Control Protocol).


## Port number
The default port number for the server is `5000`. However, this port number can be changed to anything by passing it as an argument while starting the server, as long as it is over ‘1024’ and under ‘65535’.


## Architechture
The client gets commands to use, and sends messages to change the state of the server/greenhouses or to get information back. The server handles these messages and makes changes / replies with information that was requested.
Due to the connection only being between the server and client, they are the only two actors.

## Flow of information
A string is sent between the client and server after being encrypted. The message protocol used is TCP.



## Protocol type
The system uses connection-oriented due to the TCP connection, and uses a state-full protocol, which is a protocol which remembers information from previous client requests.



## Constants
The class Clock:
ArrayList<> speeds, which holds on the int variables NORMAL, MODERATE and FAST which determines how often the simulation of sensor data should update. The values 10, 5 and 1 represents how many minutes between each update (can be easily changed to seconds in code if needed).

The class Sensor and Appliances:
String type is used when creating or handling which type of sensor/appliance is being used.


## Error types
The protocol defines several custom exceptions:
-**SensorNotAddedToGreenhouseException**: This exception is thrown when a client tries to add a sensor to the greenhouse and it for whatever reason doesn't work. 


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


## Security mechanisms
We encrypt messages using AES (Advanced Encryption Standard) method both ways between the server and client and decrypt before using the message. This method requires a key that is shared between clients and servers.
