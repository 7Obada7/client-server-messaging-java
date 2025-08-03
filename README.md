# Java Client-Server Messaging App 💬

A basic client-server communication system using Java sockets, created during the early years of my university studies. The project features a simple GUI that allows clients to connect to a server, send messages, and receive replies either directly or as a broadcast to all connected clients.

---

## 📦 Technologies Used

- Java SE
- Java Sockets
- Swing (GUI)
- Multi-threading

---

## 🎯 Features

### Server
- Accepts multiple client connections using threads
- GUI displays connected clients
- Can send:
  - Broadcast messages to all clients
  - Direct messages to a selected client

### Client
- GUI to input server IP and port
- Sends messages to the server
- Displays incoming messages from the server

---

## 🖼️ UI Overview

- IP and port input fields
- List of connected clients (on server)
- Chat/message window
- Simple user interaction flow
javac *.java
java Frm_Server  # or the class that starts the GUI server
