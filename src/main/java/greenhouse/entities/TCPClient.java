package greenhouse.entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient implements ServerSubscriber{
  private Socket socket;
  private String host;
  private int port;
  private TCPServer server;
  private InputStreamReader inputStreamReader;
  private OutputStreamWriter outputStreamWriter;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;

  public TCPClient(int port, String host, TCPServer server){
    this.host = host;
    this.port = port;
    this.server = server;
  }

  public void connectToServer(String host, int port){
    try (Socket socket = new Socket(host, port);
         InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
         OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
         Scanner scanner = new Scanner(System.in)) {

      this.socket = socket;
      this.inputStreamReader = inputStreamReader;
      this.outputStreamWriter = outputStreamWriter;
      this.bufferedReader = bufferedReader;
      this.bufferedWriter = bufferedWriter;

      boolean disconnected = false;
      while (!disconnected) {

        String messageToSend = scanner.nextLine();
        bufferedWriter.write(messageToSend);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        String messageReceived = bufferedReader.readLine();
        System.out.println("Message from server: " + messageReceived);

        if (messageToSend.equalsIgnoreCase("exit")){
          disconnected = true;
        }
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public void subscribeToServer(){
    this.server.addSubscriber(this);
  }

  public void update(){

  }


}
