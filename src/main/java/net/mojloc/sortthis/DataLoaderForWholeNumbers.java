package net.mojloc.sortthis;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataLoaderForWholeNumbers extends DataLoader {
    DataLoaderForWholeNumbers(Path dataFilePath, DataLoadHandler dataLoadHandler) {
        super(dataFilePath, dataLoadHandler);
    }

    @Override
    List<String> loadUntilBorder(String border, int maxQuantity) {
        ComparatorForNumbersAsStrings comparator = new ComparatorForNumbersAsStrings();
        List<String> result = new ArrayList<>();

        for (int i = 0; i < maxQuantity; i++) {
            String currentString;
            try {
                bufferedReader.mark(1 << 24);
                currentString = bufferedReader.readLine();

                if (currentString == null) {
                    bufferedReader.close();
                    fileReader.close();
                    dataLoadHandler.removeDataLoaderFromList(this);
                    break;
                }

                if (currentString.contains(" ")) {
                    currentString = currentString.replace(" ", "");
                }

                if (!currentString.matches("-?\\d+")) {
                    continue;
                }

                if (comparator.compare(currentString, border)*typeOfSort <= 0) {
                    result.add(currentString);
                } else {
                    bufferedReader.reset();
                    break;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return dataValidator.validateList(result, typeOfSort);
    }
}
