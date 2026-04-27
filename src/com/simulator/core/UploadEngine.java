package com.simulator.core;

import com.simulator.util.ProgressTracker;
import java.io.*;

/**
 * The core engine that manages the upload simulation, progress tracking, and resume functionality.
 */
public class UploadEngine {

    private final ChunkProcessor processor;
    private final int chunkSize;
    private final int delayMs; // Simulated latency

    public UploadEngine(int chunkSize, int delayMs) {
        this.processor = new ChunkProcessor();
        this.chunkSize = chunkSize;
        this.delayMs = delayMs;
    }

    /**
     * Simulates a file upload.
     * 
     * @param sourceFile The file on the "client" side.
     * @param destFile The destination on the "server" side.
     * @throws IOException If file operations fail.
     */
    public void upload(File sourceFile, File destFile) throws IOException {
        long sourceSize = sourceFile.length();
        long alreadyUploaded = 0;

        // Check if destination exists (Resume logic)
        if (destFile.exists()) {
            alreadyUploaded = destFile.length();
            if (alreadyUploaded >= sourceSize) {
                System.out.println("File already fully uploaded.");
                return;
            }
        }

        ProgressTracker tracker = new ProgressTracker(sourceSize);
        if (alreadyUploaded > 0) {
            tracker.setInitialOffset(alreadyUploaded);
        }

        // Open source for reading, skipping already uploaded bytes
        try (FileInputStream fis = new FileInputStream(sourceFile);
             // Open destination in append mode
             FileOutputStream fos = new FileOutputStream(destFile, true)) {

            fis.skip(alreadyUploaded);

            int bytesRead;
            while ((bytesRead = processor.processChunk(fis, fos, chunkSize)) != -1) {
                tracker.update(bytesRead);
                
                // Simulate network delay
                if (delayMs > 0) {
                    try {
                        Thread.sleep(delayMs);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("\nUpload interrupted.");
                        return;
                    }
                }
            }
        }
    }
}
