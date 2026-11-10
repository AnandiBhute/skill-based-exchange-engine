# Skill Exchange Platform

## Overview
The Skill Exchange Platform is a Java-based application that connects people who want to teach skills with people who want to learn them. Users can create profiles, list their teaching and learning skills, add interests, discover skill matches, and build learning partnerships.

The project uses **Java, JDBC, and SQLite** for database connectivity and storage. It also demonstrates the practical implementation of various object-oriented design patterns.

## Features
- **User Registration:** Register and manage user profiles.
- **Skill Management:** Add teaching skills and learning skills.
- **Interest Management:** Add interests while preventing duplicate entries.
- **Skill Matching:** Find users with matching teaching and learning skills.
- **Bond Management:** Create, complete, and cancel learning partnerships.
- **Learning Journeys:** Choose between beginner and advanced learning journeys.
- **Subscription Management:** Purchase subscriptions.
- **Undo Functionality:** Restore previous skill and interest changes using the Memento pattern.
- **Mediator Communication:** Manage interactions between users through the Mediator pattern.

## Design Patterns Implemented
- **Singleton Pattern:** Provides a single shared instance where required.
- **Factory Pattern:** Handles object creation.
- **Strategy Pattern:** Supports different learning journey strategies.
- **Mediator Pattern:** Coordinates communication between users.
- **Memento Pattern:** Saves and restores previous user profile states.

## Technologies Used
- Java
- JDBC
- SQLite
- Object-Oriented Programming
- Design Patterns

## Database
SQLite is used to store user information, teaching skills, learning skills, interests, bonds, and subscription-related data. JDBC handles database connectivity and SQL operations.

## How to Run

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Open the project folder in VS Code.

3. Ensure the SQLite JDBC driver JAR is present in the project directory.

4. Compile the Java files:
   ```bash
   javac -cp ".:sqlite-jdbc-3.53.2.1.jar" *.java
   ```

5. Run the application:
   ```bash
   java -cp ".:sqlite-jdbc-3.53.2.1.jar" Main
   ```

## Project Objective
The main objective is to encourage collaborative learning by allowing users to exchange knowledge and skills while demonstrating practical Java programming concepts, database integration, and software design patterns.
