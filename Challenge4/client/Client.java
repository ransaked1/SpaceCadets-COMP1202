package client;

import java.net.*;
import java.io.*;

/** Driver class that will setup and run a client to connect to the chat server. */
public class Client {

  // Setting up the IP and port for the server host (machine it will run on)
  static int portNumber = 6714;
  static String hostName = "90.251.65.66";
  private String clientName;

  /**
   * Main function that creates a client object and runs it.
   *
   * @param args Terminal input.
   */
  public static void main(String[] args) {
    Client client = new Client();
    client.start();
  }

  /**
   * Method that starts a socket that connects to the server and creates the read/write instances
   * for the client.
   */
  public void start() {
    try {
      Socket socket = new Socket(hostName, portNumber);

      System.out.println("Connecting to chat server...");

      new ServerWriteThread(socket, this).start();
      new ServerReadThread(socket, this).start();
    } catch (UnknownHostException e) {
      System.out.println("Server not found: " + e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Getter that returns the username of the user.
   *
   * @return client name string.
   */
  public String getClientName() {
    return clientName;
  }

  /**
   * Setter that sets the username of the user.
   *
   * @param clientName client name string.
   */
  public void setClientName(String clientName) {
    this.clientName = clientName;
  }
}
