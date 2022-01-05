# Challenge II

**_Task:_ Build a bare bones interpreter to take text file and produce an output**

_Description:_ Bare Bones is the simple language that Brookshear uses in his book, 'Computer Science: an Overview', to illustrate the power of Turing complete machines and investigate the halting problem. This weeks challenge is to implement a Bare Bones interpreter. The program should take a text file containing a bare bones program as input and execute each statement in turn. After each statement has been executed it should output the state of all the variables in the system to form a record of execution.

## Getting Started

### Prerequisites
Install the latest Java JDK:
```
sudo apt install default-jdk
```

### Installing
Build the project in the project root folder:
```
make
```
Running a test file:
```
java BareBones test1.bb
```
or 
```
java BareBones test2.bb
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

## Built With
* [Java SE 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Recommended Java version
