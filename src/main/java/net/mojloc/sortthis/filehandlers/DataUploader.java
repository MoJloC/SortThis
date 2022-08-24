package net.mojloc.sortthis.filehandlers;

import net.mojloc.sortthis.Messages;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class DataUploader {
    private final Path targetFilePath;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public DataUploader(Path targetFilePath) {
        this.targetFilePath = targetFilePath;
        openTargetFile();
    }

    private void openTargetFile() {
        try {
            fileWriter = new FileWriter(targetFilePath.toFile());
            bufferedWriter = new BufferedWriter(fileWriter);
            System.out.printf(Messages.FILE_OPENING_STATUS_MESSAGE.getMessage(), targetFilePath, "получен.");
        } catch (IOException e) {
            System.out.printf(Messages.FILE_OPENING_STATUS_MESSAGE.getMessage(), targetFilePath, "не получен.");
            System.out.printf(Messages.ERROR_OPEN_TARGET_FILE.getMessage(), targetFilePath);
            System.exit(10);
        }
    }

    public void uploadData(String[] data) {
        for (String datum : data) {
            try {
                bufferedWriter.write(datum);
                bufferedWriter.newLine();
            } catch (IOException e) {
                System.out.printf(Messages.ERROR_WRITE_TO_TARGET_FILE.getMessage(), targetFilePath);
                System.exit(11);
            }
        }
    }

    public void closeFile() {
        try {
            bufferedWriter.close();
            fileWriter.close();
            System.out.printf(Messages.END_OF_WORK_WITH_FILE_MESSAGE.getMessage(), targetFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
