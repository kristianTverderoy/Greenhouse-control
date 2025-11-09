package greenhouse.entities.entrypoints;

import greenhouse.entities.TCPClient;

/**
 * The program's entrypoint where the purpose is to start a clientside communication with a server.
 */
public class ClientApp {



  /**
   * The ClientApp's main entry method.
   * <p>
   * Expected input order:
   * - no arguments: use default host 127.0.0.1 and default port 5000
   * - one argument: treated as the server port (Integer); host defaults to 127.0.0.1
   * - two arguments: first is the host address, second is the server port (Integer)
   *
   * @param args commandline arguments specifying host and/or port as described above
   */
  public static void main(String[] args) {
    final String defaultHostAddress = "127.0.0.1";
    final int defaultServerPort = 5000;
    final String host;
    final int serverPort;

    if (args.length == 1) {
      serverPort = Integer.parseInt(args[0]);
      host = defaultHostAddress;
    } else if (args.length == 2) {
      host = args[0];
      serverPort = Integer.parseInt(args[1]);
    } else {
      host = defaultHostAddress;
      serverPort = defaultServerPort;
    }

    TCPClient client = new TCPClient();
    client.connectToServer(host, serverPort);

  }
}
