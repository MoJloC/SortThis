package net.mojloc.sortthis.filehandlers;

import net.mojloc.sortthis.validators.DataValidator;
import net.mojloc.sortthis.Messages;
import net.mojloc.sortthis.TypeOfData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public abstract class DataLoader {
    private final Path dataFilePath;
    protected final int typeOfSort;
    protected DataValidator dataValidator;
    protected FileReader fileReader;
    protected BufferedReader bufferedReader;
    protected TypeOfData typeOfData;
    protected boolean endOfFile;
    protected boolean errorReadingFile;
    private boolean successOpenFile;

    public DataLoader(Path dataFilePath, TypeOfData typeOfData, int typeOfSort) {
        this.dataFilePath = dataFilePath;
        this.typeOfData = typeOfData;
        this.typeOfSort = typeOfSort;
        dataValidator = typeOfData.getDataValidator();
        openFile();
    }

    private void openFile() {
        try {
            fileReader = new FileReader(dataFilePath.toFile());
            bufferedReader = new BufferedReader(fileReader, 1 << 24);
            successOpenFile = true;
            System.out.printf(Messages.FILE_OPENING_STATUS_MESSAGE.getMessage(), dataFilePath, "получен.");
        } catch (IOException e) {
            System.out.printf(Messages.FILE_OPENING_STATUS_MESSAGE.getMessage(), dataFilePath, "не получен.");
        }
    }

    public  abstract List<String> loadBundle(int quantity, boolean firstFillFlag, String previousValue);

    public abstract List<String> loadUntilBorder(String border, String previousValue);

    public boolean isSuccessOpenFile() {
        return successOpenFile;
    }

    public boolean isEndOfFile() {
        return endOfFile;
    }

    public boolean isErrorReadingFile() {
        return errorReadingFile;
    }

    public Path getDataFilePath() {
        return dataFilePath;
    }
}
