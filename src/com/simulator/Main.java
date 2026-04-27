package com.simulator;

import com.simulator.core.UploadEngine;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main entry point for the Local File Upload Simulator.
 */
public class Main {

    private static final String SERVER_DIR = "server";
    private static final String CLIENT_DIR = "client_storage";
    private static final int DEFAULT_CHUNK_SIZE = 64 * 1024; // 64KB
    private static final int DEFAULT_DELAY = 100; // 100ms

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("========================================");
        System.out.println("   Local File Upload Simulator (Java)");
        System.out.println("========================================");

        // Ensure directories exist
        new File(SERVER_DIR).mkdirs();
        new File(CLIENT_DIR).mkdirs();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. List files in client storage");
            System.out.println("2. Start/Resume upload");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    listFiles(CLIENT_DIR);
                    break;
                case "2":
                    handleUpload(scanner);
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void listFiles(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No files found in " + dirPath);
        } else {
            System.out.println("\nFiles in " + dirPath + ":");
            for (File f : files) {
                if (f.isFile()) {
                    System.out.println("- " + f.getName() + " (" + f.length() + " bytes)");
                }
            }
        }
    }

    private static void handleUpload(Scanner scanner) {
        System.out.print("Enter file name to upload: ");
        String fileName = scanner.nextLine();
        
        File clientDirFile = new File(CLIENT_DIR).getAbsoluteFile();
        File sourceFile = new File(clientDirFile, fileName).getAbsoluteFile();

        // Robust Path Traversal check
        try {
            if (!sourceFile.getCanonicalPath().startsWith(clientDirFile.getCanonicalPath())) {
                System.out.println("Error: Invalid filename or path traversal attempt.");
                return;
            }
        } catch (IOException e) {
            System.out.println("Error: Could not validate file path.");
            return;
        }
        
        File source = new File(CLIENT_DIR, fileName);
        File dest = new File(SERVER_DIR, fileName);

        if (!source.exists() || !source.isFile()) {
            System.out.println("Error: File not found in client storage.");
            return;
        }

        System.out.println("Starting upload simulation for: " + fileName);
        UploadEngine engine = new UploadEngine(DEFAULT_CHUNK_SIZE, DEFAULT_DELAY);
        
        try {
            engine.upload(source, dest);
        } catch (IOException e) {
            System.err.println("Upload failed: " + e.getMessage());
        }
    }
}
