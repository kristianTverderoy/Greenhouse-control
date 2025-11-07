package greenhouse.entities;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TCPServer {

  private final int port;
  private final List<ServerSubscriber> serverSubscribers = new CopyOnWriteArrayList<>();
  private final List<GreenHouse> greenHouses = new CopyOnWriteArrayList<>();
  private volatile boolean isOn = false;
  private ServerSocket serverSocket;

  /**
   * Constructs a TCP server that will listen on the specified port.
   *
   * @param port the port number on which the server will listen for connections
   */
  public TCPServer(int port){
    this.port = port;

  }

  /**
   * Starts the server and begins accepting client connections.
   * This method runs in a loop while the server is on, accepting and handling clients.
   * The server socket is closed when the server stops or an exception occurs.
   */
  public void run() {

    try {
      serverSocket = new ServerSocket(port);

      while (isOn) {
        Socket clientSocket = serverSocket.accept();
        handleClient(clientSocket);
      }
    } catch (IOException e) {
      if (isOn) {
        e.printStackTrace();
      }
    } finally {
      closeServer();
    }
  }

  /**
   * Handles communication with a connected client.
   * Reads messages from the client and processes them until the client disconnects
   * or sends an exit command.
   *
   * @param clientSocket the socket connection to the client
   */
  private void handleClient(Socket clientSocket) {

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

      boolean clientConnected = true;
      while (isOn && clientConnected && !clientSocket.isClosed()) {
        String message = reader.readLine();
        if (message == null || message.equalsIgnoreCase("exit")) {
          clientConnected = false;
        } else {
          handleClientRequest(message, writer);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();

    } finally {
      try {
        clientSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Processes a client request and sends a response back to the client.
   *
   * @param message the message received from the client
   * @param writer the writer used to send responses back to the client
   */
  private void handleClientRequest(String message, BufferedWriter writer){
    try {
      writer.write("Server received: " + message);
      writer.newLine();
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Adds a subscriber to receive notifications from the server.
   *
   * @param subscriber the subscriber to be added
   */
  public void addSubscriber(ServerSubscriber subscriber){
    this.serverSubscribers.add(subscriber);
  }

  /**
   * Notifies all registered subscribers by calling their update method.
   */
  public void notifySubscribers(){
    for (ServerSubscriber subscriber : this.serverSubscribers){
      subscriber.update();
    }
  }

  /**
   * Updates the server state to "on", allowing it to start accepting connections in the run loop.
   */
  public void startServer(){
      this.isOn = true;
    }

    /**
     * Updates the server state to "off", stopping it from accepting further connections in the run loop.
     */
    public void stopServer(){
      this.isOn = false;
      closeServer();
    }

    private void closeServer(){
      try {
        if (serverSocket != null && !serverSocket.isClosed()) {
          serverSocket.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


}
