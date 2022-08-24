package net.mojloc.sortthis.filehandlers;

import net.mojloc.sortthis.ComparatorForNumbersAsStrings;
import net.mojloc.sortthis.Messages;
import net.mojloc.sortthis.TypeOfData;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataLoaderForWholeNumbers extends DataLoader {
    private final ComparatorForNumbersAsStrings comparator = new ComparatorForNumbersAsStrings();
    public DataLoaderForWholeNumbers(Path dataFilePath, TypeOfData typeOfData, int typeOfSort) {
        super(dataFilePath, typeOfData, typeOfSort);
    }

    @Override
    public List<String> loadBundle(int quantity, boolean firstFillFlag, String previousValue) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            String currentString;

            try {
                currentString = bufferedReader.readLine();

                if (currentString == null) {
                    try {
                        bufferedReader.close();
                        fileReader.close();
                        endOfFile = true;
                        break;
                    } catch (IOException e) {
                        System.out.println(Messages.ERROR_CLOSING_FILE);
                        endOfFile = true;
                        break;
                    }
                }

                if (currentString.equals("")) {
                    continue;
                }

            } catch (IOException e) {
                System.out.println(Messages.ERROR_READING_FROM_FILE.getMessage());
                errorReadingFile = true;
                break;
            }

            result.add(currentString);
        }

        return dataValidator.validateAll(result, typeOfSort, firstFillFlag, previousValue);
    }

    @Override
    public List<String> loadUntilBorder(String border, String previousValue) {
        List<String> result = new ArrayList<>();

        for (;;) {
            String currentString;
            try {
                bufferedReader.mark(1 << 23);
                currentString = bufferedReader.readLine();

                if (currentString == null) {
                    try {
                        bufferedReader.close();
                        fileReader.close();
                        endOfFile = true;
                        break;
                    } catch (IOException e) {
                        System.out.println(Messages.ERROR_CLOSING_FILE);
                        endOfFile = true;
                        break;
                    }
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
                System.out.println(Messages.ERROR_READING_FROM_FILE.getMessage());
                errorReadingFile = true;
                break;
            }
        }

        return dataValidator.validateList(result, typeOfSort, previousValue);
    }
}
