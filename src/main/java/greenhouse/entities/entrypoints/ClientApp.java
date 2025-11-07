package greenhouse.entities.entrypoints;

import greenhouse.entities.TCPClient;

/**
 * The program's entrypoint where the purpose is to start a clientside communication with a server.
 */
public class ClientApp {



  /**
   * The ClientApp's main entry method.
   *
   * @param args The commandline arguments to pass upon starting the program, indicating which
   *             server port to connect to.
   */
  public static void main(String[] args) {
    final String loopbackAddress = "127.0.0.1";
    final int defaultServerPort = 5000;

    TCPClient client = new TCPClient();
    client.connectToServer(loopbackAddress,defaultServerPort);

  }
}
