package greenhouse.entities;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import greenhouse.entities.actuators.Actuator;
import greenhouse.entities.sensors.*;

public class TCPServer {

  private final int port;
  private final List<ClientConnection> subscribedClients = new CopyOnWriteArrayList<>();
  private final List<GreenHouse> greenHouses = new CopyOnWriteArrayList<>();
  private volatile boolean isOn = false;
  private ServerSocket serverSocket;
  private final MenuSystem menuSystem;


  /**
   * Constructs a TCP server that will listen on the specified port.
   *
   * @param port the port number on which the server will listen for connections
   */
  public TCPServer(int port) throws IllegalArgumentException {
    if (port < 0 || port > 65535) {
      throw new IllegalArgumentException("Port number must be between 0 and 65535.");
    }
    this.port = port;
    this.menuSystem = new MenuSystem(greenHouses, this);
  }

  /**
   * Starts the server and begins accepting client connections.
   * This method runs in a loop while the server is on, accepting and handling clients.
   * The server socket is closed when the server stops or an exception occurs.
   */

  //TODO: Remove sout statements when no longer necessary for debugging.
  public void run() {
    if (!isOn) {
      startServer();
    }

    try (ServerSocket ss = new ServerSocket(port)) {
      serverSocket = ss;


      while (isOn) {
        System.out.println("Server is listening on port " + port);
        Socket clientSocket = ss.accept();
        System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
        new Thread(() -> handleClient(clientSocket)).start(); // Handle each client in a separate thread
      }
    } catch (IOException e) {
      if (isOn) {
        e.printStackTrace();
      }
    } finally {
      // try-with-resources already closed the socket; clear the field reference for clarity
      serverSocket = null;
    }
  }

  /**
   * Handles communication with a connected client.
   * Reads messages from the client and processes them until the client disconnects
   * or sends an exit command.
   * <p>
   * Upon receiving an exit command or a null message, the client is removed from the list of subscribed clients.
   *
   * @param clientSocket the socket connection to the client
   */
  private void handleClient(Socket clientSocket) {

    try (clientSocket; BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
      writer.write("Welcome to the Greenhouse Server!");
      writer.newLine();


        while (isOn && !clientSocket.isClosed()) {
          menuSystem.showStartMenu(writer);
          String message = reader.readLine(); // message is null if client abruptly disconnects

          if (message == null || message.equalsIgnoreCase("exit")) {
            removeSubscriber(clientSocket);
          } else {
            handleClientRequest(clientSocket, message, reader, writer);
          }
        }
      } catch (IOException e) {
      // Client disconnected abruptly (e.g., connection reset)
      removeSubscriber(clientSocket);
      System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
    }
  }

  /**
   * Processes a client request.
   * @param clientSocket The socket connection to the client.
   * @param message The message received from the client.
   * @param reader The BufferedReader used to read messages from the client.
   * @param writer The BufferedWriter used to send responses back to the client.
   * @throws IOException If an I/O error occurs while handling the command.
   */
  private void handleClientRequest(Socket clientSocket, String message, BufferedReader reader, BufferedWriter writer) throws IOException{
    String command = message.toLowerCase().trim();

    switch (command) {
      case "greenhouses" -> menuSystem.handleGreenhousesMenu(reader, writer);
      case "subscribe" -> handleSubscribe(clientSocket, writer);
      case "help" -> menuSystem.helpMessage(writer);
      default -> {
        writer.write("Did not recognize command. Type 'help' for available commands.");
        writer.newLine();
        writer.flush();
      }
    }
  }

  /**
   * The various messages the server can handle from clients.
   *
   * @param messageFromClient The request message sent from the client.
   * @return The response message to be sent back to the client.
   */
  private String handleMessage(String messageFromClient) {
    String message = messageFromClient.toLowerCase().trim();
    if (message.startsWith("addsensor")) {
      try {
        addSensorsToGreenhouse(message);
        return "The sensor(s) was added succesfully";
      } catch (SensorNotAddedToGreenHouseException e) {
        return "There was a problem adding the sensor to the greenhouse.";
      } catch (NoExistingGreenHouseException e) {
        return "There is no greenhouse created yet";
      } catch (IOException e) {
        return "Wrong input. Please follow the example";
      }

    }
//    if (message.startsWith("sensorreading")) {
//      return handleSensorReadingRequest(message);
//    }

    //:TODO: Create better cases.

/*
    if (message.startsWith("actuatorreading")){
      return handleActuatorReadingRequest(message);
    }
*/

    return switch (message) {
      case "status" -> "Server is running";
      case "getallgreenhouses" -> getListOfAllGreenHouses();
      case "ison" -> Boolean.toString(isOn);
      case "info" -> "Server information";
      case "help" -> "Available commands: Status, GetAllGreenHouses, IsOn, Info, Help, AvailableSensors";
      case "availablesensors" -> "'HumiditySensor', 'LightSensor', 'PHSensor', 'TemperatureSensor'"
              + "Example: 'AddSensors -0 HumiditySensor ' this adds a humidity sensor to greenhouse nr.0."
              + " \n \n Example for multiple sensors: 'AddSensors -2 -LightSensor HumiditySensor TemperatureSensor'."
              + " This adds multiple sensors at the same time, to Greenhouse nr.2.";
      case "newgreenhouse" -> createNewGreenhouse();
      default -> "Unknown command";
    };
  }

  /**
   * Handles a sensor reading request from the client and returns sensor data.
   *
   * <p>This method parses the command string to extract the greenhouse ID and sensor specification,
   * then retrieves the requested sensor information.</p>
   *
   * @param messageFromClient the command string in the format: "sensorReading -<greenhouseId> -<sensorId|'a'>"
   *                            <p>Examples:</p>
   *                            <ul>
   *                              <li>"sensorReading -0 -2" - reads data from sensor with ID 2 in greenhouse 0</li>
   *                              <li>"sensorReading -1 -a" - reads data from all sensors in greenhouse 1</li>
   *                            </ul>
   * @return a string containing the sensor reading(s), or "Sensor not found with ID: <id>" if the sensor doesn't exist.
   */
  //TODO: guard condition if no greenhouses
  public String handleSensorReadingRequest(String messageFromClient) throws NoExistingGreenHouseException, IOException, IllegalArgumentException {
    if (greenHouses.isEmpty()) {
      throw new NoExistingGreenHouseException();
    }
    try {
      String[] parts = messageFromClient.split("-");
      GreenHouse greenHouse = greenHouses.get(Integer.parseInt(parts[2].trim())); //This represents the greenhouse with the ID specified by the client.
      parts[1] = parts[1].trim().toLowerCase(); //This represents the sensor(s)

      if (parts[1].equals("a")) {
        return greenHouse.getAllSensorsInformation();
      } else {
        try {
          Sensor<?> sensor = greenHouse.getSensor(Integer.parseInt(parts[1]));
          if (sensor != null) {
            return sensor.toString();
          } else {
            return "Sensor not found with ID: " + parts[1];
          }
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid sensor ID format.");
        }
      }

    } catch (IndexOutOfBoundsException e) {
      throw new IOException("The user did not follow the example for input");
    }
  }

  /**
   * Receive actuator status data from any sensor node.
   */ /*
  private String handleActuatorReadingRequest(String message) {
    String[] parts = message.split("-");
    GreenHouse greenHouse = greenHouses.get(Integer.parseInt(parts[1].trim()));
    parts[2] = parts[2].trim().toLowerCase();

    if (parts[2].equals("a")) {
      return greenHouse.getAllActuatorsInformation();
    } else {
      Actuator actuator = greenHouse.getActuator(Integer.parseInt(parts[2]));
      if (actuator != null) {
        return actuator.toString();
      } else {
        return "Actuator not found with ID: " + parts[2];
      }
    }


  }*/


  /**
   * Creates a new greenhouse and adds it to the threadsafe array list.
   */
  public String createNewGreenhouse() {
    try {
      GreenHouse gh;
      if (!greenHouses.isEmpty()) {
        gh = new GreenHouse(greenHouses.getLast().getID() + 1);

      } else {
        gh = new GreenHouse(0);
      }
      this.greenHouses.add(gh);
      return "GreenHouse created successfully.";
    } catch (Exception e) {
      return "There was an error creating a new green house. Please try again.";
    }

  }

  /**
   * Adds specified sensors to a selected greenhouse.
   * @param message the command string in the format: "addSensor -<greenhouseId> <sensor1> <sensor2>..."
   *                Example: "addSensor -0 HumiditySensor TemperatureSensor"
   *                This adds a HumiditySensor and TemperatureSensor to greenhouse with ID 0.
   * @throws SensorNotAddedToGreenHouseException if the sensor type is invalid
   * @throws NoExistingGreenHouseException if there are no existing greenhouses in the list or the greenhouse
   * ID does not exist.
   * @throws IOException if the message format is incorrect or cannot be parsed.
   */
  public void addSensorsToGreenhouse(String message) throws SensorNotAddedToGreenHouseException, NoExistingGreenHouseException, IOException {
    if (greenHouses.isEmpty()){
      throw new NoExistingGreenHouseException();
    }

    boolean splitSuccessful = false;
    try {
      String[] parts = message.split("-");
      String[] sensors = parts[1].split(" ");
      int greenhouseId = Integer.parseInt(parts[2].trim());


      GreenHouse targetGreenhouse = greenHouses.stream()
              .filter(gh -> gh.getID() == greenhouseId)
              .findFirst()
              .orElseThrow(NoExistingGreenHouseException::new);

      for (String sensor : sensors) {

      switch(sensor){
        case "humiditysensor" -> targetGreenhouse.addHumiditySensor();

        case "lightsensor" -> targetGreenhouse.addLightSensor();

        case "phsensor" -> targetGreenhouse.addPhSensor();

        case "temperaturesensor" -> targetGreenhouse.addTemperatureSensor();

        case "moisturesensor" -> targetGreenhouse.addMoistureSensor();

        case "nitrogensensor" -> targetGreenhouse.addNitrogenSensor();

          default -> throw new SensorNotAddedToGreenHouseException();
        }

      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IOException("The user did not follow the example for input");
    }
  }

  /**
   * Returns the name of all the green houses as one string
   *
   * @return name of all the green houses as one string
   */
  public String getListOfAllGreenHouses() {
    StringBuilder result = new StringBuilder();
    for (GreenHouse greenHouse : greenHouses) {
      result.append("Greenhouse ").append(greenHouse.getID()).append(", ");
    }
    return result.toString().trim();
  }

  private void handleSubscribe(Socket clientSocket, BufferedWriter writer) throws IOException {
    boolean alreadySubscribed = subscribedClients.stream()
            .anyMatch(cc -> cc.socket().equals(clientSocket));

    if (!alreadySubscribed) {
      addSubscriber(clientSocket, writer);
      writer.write("Subscribed successfully.");
    } else {
      writer.write("Already subscribed.");
    }
    writer.newLine();
    writer.flush();
  }


  /**
   * Adds a subscriber to receive notifications from the server.
   *
   * @param subscriber the subscriber to be added
   */
  public void addSubscriber(Socket subscriber, BufferedWriter writer) {
    this.subscribedClients.add(new ClientConnection(subscriber, writer));
  }

  /**
   * Removes a subscriber from the subscriber list.
   *
   * @param subscriber the subscriber to be removed
   */
  private void removeSubscriber(Socket subscriber) {
    this.subscribedClients.removeIf(
            clientConnection -> clientConnection.socket().equals(subscriber));
  }

  /**
   * Notifies all registered subscribers with the provided update message.
   * The message is sent to each subscriber's writer, and
   * is sent over the network connection.
   * <p>
   * If a notification fails to be sent to a subscriber,
   * it's assumed the subscriber is no longer reachable and is removed from the list.
   */
  public void notifySubscribers(String updateMessage) {
    subscribedClients.removeIf(clientConnection -> {
      try {
        clientConnection.writer.write(updateMessage);
        clientConnection.writer.newLine();
        clientConnection.writer.flush();
        return false; // Notification sent successfully, i.e do not remove subscriber.
      } catch (IOException e) {
        return true; // There was an error in notifying the subscriber, so we remove it.
      }
    });
  }

  /**
   * Updates the server state to "on", allowing it to start accepting connections in the run loop.
   */
  public synchronized void startServer() {
    this.isOn = true;
  }

  /**
   * Updates the server state to "off", stopping it from accepting further connections in the run loop.
   */
  public synchronized void stopServer() {
    this.isOn = false;
    subscribedClients.forEach(clientConnection -> {
      try {
        clientConnection.socket().close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    subscribedClients.clear();
    closeServer();
  }

  /**
   * Closes the server socket to stop accepting new connections.
   */
  private synchronized void closeServer() {
    try {
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the state the server is in, either on or off.
   *
   * @return true if the server is on, false otherwise.
   */
  public boolean isOn() {
    return isOn;
  }


  /**
   * Record holding information about a client connection.
   */
  private record ClientConnection(Socket socket, BufferedWriter writer) {

    /**
     * Gets the socket connection to the client.
     *
     * @return the client socket
     */
    @Override
    public Socket socket() {
      return socket;
    }

    /**
     * Gets the writer used to send messages to the client.
     *
     * @return the buffered writer
     */
    @Override
    public BufferedWriter writer() {
      return writer;
    }
  }


}
