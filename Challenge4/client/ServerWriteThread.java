package client;

import java.io.*;
import java.net.*;

/** Thread that writes to the server. */
public class ServerWriteThread extends Thread {

  private PrintWriter serverOut;
  private Socket socket;
  private Client client;

  /**
   * Method that sets up the socket that reads from the server.
   *
   * @param socket the socket of the server.
   * @param client the client object that has the username of the user.
   */
  public ServerWriteThread(Socket socket, Client client) {
    this.socket = socket;
    this.client = client;

    try {
      OutputStream out = socket.getOutputStream();
      serverOut = new PrintWriter(out, true);
    } catch (IOException e) {
      System.out.println("Error getting output stream.");
      e.printStackTrace();
    }
  }

  /** Run method of the thread. */
  public void run() {
    Console console = System.console();

    // Get the users username and send the message to the server that they joined.
    String clientName = console.readLine("\nEnter username: ");
    client.setClientName(clientName);
    serverOut.println(clientName);

    String text;

    // Push user messages to the server until exit message is sent.
    do {
      text = console.readLine("[" + clientName + "] > ");
      serverOut.println(text);
    } while (!text.equals("exit"));

    try {
      socket.close();
    } catch (IOException e) {
      System.out.println("Error outputting to server: " + e.getMessage());
    }
  }
}
