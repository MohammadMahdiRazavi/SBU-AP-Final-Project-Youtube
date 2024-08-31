# Youtube

## Introduction
This is the final project of our [**Advanced Programming**](https://github.com/Advanced-Programming-1402) course in Shahid Beheshti University. It's a clone of the famous video-streaming application **Youtube** designed using **Java** programming language.<br>
From [here](), you can acces the project documentation as well.


## Objectives
Here is a list of concepts which where used through out the project:
- Using OOP concepts
- Database design
- Multithreading concepts
- Socket Programming
- Designing graphical user interface with JavaFX


## Pre Requirements
- Java
- PostgreSQL
- JavaFX
- IntelliJ as an IDE
- Maven as a build system for the project



## Creating GUI
The main tool for designing graphical user interface for this project is [JavaFX](https://en.wikipedia.org/wiki/JavaFX).


## Implementation
The project has 3 main parts:<br>
1. `YoutubeClient` <br>
2. `YoutubeServer`<br>
3. `Sockets`
- `YoutubeClient`
  This is the package that is considered to be the interface/app layer of the project. It's repsonsible for getting input from a client, sending it through a socket to the server and waiting for a proper response.
- `YoutubeServer`
  The server is responsible for reading the requests from each clinet, either respoend directly or acces the database using
  `DataBaseManager`
- class methods and then, responding back to the client. It's worth noting that the server is multithreaded, which means it can handle dealing with multiple reqeusets as well. Also a server log a printed to the terminal to show what the server is doint at the moment.
- `Sockets`
  It's worth noting that all the data passed between each client and the server is done through the use of sockets. In fact, each request from the client is sent to the socket, then server receives it and responds properly **through the same socket** for each client.

## date/time
Spring and Summer of 2024 (Version 1.0)