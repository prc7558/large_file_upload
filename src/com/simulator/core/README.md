# Core Package

This package manages the essential logic of the file upload simulation.

## Files
1. **ChunkProcessor.java**
   - **Purpose**: Acts as the low-level data handler.
   - **Key Method**: `processChunk(InputStream, OutputStream, int)`
   - **Logic**: It reads exactly `chunkSize` bytes from the input and writes them to the output. This simulates a packet system in a network.

2. **UploadEngine.java**
   - **Purpose**: Orchestrates the entire upload process.
   - **Key Method**: `upload(File, File)`
   - **Logic**: 
     - First, it checks the destination directory (`server/`) to see if the file exists.
     - If it exists, it determines how much was already "uploaded" (resumption).
     - It then skips that amount in the source and opens the output stream in **append mode** (`new FileOutputStream(dest, true)`).
     - It loops through the file, calling the `ChunkProcessor` and updating the `ProgressTracker`.

## Importance
This folder demonstrates how complex tasks (like resuming an upload) can be broken down into smaller, manageable chunks (chunked I/O).
