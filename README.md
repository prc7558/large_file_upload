# Local File Upload Simulator (Java)

## Overview
The **Local File Upload Simulator** is a Java-based application designed to simulate the process of uploading files to a remote server. Instead of a real network transfer, it simulates "uploading" by copying files from a `client_storage` directory to a `server` directory using chunk-based I/O.

## Objective
The primary goal of this project is to demonstrate:
1.  **File Handling in Java**: Utilizing `FileInputStream` and `FileOutputStream` for byte-level operations.
2.  **Chunk Processing**: Reading and writing data in small portions to simulate real-world data streaming.
3.  **Progress Tracking**: Providing real-time visual feedback in the console.
4.  **Resume Functionality**: Automatically detecting partially uploaded files and resuming from the last written byte.

## Features
-   **Chunked I/O**: Reads/writes files in 64KB chunks.
-   **Simulated Latency**: Artificial delay to mimic network speeds.
-   **Visual Progress Bar**: Real-time percentage and progress bar in the CLI.
-   **Safe Resume**: Appends data to existing files if an upload is interrupted.

## Technologies Used
-   **Language**: Java (Standard Edition)
-   **Concepts**:
    -   **OOP**: Encapsulation of logic in `UploadEngine`, `ChunkProcessor`, and `ProgressTracker`.
    -   **PPL Principles**: Demonstrates strong typing, exception handling, and resource management (try-with-resources).
    -   **Java I/O Package**: `java.io.*` for stream-based processing.

## Project Structure
```text
local_file_upload_simulator/
├── client_storage/     # Simulated local user directory (Source)
├── server/             # Simulated remote server directory (Destination)
├── src/
│   └── com/
│       └── simulator/
│           ├── core/   # Engine and Chunk Processing logic
│           ├── util/   # UI and Progress tracking utilities
│           └── Main.java # Main Entry Point
└── README.md           # This documentation
```

## Step-by-Step Instructions

### 1. Preparation
Place any file you wish to "upload" inside the `client_storage` folder.

### 2. Compilation
Open your terminal in the project root and run:
```bash
javac -d bin src/com/simulator/*.java src/com/simulator/core/*.java src/com/simulator/util/*.java
```

### 3. Execution
Run the application:
```bash
java -cp bin com.simulator.Main
```

### 4. Uploading
- Select option `1` to see available files.
- Select option `2` and enter the filename.
- Observe the progress bar!

## How It Works
1.  **Simulation Engine**: The `UploadEngine` checks if the file already exists in the `server` folder.
2.  **Resume Logic**: If `server/file.txt` exists and is smaller than `client_storage/file.txt`, the engine uses `fis.skip(size)` to jump to the correct position and `FileOutputStream(file, true)` to append.
3.  **Progress tracking**: After every chunk is processed, the `ProgressTracker` updates the CLI with the current percentage using the `\r` carriage return to overwrite the same line.

## Sample Workflow
1. User starts upload of `video.mp4` (100MB).
2. At 45%, the user cancels the process (Ctrl+C).
3. The user restarts the application and chooses to upload `video.mp4` again.
4. The application detects `video.mp4` in `server/` with 45MB size.
5. Upload resumes from 45% and completes successfully.
