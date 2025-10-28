package greenhouse.entities.entrypoints;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class serverApp {
    private final static int serverPort = 5000;
    private final static String loopbackAddress = "127.0.0.1";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort, 10);

        } catch (NullPointerException e){


        } catch (IOException e){

        } catch (IllegalArgumentException e){
            System.err.println("ServerPort needs to be within the range 0-65535, " +
                    "but it was tried to set to: " + serverPort);
        }
    }
}
