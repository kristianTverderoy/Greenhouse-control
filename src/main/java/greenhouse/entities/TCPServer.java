package greenhouse.entities;


import java.net.ServerSocket;
import java.util.List;

public class TCPServer {
    private ServerSocket mainServerSocket;
    private ServerSocket listenerServerSocket;
    private int port;
    private List<ServerSubscriber> serverSubscribers;



    public TCPServer(){

    }

    public void setUpServer(){
        try {

            ServerSocket serverSocket = new ServerSocket();

        } catch(ServerSetupException e) {

        }
    }



}
