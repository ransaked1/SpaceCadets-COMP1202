package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

  private static int portNumber = 6714;
  private static Set<ClientThread> clientThreads = new HashSet<>();
  private Set<String> clientNames = new HashSet<>();

  public static void main(String[] args) {
    Server server = new Server();
    try {
      ServerSocket serverSocket = new ServerSocket(portNumber);
      server.acceptClient(serverSocket);
    } catch (IOException e) {
      System.err.println("Could not listen to port: " + portNumber);
      System.exit(1);
    }
  }

  /**
   * Method that accepts an incoming connection. In this chat app the server accepts any client that
   * wants to connect and there is no security.
   *
   * @param serverSocket the socket number to connect through.
   */
  public void acceptClient(ServerSocket serverSocket) {
    // Check for an incoming connection continuously until the server is shut down.
    while (true) {
      try {
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        // Create a new thread to listen to the client and add it to the list of clients.
        ClientThread client = new ClientThread(socket, this);
        clientThreads.add(client);
        client.start();

      } catch (IOException e) {
        System.err.println("Could not accept client on port " + portNumber);
        e.printStackTrace();
      }
    }
  }

  /**
   * Getter for the client list.
   *
   * @return a set of all the clients connected to the server.
   */
  public Set<String> getClientNames() {
    return clientNames;
  }

  /**
   * Method that sends a message to all the client threads.
   *
   * @param message the message to send.
   * @param exceptionClient the client that sent the message.
   */
  void broadcast(String message, ClientThread exceptionClient) {
    for (ClientThread client : clientThreads) {
      // Skipping the client who sent the message to the server.
      if (client != exceptionClient) {
        client.sendMessage(message);
      }
    }
  }

  /**
   * Adding a new client to the client list.
   *
   * @param clientName the username of the client as string.
   */
  void addClientName(String clientName) {
    clientNames.add(clientName);
  }

  /**
   * Remove a client from the client list.
   *
   * @param clientName the name of the client as string.
   * @param client the thread of the respective client.
   */
  void removeClient(String clientName, ClientThread client) {
    if (clientNames.remove(clientName)) {
      clientThreads.remove(client);
      // Output message to the server's console (not seen by clients).
      System.out.println("The user " + clientName + " left the chat.");
    }
  }

  /**
   * Check if anyone is connected to the server.
   *
   * @return True if nobody is connected and False otherwise.
   */
  boolean clientsConnectedState() {
    return !this.clientNames.isEmpty();
  }
}
