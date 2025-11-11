package greenhouse.entities;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.MalformedInputException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import greenhouse.entities.sensors.*;

public class TCPServer {

  private final int port;
  private final List<ClientConnection> subscribedClients = new CopyOnWriteArrayList<>();
  private final List<GreenHouse> greenHouses = new CopyOnWriteArrayList<>();
  private volatile boolean isOn = false;
  private ServerSocket serverSocket;

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
      try {

        boolean clientConnected = true;
        while (isOn && clientConnected && !clientSocket.isClosed()) {
          String message = reader.readLine();
          if (message.equalsIgnoreCase("exit")) {
            clientConnected = false;
            removeSubscriber(clientSocket);
          } else {
            handleClientRequest(clientSocket, message, writer);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Processes a client request and sends a response back to the client.
   *
   * @param message the message received from the client
   * @param writer  the writer used to send responses back to the client
   * @param clientSocket the socket connection to the client
   */
  private void handleClientRequest(Socket clientSocket, String message, BufferedWriter writer) {
    try {
      if (message.equalsIgnoreCase("subscribe")) {
        boolean alreadySubscribed = subscribedClients.stream()
                .anyMatch(clientConnection -> clientConnection.socket().equals(clientSocket));
        if (!alreadySubscribed){
          addSubscriber(clientSocket, writer);
          writer.write("Subscribed successfully.");
        }

      } else {
        writer.write(handleMessage(message));
      }
      writer.newLine();
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * The various messages the server can handle from clients.
   * @param messageFromClient The request message sent from the client.
   * @return The response message to be sent back to the client.
   */
  private String handleMessage(String messageFromClient) {
    String message = messageFromClient.toLowerCase().trim();
    if (message.startsWith("addsensor")){
      try {
      addSensorsToGreenhouse(message);
      return "The sensor(s) was added succesfully";
      } catch (SensorNotAddedToGreenHouseException e){
        return "There was a problem adding the sensor to the greenhouse.";
      } catch (NoExistingGreenHouseException e){
        return "There is no greenhouse created yet";
      } catch (IOException e){
        return "Wrong input. Please follow the example";
      }

    }
    return switch (message) {
      case "status" -> "Server is running";
      case "ison" -> Boolean.toString(isOn);
      case "info" -> "Server information";
      case "help" -> "Available commands: Status, IsOn, Info, Help, AvailableSensors";
      case "availablesensors" -> "'HumiditySensor', 'LightSensor', 'MotionSensor', 'PHSensor', 'TemperatureSensor'" + "\n" 
      + "Example: 'AddSensors -HumiditySensor' this adds a humidity sensor to the greenhouse. \n \n Example for multiple sensors: 'AddSensors -LightSensor HumiditySensor MotionSensor'."
      + " This adds multiple sensors at the same time.";
      case "newgreenhouse" -> {
        if (greenHouses.isEmpty()){
            createNewGreenhouse();
            yield "Your greenhouse was created successfully";
          } else {
        yield "There is only support for having one greenhouse currently.";
        }
      }
      default -> "Unknown command";
    };
  }

  /**
   * Creates a new greenhouse and adds it to the threadsafe array list.
   */
  private void createNewGreenhouse(){
    GreenHouse gh = new GreenHouse();
    this.greenHouses.add(gh);
  }

  private void addSensorsToGreenhouse(String message) throws SensorNotAddedToGreenHouseException, NoExistingGreenHouseException, IOException {
    if (greenHouses.isEmpty()){
      throw new NoExistingGreenHouseException();
    }

    boolean splitSuccessful = false;
    try {
    String[] parts = message.split("-");
    String[] sensors = parts[1].split(" ");




    for (String sensor : sensors) {

        switch(sensor){
        case "humiditysensor" -> this.greenHouses.getFirst().addSensor(new HumiditySensor<>(
                this.greenHouses.getFirst().getNextAvailableSensorId()));

        case "lightsensor" -> this.greenHouses.getFirst().addSensor(new LightSensor<>(
                this.greenHouses.getFirst().getNextAvailableSensorId()));

        case "motionsensor" -> this.greenHouses.getFirst().addSensor(new MotionSensor<>(
                this.greenHouses.getFirst().getNextAvailableSensorId()));

        case "phsensor" -> this.greenHouses.getFirst().addSensor(new PHSensor<>(
                this.greenHouses.getFirst().getNextAvailableSensorId()));

        case "temperaturesensor" -> this.greenHouses.getFirst().addSensor(new TemperatureSensor<>(
                this.greenHouses.getFirst().getNextAvailableSensorId()));

          default -> throw new SensorNotAddedToGreenHouseException();
      }

      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IOException("The user did not follow the example for input");
    }
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
  public void startServer() {
    this.isOn = true;
  }

  /**
   * Updates the server state to "off", stopping it from accepting further connections in the run loop.
   */
  public void stopServer() {
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
  private void closeServer() {
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
