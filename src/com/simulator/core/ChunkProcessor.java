package com.simulator.core;

import java.io.*;

/**
 * Handles chunk-based reading and writing of file data.
 * This class is responsible for the low-level I/O operations.
 */
public class ChunkProcessor {

    /**
     * Reads a chunk of data from the input stream and writes it to the output stream.
     * 
     * @param in The source input stream.
     * @param out The destination output stream.
     * @param chunkSize The size of the chunk to read/write.
     * @return The actual number of bytes processed. Returns -1 if EOF is reached.
     * @throws IOException If an I/O error occurs.
     */
    public int processChunk(InputStream in, OutputStream out, int chunkSize) throws IOException {
        byte[] buffer = new byte[chunkSize];
        int bytesRead = in.read(buffer);
        
        if (bytesRead != -1) {
            out.write(buffer, 0, bytesRead);
            out.flush(); // Ensure data is written to disk
        }
        
        return bytesRead;
    }
}
