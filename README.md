# Socket Chat App
 
 An Android app, with a single screen that will:
 1. Connect to a socket server .
 2. The user can send messages to this server and listen to incoming messages as well .

## Table of Contents

1. [Module: app](#module-app)
   - [1.1 MainActivity](#11-mainactivity)
   - [1.2 MainViewModel](#12-mainviewmodel)
   - [1.3 MainAPP.kt](#13-mainappkt)
2. [Module: data](#module-data)
   - [2.1 WebSocketMessageDao](#21-websocketmessagedao)
   - [2.2 AppDatabase](#22-appdatabase)
   - [2.3 WebSocketRepositoryImpl](#23-websocketrepositoryimpl)
   - [2.4 WebSocketClient](#24-websocketclient)

3. [Module: domain](#module-domain)
   - [3.1 WebSocketRepository](#32-websocketrepository)
   - [3.2 CacheMessagesUseCase](#33-cachemessagesusecase)
   - [3.3 CloseWebSocketConnection](#34-closewebsocketconnection)
   - [3.4 ReceiveCachedMessagesUseCase](#35-receivecachedmessagesusecase)
   - [3.5 ReceiveMessagesUseCase](#36-receivemessagesusecase)
   - [3.6 SendMessageUseCase](#37-sendmessageusecase)
4. [Module: di](#module-di)
5. [Additional Notes](#additional-notes)
   - [Testing](#testing)
   - [Room_DB](#room_db)
   - [Coroutines Flow](#coroutines-flow)
   - [Hilt](#hilt)
   - [MVVM Architecture](#mvvm-architecture)
   - [Multi-Module Architecture](#multi-module-architecture)
   
## Module: app

### 1.1 MainActivity

- **Responsibility:**
  - Entry point of the application.
  - Observe the ui with updates recieved from VM.

- **Usage:**
  - Add MainActivity in the application manifest.
  - Use MainActivity to launch the application.

### 1.2 MainViewModel

- **Responsibility:**
  - Manages UI-related data and operations.
  - Interacts with the WebSocketRepository to fetch and send messages.

- **Usage:**
  - Use MainViewModel to observe and update UI-related data.
  - Inject the ViewModel into the UI components using Hilt.

### 1.3 MainAPP.kt

- **Responsibility:**
  - Application class.
  - Initializes Hilt and other global components.

- **Usage:**
  - Add MainAPP to the application manifest as the application class.

## Module: data

### 2.1 WebSocketMessageDao

- **Responsibility:**
  - Data Access Object (DAO) for WebSocket messages.
  - Defines methods to interact with the local database.

- **Usage:**
  - Use WebSocketMessageDao to perform CRUD operations on WebSocket messages.

### 2.2 AppDatabase

- **Responsibility:**
  - Local database for storing WebSocket messages.
  - Uses Room Database.

- **Usage:**
  - Inject AppDatabase where local storage is required.

### 2.3 WebSocketRepositoryImpl

- **Responsibility:**
  - Implements the WebSocketRepository interface.
  - Manages data flow between the local database and the remote WebSocket server.

- **Usage:**
  - Inject WebSocketRepositoryImpl into repositories and use cases.

### 2.4 WebSocketClient

- **Responsibility:**
  - Handles WebSocket connection and communication.
  - Provides methods for sending and receiving messages.

- **Usage:**
  - Use WebSocketClient in the repository to establish and manage WebSocket connections.

## Module: domain


### 3.1 WebSocketRepository

- **Responsibility:**
  - Defines the contract for managing WebSocket messages.
  - Acts as a bridge between the domain and data layers.

- **Usage:**
  - Inject WebSocketRepository into use cases.

### 3.2 CacheMessagesUseCase

- **Responsibility:**
  - Use case for caching WebSocket messages locally.

- **Usage:**
  - Inject CacheMessagesUseCase where caching of messages is required.

### 3.3 CloseWebSocketConnection

- **Responsibility:**
  - Use case for closing the WebSocket connection.

- **Usage:**
  - Inject CloseWebSocketConnection where closing the WebSocket connection is required.

### 3.4 ReceiveCachedMessagesUseCase

- **Responsibility:**
  - Use case for retrieving locally cached WebSocket messages.

- **Usage:**
  - Inject ReceiveCachedMessagesUseCase where retrieving cached messages is required.

### 3.5 ReceiveMessagesUseCase

- **Responsibility:**
  - Use case for receiving WebSocket messages from the server.

- **Usage:**
  - Inject ReceiveMessagesUseCase where handling incoming messages is required.

### 3.6 SendMessageUseCase

- **Responsibility:**
  - Use case for sending WebSocket messages to the server.

- **Usage:**
  - Inject SendMessageUseCase where sending messages is required.

## Module: di

- **Responsibility:**
  - Contains Hilt-related configuration and dependency injection modules.

- **Usage:**
  - Include the di module in the application module for Hilt setup.

## Additional Notes


### Room_DB

- Room database to cache send and recieved msgs .

### Coroutines Flow

- Utilize Coroutines Flow for handling asynchronous operations.

### Hilt

- Utilize Hilt for dependency injection.

### MVVM Architecture

- Ensure that the components follow the MVVM architectural pattern.
  
### Testing

- All components should be unit-tested using appropriate testing frameworks.
- Use Coroutines Test for testing suspending functions and flows.
  

