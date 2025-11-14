package greenhouse.entities.entrypoints;

import greenhouse.entities.TCPServer;

/**
 * The program's entrypoint where the purpose is to start a TCPServer and allow communication
 * with clients connecting to the server.
 */
public class ServerApp {
  private static final int serverPort = 5000;
  private static final String loopbackAddress = "127.0.0.1";

  /**
   * The ServerApp's main entry point.
   *
   * @param args The commandline arguments to pass upon starting the program.
   */
  public static void main(String[] args) {
    try {
//      ServerSocket serverSocket = new ServerSocket(serverPort, 10);
      TCPServer server = new TCPServer(serverPort);
      server.run();
    } catch (IllegalArgumentException e){
      System.err.println("ServerPort needs to be within the range 0-65535, "
              + "but it was tried to set to: " + serverPort);

    } catch (Exception e){
      System.err.println("There was an error while trying to start the server: " + e.getMessage());

    }
  }
}
