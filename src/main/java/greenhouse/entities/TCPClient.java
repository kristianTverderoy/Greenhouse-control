package greenhouse.entities;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


/**
 * TCPClient class represents a client that connects to a TCP server,
 * sends and receives messages, and can subscribe to server updates.
 */
public class TCPClient implements ServerSubscriber{
  private Socket socket;
  private InputStreamReader inputStreamReader;
  private OutputStreamWriter outputStreamWriter;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;

  /**
   * Constructs a TCPClient with the specified port, host, and server.
   */
  public TCPClient(){}

  //TODO: Remove sout statements when no longer necessary for debugging.
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

  /**
   * Subsribes this client to receive updates from the server by sending a subscribe command.
   * The server is expected to handle the subscription logic.
   */
  public void subscribeToServer(){
    try {
      bufferedWriter.write("subscribe");
      bufferedWriter.newLine();
      bufferedWriter.flush();

      String response = bufferedReader.readLine();
      System.out.println("Subscription response from server: " + response);
    } catch (IOException e){
      System.err.println("Failed to subscribe to server: " + e.getMessage());
    }
  }

  public void update(){

  }


}
