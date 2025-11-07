package greenhouse.entities;

import java.net.Socket;

public class TCPClient implements ServerSubscriber{
  private Socket socket;
  private String host;
  private int port;
  private TCPServer server;

  public TCPClient(int port, String host, TCPServer server){
    this.host = host;
    this.port = port;
    this.server = server;
  }

  public void subscribeToServer(){
    this.server.addSubscriber(this);
  }

  public void update(){

  }


}
