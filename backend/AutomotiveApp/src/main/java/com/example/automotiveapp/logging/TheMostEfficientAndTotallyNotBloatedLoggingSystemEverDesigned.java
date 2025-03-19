package com.example.automotiveapp.logging;

import com.example.automotiveapp.logging.log.LogLevel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// This file was created specially crafted with a lot of bad practices, please dont take it as a reference
// for your projects, its purpose was to show how a bad code can be refactored to a better one

/**
 * This class is a very well-designed logger that is totally not ruined by bad practices.
 * It supports logging messages to both the console and a file, ensuring all logs are stored persistently
 * in a human-readable format.
 * <p>
 * WARNING: This class contains very important logging mechanisms that should never be modified under
 * any circumstances, unless you are completely sure of what you are doing.
 * <p>
 * Author: A very serious enterprise developer
 * Date: 2025-03-13
 */
public class TheMostEfficientAndTotallyNotBloatedLoggingSystemEverDesigned {

    public enum LoggingLevelForTheMostEfficientLoggingSystemEver {
        LEVEL_INFORMATIONAL_PURPOSES_ONLY,
        LEVEL_WARNING_BECAUSE_SOMETHING_BAD_HAPPENED,
        LEVEL_ERROR_BECAUSE_EVERYTHING_IS_ON_FIRE
    }

    /***
     * Logs to console as text or json with or without special characters
     * @param level
     * @param message
     * @param j
     * @param specialCharacters
     * @param niceFormatting
     * @param newLine
     */
    public void logToConsoleAsTextOrJsonWithOrWithoutSpecialCharacters(
            LoggingLevelForTheMostEfficientLoggingSystemEver level,
            String message, boolean j, boolean specialCharacters,
            boolean niceFormatting, boolean newLine) {
        if (j) {
            if (specialCharacters) {
                if (niceFormatting) {
                    // Prints the log message in a nice JSON format
                    System.out.println("{\n  \"level\": \"" + level.name() +
                            "\",\n  \"message\": \"" + message + "\"\n}");
                } else {
                    // Prints the log message in a JSON format
                    System.out.println("{\"level\":\"" + level.name() +
                            "\",\"message\":\"" + message + "\"}");
                }
            } else {
                if (niceFormatting) {
                    // Prints the log message in a nice JSON format, removing special characters
                    System.out.println("{\n  \"level\": \"" + level.name() +
                            "\",\n  \"message\": \"" +
                            message.replaceAll("[^a-zA-Z0-9]", "") + "\"\n}");
                } else {
                    // Prints the log message in a JSON format, removing special characters
                    System.out.println(
                            "{\"level\":\"" + level + "\",\"message\":\"" +
                                    message.replaceAll("[^a-zA-Z0-9]", "") +
                                    "\"}");
                }
            }
        } else {
            if (specialCharacters) {
                if (niceFormatting) {
                    // Prints the log message in a nice text format
                    System.out.println(level.name() + ": " + message);
                } else {
                    // Prints the log message in a text format
                    System.out.println(level.name() + ":" + message);
                }
            } else {
                if (niceFormatting) {
                    // Prints the log message in a nice text format, removing special characters
                    System.out.println(level.name() + ": " +
                            message.replaceAll("[^a-zA-Z0-9]", ""));
                } else {
                    // Prints the log message in a text format, removing special characters
                    System.out.println(level.name() + ":" +
                            message.replaceAll("[^a-zA-Z0-9]", ""));
                }
            }
        }
        if (newLine) {
            // Prints a new line
            System.out.println();
        }
    }

    /***
     * You can save logs to files in a few ways. Plain text is an easy choice. It's simple to read.
     * JSON is another option. It structures the data better. You get to pick the format that works best.
     * Sometimes you need to remove special characters. This cleans up the log files.
     * For example, you might want to log errors. Text files are great for quick checks.
     * JSON files help when you need to parse data. Removing weird characters makes sure your logs are clean.
     * This helps avoid issues later on. Each method has its pros and cons. Choose the right one for your task.
     * Clean logs make debugging easier.
     * @param level The log level
     * @param message The log message
     * @param f The file path
     * @param j If the log should be in JSON format
     * @param specialCharacters If special characters should be removed
     * @param niceFormatting If the log should be formatted nicely
     * @param newLine If a new line should be added
     */
    public void logToFileProbablyAsJson(
            LoggingLevelForTheMostEfficientLoggingSystemEver level,
            String message, String f, boolean j, boolean specialCharacters,
            boolean niceFormatting, boolean newLine) {
        File file = new File(f);
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(f, true);
            if (j) {
                if (specialCharacters) {
                    if (niceFormatting) {
                        // Writes the log message to the file in a nice JSON format
                        fw.append("{\n  \"level\": \"")
                                .append(String.valueOf(level))
                                .append("\",\n  \"message\": \"")
                                .append(message).append("\"\n}");
                    } else {
                        // Writes the log message to the file in a JSON format
                        fw.append("{\"level\":\"").append(String.valueOf(level))
                                .append("\",\"message\":\"").append(message)
                                .append("\"}");
                    }
                } else {
                    if (niceFormatting) {
                        // Writes the log message to the file in a nice JSON format, removing special characters
                        fw.append("{\n  \"level\": \"")
                                .append(String.valueOf(level))
                                .append("\",\n  \"message\": \"")
                                .append(message.replaceAll("[^a-zA-Z0-9]", ""))
                                .append("\"\n}");
                    } else {
                        // Writes the log message to the file in a JSON format, removing special characters
                        fw.append("{\"level\":\"").append(String.valueOf(level))
                                .append("\",\"message\":\"")
                                .append(message.replaceAll("[^a-zA-Z0-9]", ""))
                                .append("\"}");
                    }
                }
            } else {
                if (specialCharacters) {
                    if (niceFormatting) {
                        // Writes the log message to the file in a nice text format
                        fw.append(String.valueOf(level)).append(": ")
                                .append(message);
                    } else {
                        // Writes the log message to the file in a text format
                        fw.append(String.valueOf(level)).append(":")
                                .append(message);
                    }
                } else {
                    if (niceFormatting) {
                        // Writes the log message to the file in a nice text format, removing special characters
                        fw.append(String.valueOf(level)).append(": ")
                                .append(message.replaceAll("[^a-zA-Z0-9]", ""));
                    } else {
                        // Writes the log message to the file in a text format, removing special characters
                        fw.append(String.valueOf(level)).append(":")
                                .append(message.replaceAll("[^a-zA-Z0-9]", ""));
                    }
                }
            }
            if (newLine) {
                // Writes a new line
                fw.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Logs to file and console
     * @param level
     * @param message
     * @param fileName
     * @param asJson
     * @param specialCharacters
     * @param niceFormatting
     * @param newLine
     */
    public void logToFileAndConsole(
            LoggingLevelForTheMostEfficientLoggingSystemEver level,
            String message, String fileName, boolean asJson,
            boolean specialCharacters, boolean niceFormatting,
            boolean newLine) {
        // Logs to console
        logToConsoleAsTextOrJsonWithOrWithoutSpecialCharacters(level, message,
                asJson, specialCharacters, niceFormatting, newLine);
        // Logs to file
        logToFileProbablyAsJson(level, message, fileName, asJson,
                specialCharacters, niceFormatting, newLine);
    }
}
