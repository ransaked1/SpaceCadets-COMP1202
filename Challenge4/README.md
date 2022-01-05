# Challenge IV

**_Task:_ Create a client-server networked chat application**

***Description:*** This week's challenge is to make a client-server chatroom. A bit like IRC, but with one user. Unless you get onto the Threading at the end, in which case you might have multiple users :-)

There should be two programs that you can run:

* a Server program, which receives messages and prints them on it's local display
* a Client program, which waits for the user to type a message, and then sends it to the Server

In both cases, you will need to use sockets. This is the way clients and servers communicate reliably (as opposed to datagrams, which are "unreliable" as messages are not guaranteed to be delivered), typically over TCP.
* The server will need a ServerSocket (which opens a socket on your local machine).
* The client will need a Socket (which opens a socket on a 'remote' machine).
* For the purposes of testing, your 'remote' machine will probably be your same local machine, in which case the hostname is localhost.
* You will need to pick a port number. Anything over 1024 will probably not clash, but be aware another program may be using it. You could use a command line argument (remember String[] args in main?) to change the port number when launching the server.

To do anything with this client-server set up, you will need to do some communication.
* A ServerSocket cannot do anything until a client connects. Look at .accept() for this.
* Both Socket and ServerSocket have a .getInputStream() and .getOutputStream(). Work out how to read and write to these.
* Get your client sending messages, and have the server listen and print these out.

***Extension choice:*** Multiple clients with threads.

## Getting Started

### Prerequisites
Install the latest Java JDK:
```
sudo apt install default-jdk
```

### Installing the server
Change the port number in Server.java or leave the default number 6714.

Build the project in the project root folder:
```
make server
```

### Installing the client
Change the portNumber and hostName in Client.java to the IP and port of the machine the server is installed on.

Build the project in the project root folder:
```
make client
```

### Running the server
Before running the server make sure you have opened the port you set it up to listen to.
```
java server/Server
```

### Running the client
Skip the installation part with:
```
make demo
```

### Additional build options
Cleaning up all class files:
```
make clean
```
Rebuild the project (If you made changes to the code):
```
make re
```
Build both the server and client:
```
make all
```

## Built With
* [Java SE 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Recommended Java version
