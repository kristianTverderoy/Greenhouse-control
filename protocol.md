# Protocol

This document describes the protocol designed
for communication between clients (Control nodes)
and servers (sensor and actuator nodes).

## Terminology
- **Client**: A device that sends requests to the server to create greenhouses, retrieve sensor 
data or control appliances.
- **Server**: A device that hosts sensors and appliances, responding to client requests.
- **GreenHouse**: A virtual representation of a physical greenhouse containing sensors and appliances.
- **Sensor**: A device that measures environmental parameters (e.g., temperature, humidity).
- **Appliance**: A device that can be controlled (e.g., heater, sprinkler).

## Underlying transport protocol

The underlying transport protocol used for communication is TCP (Transmission Control Protocol).

## Port number
The default port number for the server is `5000`.

## Architechture


## Flow of information


## Protocol type


## Constants


## Error types

## Scenario

## Handling network errors
Network errors are printed out to the console for debugging purposes.

## Security mechanisms
The communication protocol incorporates encryption to 
ensure data confidentiality and integrity.