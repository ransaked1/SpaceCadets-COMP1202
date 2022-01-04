package server;

import java.io.*;
import java.net.*;
import java.util.*;

/** Thread that listens to each client connected to the server. */
public class ClientThread extends Thread {

  private Socket socket;
  private Server server;
  private PrintWriter out;
  
  public ClientThread(Socket socket, Server server) {
    this.server = server;
    this.socket = socket;
  }

  /** Method that mannages data sent and received from the clients. */
  public void run() {
    try {
      InputStream in = socket.getInputStream();
      BufferedReader readIn = new BufferedReader(new InputStreamReader(in));

      OutputStream output = socket.getOutputStream();
      out = new PrintWriter(output, true);

      // Show the clients connected to the server when joining.
      printClients();

      // Get the clients username and add it to the server's list of clients.
      String clientName = readIn.readLine();
      server.addClientName(clientName);

      // Output a message to all the clients that the user has joined the chat.
      String serverMessage = clientName + " just connected!";
      server.broadcast(serverMessage, this);

      // Get the message from the client and broadcast it to everyone until exit message received.
      String clientMessage;
      while (!clientMessage.equals("exit")) {
        clientMessage = readIn.readLine();
        serverMessage = "[" + clientName + "]" + " > " + clientMessage;
        server.broadcast(serverMessage, this);
      }

      // Remove the client when disconnecting.
      server.removeClient(clientName, this);
      socket.close();

      // Output a message to all the clients that the user has left the chat.
      serverMessage = clientName + " left the chat.";
      server.broadcast(serverMessage, this);
    } catch (IOException e) {
      System.out.println("ClientThread error!");
      e.printStackTrace();
    }
  }

  /** This method outputs the names of the users connected to the server. */
  void printClients() {
    if (server.clientsConnectedState()) {
      out.println("Connected users: " + server.getClientNames());
    } else {
      out.println("No other users connected");
    }
  }

  /**
   * Output a message to the terminal.
   *
   * @param message the message to output.
   */
  void sendMessage(String message) {
    out.println(message);
  }
}
