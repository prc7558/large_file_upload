# Server Folder (Simulation)

This represents the "remote server" storage.

## Purpose
- In a real application, this folder would be on a distant computer accessed via the internet.
- In this simulation, it is just a local folder where files are copied to chunk-by-chunk.

## Files
- Files uploaded via the application will appear here.
- If an upload is interrupted, you will see a partial file here.
- The `UploadEngine` uses the size of files in this folder to know where to **resume** from.
