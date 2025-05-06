# Theatre Manager

A basic Java desktop application for managing theatres and shows, using Swing for the interface and CSV files for data persistence.
This is a job for the Java exam at Catania's Steve Jobs Academy.

## Features

- Load theatres and shows from CSV files (example files included)
- Display shows in a table with filtering options
- Buy tickets and track total earnings
- Add new shows and theatres via GUI
- Simple, no external dependencies

## Project Structure

```plaintext
theatre-manager/
├── data/
│   ├── theatres.csv
│   └── shows.csv
├── src/
│   ├── Show.java
│   ├── Theatre.java
│   ├── TheatreApp.java
│   ├── TheatreGUI.java
│   └── TheatreManager.java
└── .gitignore
```

## How to Run

1. Compile all Java files:

   ```bash
   javac src/*.java
   ```

2. Run the main application:

   ```bash
   java -cp src TheatreApp
   ```

## Requirements

- Java 8 or higher

## Author

Nicola Corsaro
