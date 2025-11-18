package greenhouse.entities;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;


/**
 * TCPClient class represents a client that connects to a TCP server,
 * sends and receives messages, and can subscribe to server updates.
 */
public class TCPClient {
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private static final String ENCRYPTION_ALGORITHM = "AES";
  private static final SecretKey SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode("m0VxcSPFs+2cuMUfh6tjWMj90eihSDGpc1cLr/B9e1Y="), ENCRYPTION_ALGORITHM);

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

      this.bufferedReader = bufferedReader;
      this.bufferedWriter = bufferedWriter;

      // Thread to listen for server messages:
      Thread listenerThread = new Thread(() -> {
        try {
          String serverMessage;
          while ((serverMessage = bufferedReader.readLine()) != null) {
            System.out.println(decryptMessage(serverMessage));
          }
        } catch (IOException e) {
          System.err.println("Connection to server lost: " + e.getMessage());
          }
        });
      listenerThread.setDaemon(true);
      listenerThread.start();

      // Main loop only sends messages to server.
      boolean disconnected = false;
      while (!disconnected) {

        String messageToSend = scanner.nextLine();
        String encryptedMessage = encryptMessage(messageToSend);

        bufferedWriter.write(encryptedMessage);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        if (messageToSend.equalsIgnoreCase("exit")){
          disconnected = true;
        }
      }
    } catch (Exception e){
      System.err.println("Could not connect to server: " + e.getMessage());
    }
  }

  /**
   * Encrypts a message using AES encryption.
   * The encrypted message is encoded in Base64 for safe transmission.
   *
   * @param message the plaintext message to encrypt
   * @return the encrypted message encoded in Base64, or original message if encryption fails
   */
  private String encryptMessage(String message) {
    if (message == null || message.isEmpty()) {
      return message;
    }

    try {
      Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
      byte[] encryptedBytes = cipher.doFinal(message.getBytes());
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
      System.err.println("Encryption failed: " + e.getMessage());
      return message;
    }
  }

  /**
   * Decrypts a Base64-encoded AES encrypted message.
   *
   * @param encryptedMessage the encrypted message in Base64 format
   * @return the decrypted plaintext message, or original message if decryption fails
   */
  private String decryptMessage(String encryptedMessage) {
    if (encryptedMessage == null || encryptedMessage.isEmpty()) {
      return encryptedMessage;
    }

    try {
      Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
      byte[] decodedBytes = Base64.getDecoder().decode(encryptedMessage);
      byte[] decryptedBytes = cipher.doFinal(decodedBytes);
      return new String(decryptedBytes);
    } catch (Exception e) {
      System.err.println("Decryption failed: " + e.getMessage());
      return encryptedMessage;
    }
  }

  /**
   * Subscribes this client to receive updates from the server by sending a subscribe command.
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
