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
  private Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private volatile boolean isConnected = false;
  private volatile boolean shouldReconnect = true;
  private static final int MAX_RECONNECT_ATTEMPTS = 5;
  private static final int INITIAL_DELAY_MS = 1000;
  private static final String ENCRYPTION_ALGORITHM = "AES";
  private static final SecretKey SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode("m0VxcSPFs+2cuMUfh6tjWMj90eihSDGpc1cLr/B9e1Y="), ENCRYPTION_ALGORITHM);

  /**
   * Constructs a TCPClient with the specified port, host, and server.
   */
  public TCPClient(){}

  public void connectToServer(String host, int port) {
      Scanner scanner = new Scanner(System.in);

      try {
          socket = new Socket(host, port);
          bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
          isConnected = true;
      } catch (IOException e) {
          System.err.println("Could not connect to server: " + e.getMessage());
          isConnected = false;
      }

      Thread listenerThread = createAndStartListenerIfConnected();

      while (shouldReconnect) {
          String messageToSend = scanner.nextLine();

          if (messageToSend.equalsIgnoreCase("reconnect")) {
              isConnected = false;
              closeResources();

              if (attemptReconnection(host, port)) {
                  listenerThread = createAndStartListenerIfConnected();
                  continue;
              } else {
                  System.out.println("Failed to reconnect. Type 'reconnect' to try again or 'exit' to quit.");
                  continue;
              }
          }

          if (messageToSend.equalsIgnoreCase("exit")) {
              shouldReconnect = false;
              isConnected = false;
              closeResources();
              return;
          }

          if (isConnected) {
              try {
                  String encryptedMessage = encryptMessage(messageToSend);
                  bufferedWriter.write(encryptedMessage);
                  bufferedWriter.newLine();
                  bufferedWriter.flush();
              } catch (IOException e) {
                  System.err.println("Failed to send message: " + e.getMessage());
                  isConnected = false;
                  closeResources();
              }
          } else {
              System.out.println("Not connected. Type 'reconnect' to reconnect or 'exit' to quit.");
          }
      }
  }

  /**
   * Creates and starts a listener thread if connected.
   * @return the started thread, or null if not connected
   */
  private Thread createAndStartListenerIfConnected() {
      if (!isConnected || bufferedReader == null) return null;

      Thread listenerThread = new Thread(() -> {
          try {
              String serverMessage;
              while (isConnected && (serverMessage = bufferedReader.readLine()) != null) {
                  System.out.println(decryptMessage(serverMessage));
              }
          } catch (IOException e) {
              if (isConnected) {
                  System.err.println("Connection to server lost: " + e.getMessage());
                  isConnected = false;
              }
          }
      });
      listenerThread.setDaemon(true);
      listenerThread.start();
      return listenerThread;
  }

  /**
   * Attempts to reconnect to the server with exponential backoff.
   * @param host server hostname or IP
   * @param port server port number
   * @return true if reconnection successful, false otherwise
   */
  private boolean attemptReconnection(String host, int port) {
      int attempts = 0;
      int delay = INITIAL_DELAY_MS;

      while (attempts < MAX_RECONNECT_ATTEMPTS) {
          try {
              System.out.println("Reconnecting... Attempt " + (attempts + 1) + "/" + MAX_RECONNECT_ATTEMPTS);
              Thread.sleep(delay);

              socket = new Socket(host, port);
              bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
              isConnected = true;

              System.out.println("Reconnected successfully!");
              return true;

          } catch (IOException | InterruptedException e) {
              attempts++;
              delay = Math.min(delay * 2, 30000);

              if (attempts >= MAX_RECONNECT_ATTEMPTS) {
                  System.err.println("Max reconnection attempts reached. Failed to reconnect.");
                  return false;
              }
          }
      }
      return false;
  }

  /**
   * Closes all connection resources safely.
   */
  private void closeResources() {
      try {
          if (bufferedWriter != null) bufferedWriter.close();
          if (bufferedReader != null) bufferedReader.close();
          if (socket != null && !socket.isClosed()) socket.close();
      } catch (IOException e) {
          System.err.println("Error closing resources: " + e.getMessage());
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
