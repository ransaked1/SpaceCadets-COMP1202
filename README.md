# Space Cadets
Besides the Programming I course in my first semester, there was an optional course for students with previous experience in programming that included weekly challenges that explored more advanced topics. The only condition is to have your solution written in Java. The course was also a survival competition for the students who can complete the most challenges in the 9 weeks. I made it into top 5 out of the initial 50 participants :)

Below is a general breakdown of each challenge with a more detailed Readme in its folder.

## Challenges Breakdown

### Challenge I
Unfortunately, I have the bad habbit of pushing authentication tokens to public repositories. After panicking I purged my repository containing my solution to the challenge using Selenium and Java. No local backup was available so the code was lost forever. ***RIP***

**_Task:_ Write a Web service to Fetch an email ID and get a person's name**

_Description:_ Have a look at the Web page at https://www.ecs.soton.ac.uk/people/dem. This is a departmental information page which gives all sorts of information about a member of staff. The Web address is constructed from a departmental email id (in this case dem). If I have someone else's email id, I can look up their name from one of these Web pages. Try it with your own email id. In fact, in the past the name started at the 12th character of the 6th line of the HTML data returned by the Web server. It finishes when a '<' character appears. (Choose 'View Source' from your Web browser to check where it is now.)
### Challenge II

**_Task:_ Build a bare bones interpreter to take text file and produce an output**

_Description:_ Bare Bones is the simple language that Brookshear uses in his book, 'Computer Science: an Overview', to illustrate the power of Turing complete machines and investigate the halting problem. This weeks challenge is to implement a Bare Bones interpreter. The program should take a text file containing a bare bones program as input and execute each statement in turn. After each statement has been executed it should output the state of all the variables in the system to form a record of execution.
### Challenge III

**_Task:_ Extend the bare bones language interpreter**

***Description:*** Extend your interpreter with whatever you think is important, try and keep the syntax consistent. Make sure you document your language extensions.

***Extension choice:*** Comments and if/else statements.
### Challenge IV

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
### Challenge V

**_Task:_ Write an applet that makes spirographs**

***Description:*** The challenge this week is to create a virtual version of a "Spirograph" (a childrens toy back in the dark ages). A Spirograph consisted of a set of small toothed wheels that rolled around the inside of larger wheels. You put a pen inside a hole in the smaller wheels and drew out a pattern as it looped around the bigger wheel. Your challenge is to write an Applet or JApplet or AWT application or Swing application or JavaFX application that draws a simple hypercycloid.

In this challenge I'd like you to focus on creating an Object Oriented solution. For example, you might create a separate class that represents a hypocycloid, which has instance variables for (among other things) the radius of the circles. Your class should provide a sensible constructor so that a hypocycloid of a particular shape can be easily created. The class should provide a paint() method so that it can be easily drawn by the applet's paint() method.
### Challenge VI

**_Task:_ Circle detection (Computer Vision)**

***Description:*** Steps for circle location detection:
1. Load image
2. Convert image to greyscale
3. Do edge detection (Sobel operator)
4. Do circle detection at various radii (Hough transform)
5. Draw circle around the most prominent circle(s)
### Challenge IX

**_Task:_ Code anything winter themed in the programming language of choice**

***Project choice:*** Github Rewind - given a github username the program builds images with year statistics like Spotify Wrapped. Written in Python.
