# ICPGetACs

## Description
**ICPGetACs** is a Java project designed to fetch Certificate Authorities (ACs) from the ICP-Brasil website using a certificate (A1 ICP). It automates the download process and handles certificates through a multi-threaded architecture. The project utilizes Selenium for web interaction and Apache Commons IO for file management.

## Table of Contents

- [Description](#description)
- [How to Use](#how-to-use)
- [Features](#features)
- [Dependencies](#dependencies)
- [Execution](#execution)
- [Output](#output)

## How to Use

### 1. Clone the Repository
Clone the repository to your local machine:
```bash
git clone <repository-link>
```

### 2. Open in an IDE
Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans). Ensure you have Maven configured in your IDE to manage dependencies.

### 3. Execute via IDE
Run the application by executing the `Main` class as a Java application.

### 4. Execute via Command Line
For terminal execution:

1. Compile the application:
   ```bash
   javac -cp "path/to/dependencies/*" icpgetacs/Main.java
   ```

2. Run the application:
   ```bash
   java -cp "path/to/dependencies/*:." icpgetacs.Main
   ```

## Features

- **Multi-threading**: Manages file downloads and processing efficiently.
- **Web Interaction**: Automates navigation and downloads from the ICP-Brasil website using Selenium.
- **File Management**: Extracts and processes downloaded certificates with support for OpenSSL.

## Dependencies

The project relies on the following Maven dependencies:

- `org.seleniumhq.selenium:selenium-java` (for web automation)
- `commons-io:commons-io` (for file management)

Ensure these dependencies are configured in your `pom.xml` file.

## Execution

### From an IDE

1. Open the `Main` class.
2. Right-click and select **Run as Java Application** (specific instructions vary by IDE).

### From the Command Line

1. Compile the application:
   ```bash
   javac icpgetacs/Main.java
   ```

2. Execute the compiled program:
   ```bash
   java icpgetacs.Main
   ```
---

This project demonstrates an automated approach to managing ICP-Brasil certificates, making it ideal for scenarios requiring periodic updates and certificate handling.

