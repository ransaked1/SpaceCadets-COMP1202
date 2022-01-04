package client;

import java.io.*;
import java.net.*;

/** Thread that reads from server. */
public class ServerReadThread extends Thread {

  private Socket socket;
  private BufferedReader serverIn;
  private Client client;

  /**
   * Method that reads the messages sent to the server.
   *
   * @param socket the socket of the server.
   * @param client the client object that has the username of the user.
   */
  public ServerReadThread(Socket socket, Client client) {
    this.socket = socket;
    this.client = client;

    try {
      // Get messages from server.
      InputStream in = socket.getInputStream();
      serverIn = new BufferedReader(new InputStreamReader(in));
    } catch (IOException e) {
      System.out.println("Error getting input stream.");
      e.printStackTrace();
    }
  }

  /** Run method of the thread. */
  public void run() {
    // Infinite loop that checks that the server is available for reading.
    while (true) {
      try {
        String response = null;

        try {
          response = serverIn.readLine();
        } catch (Exception e) {
          System.out.println("Disconnected.");
          break;
        }

        if (response == null) {
          System.out.println("\nServer disconnected. Please try again later.");
          break;
        }

        // Output the message read from the server.
        System.out.println("\n" + response);

        // Output the name of the user that wrote the message.
        if (client.getClientName() != null) {
          System.out.print("[" + client.getClientName() + "] > ");
        }
      } catch (Exception e) {
        System.out.println("Error reading from server.");
        e.printStackTrace();
        break;
      }
    }
  }
}
