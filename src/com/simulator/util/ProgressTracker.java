package com.simulator.util;

/**
 * Utility class to track and display upload progress in the console.
 */
public class ProgressTracker {

    private final long totalSize;
    private long currentSize;
    private static final int BAR_SIZE = 40;

    public ProgressTracker(long totalSize) {
        this.totalSize = totalSize;
        this.currentSize = 0;
    }

    /**
     * Updates the progress and prints the progress bar.
     * @param bytesAdded Number of bytes processed in the last chunk.
     */
    public void update(int bytesAdded) {
        this.currentSize += bytesAdded;
        display();
    }

    /**
     * Sets the initial offset (for resume functionality).
     * @param initialSize Bytes already uploaded.
     */
    public void setInitialOffset(long initialSize) {
        this.currentSize = initialSize;
        System.out.println("Resuming from: " + formatSize(initialSize));
    }

    private void display() {
        double percent = (double) currentSize / totalSize;
        int filledCount = (int) (percent * BAR_SIZE);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < BAR_SIZE; i++) {
            if (i < filledCount) bar.append("=");
            else if (i == filledCount) bar.append(">");
            else bar.append(" ");
        }
        bar.append("]");

        String progressInfo = String.format("\r%s %.2f%% (%s / %s)", 
                bar.toString(), 
                percent * 100, 
                formatSize(currentSize), 
                formatSize(totalSize));
        
        System.out.print(progressInfo);
        
        if (currentSize >= totalSize) {
            System.out.println("\nUpload Complete!");
        }
    }

    private String formatSize(long size) {
        if (size < 1024) return size + " B";
        int z = (63 - Long.numberOfLeadingZeros(size)) / 10;
        return String.format("%.2f %sB", (double)size / (1L << (z * 10)), " KMGTPE".charAt(z));
    }
}
