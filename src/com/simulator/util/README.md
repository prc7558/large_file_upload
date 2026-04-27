# Utility Package

This package provides visual and logistical support to the application.

## Files
1. **ProgressTracker.java**
   - **Purpose**: Tracks the percentage of completion and prints a physical bar to the console.
   - **Line-by-Line Logic**:
     - `\r` (Carriage Return): This is used to move the cursor back to the start of the line without starting a new one. This allows the progress bar to update in place.
     - `BAR_SIZE`: A constant that defines how many characters long the bar is.
     - `formatSize()`: A helper that converts raw bytes into human-readable strings like `KB` or `MB`.

## Contribution
While the `core` package handles the data, `util` handles the **User Experience (UX)**. It ensures the user knows exactly what is happening and how much time/data is left.
