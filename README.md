# Spring Smart Door Lock Socket

A Spring Boot WebSocket application for managing smart door lock communications.

## Project Overview

This project implements a WebSocket server using Spring Boot to handle real-time communications for smart door lock systems. It includes Redis Stream integration for message handling and WebSocket functionality for client-server communication.

## Features

- WebSocket server implementation
- Redis Stream integration for message handling
- Push notification support
- Client management system
- Global exception handling
- Configuration management

## Project Structure

```
src/
├── main/
    ├── java/spring/socket/
    │   ├── config/
    │   │   ├── EnvConfig.java
    │   │   ├── Global.java
    │   │   ├── RedisStreamConfig.java
    │   │   └── WebConfig.java
    │   ├── controller/
    │   │   └── PushController.java
    │   ├── exception/
    │   │   ├── ErrorDetails.java
    │   │   ├── GlobalExceptionHandler.java
    │   │   └── ResourceNotFoundException.java
    │   ├── tiosocket/
    │   │   ├── ClientManager.java
    │   │   ├── MyMsgHandler.java
    │   │   ├── RedisStreamPublisher.java
    │   │   ├── RedisStreamSubscriber.java
    │   │   └── RedisSubscriber.java
    │   └── SocketApplication.java
    └── resources/
        └── application.yml
```

## Prerequisites

- Java 8 or higher
- Maven
- Redis Server

## Getting Started

1. Clone the repository:
```bash
git clone https://github.com/snowflakeworld/Spring_SmartDoorLockSocket.git
```

2. Navigate to the project directory:
```bash
cd Spring_SmartDoorLockSocket
```

3. Build the project:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

## Configuration

The application can be configured through the `application.yml` file located in the `src/main/resources` directory.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Contact

Project Link: https://github.com/snowflakeworld/Spring_SmartDoorLockSocket