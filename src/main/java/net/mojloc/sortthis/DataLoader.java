package net.mojloc.sortthis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

abstract class DataLoader {
    private final Path dataFilePath;
    protected final int typeOfSort;
    protected DataValidator dataValidator;
    protected FileReader fileReader;
    protected BufferedReader bufferedReader;
    protected DataLoadHandler dataLoadHandler;
    private boolean successOpenFile;

    DataLoader(Path dataFilePath, DataLoadHandler dataLoadHandler) {
        this.dataFilePath = dataFilePath;
        this.dataLoadHandler = dataLoadHandler;
        typeOfSort = dataLoadHandler.getTypeOfSort();
        dataValidator = dataLoadHandler.getTypeOfData().getDataValidator();
        openFile();
    }

    private void openFile() {
        try {
            fileReader = new FileReader(dataFilePath.toFile());
            bufferedReader = new BufferedReader(fileReader);
            successOpenFile = true;
            System.out.printf(Messages.FILE_OPENING_STATUS_MESSAGE.getMessage(), dataFilePath, "получен.");
        } catch (IOException e) {
            System.out.printf(Messages.FILE_OPENING_STATUS_MESSAGE.getMessage(), dataFilePath, "не получен.");
        }
    }

    abstract List<String> loadUntilBorder(String border, int maxQuantity);

    boolean isSuccessOpenFile() {
        return successOpenFile;
    }

    List<String> loadBundle(int quantity) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            String currentString;

            try {
                currentString = bufferedReader.readLine();
                if (currentString == null) {
                    bufferedReader.close();
                    fileReader.close();
                    dataLoadHandler.removeDataLoaderFromList(this);
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            result.add(currentString);
        }

        return dataValidator.validateAll(result, typeOfSort);
    }
}
