package net.mojloc.sortthis;

import net.mojloc.sortthis.filehandlers.DataLoader;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO Доработать вывод информации по устранённым при обработке файлов (пробелы и т.п.) ошибкам.

class DataLoadHandler {
    private final int quantityBorderForLoaders = 25;
    private final int typeOfSort;
    private final TypeOfData typeOfData;
    private final List<Path> sourceFilesPaths;
    private final ComparatorForNumbersAsStrings comparator = new ComparatorForNumbersAsStrings();
    private int lowerBoundIndex = -1;
    private String lowerBoundValue;
    private boolean lowerBoundFlag = false;
    private boolean firstFillFlag = true;
    private List<DataLoader> listOfDataLoaders = new ArrayList<>();
    private List<String> listOfPreviousValues = new ArrayList<>();
    private List<List<String>> listOfListsWithData = new ArrayList<>();

    public DataLoadHandler(int typeOfSort, TypeOfData typeOfData, List<Path> sourceFilesPath) {
        this.typeOfSort = typeOfSort;
        this.typeOfData = typeOfData;
        this.sourceFilesPaths = sourceFilesPath;
        fillListOfDataLoaders();
        prepareListForData();
    }

    private void fillListOfDataLoaders() {
        Class<?> dataLoaderClass = typeOfData.getDataLoaderClass();
        for (Path filePath : sourceFilesPaths) {
            DataLoader currentDataLoader;

            try {
                currentDataLoader = (DataLoader) Arrays.stream(dataLoaderClass.getDeclaredConstructors()).findFirst()
                        .get().newInstance(filePath, typeOfData, typeOfSort);

            } catch (InstantiationException e) {
                System.out.printf(Messages.ERROR_DATA_LOADER_CREATION.getMessage(), "InstantiationException", filePath.toString());
                continue;
            } catch (InvocationTargetException e) {
                System.out.printf(Messages.ERROR_DATA_LOADER_CREATION.getMessage(), "InvocationTargetException", filePath.toString());
                continue;
            } catch (IllegalAccessException e) {
                System.out.printf(Messages.ERROR_DATA_LOADER_CREATION.getMessage(), "IllegalAccessException", filePath.toString());
                continue;
            }

            if (!currentDataLoader.isSuccessOpenFile()) {
                continue;
            }

            listOfDataLoaders.add(currentDataLoader);
        }
    }

    private void prepareListForData() {
        for (int i = 0; i < listOfDataLoaders.size(); i++) {
            listOfListsWithData.add(new ArrayList<>());
            listOfPreviousValues.add("");
        }
    }

    List<String> loadData() {
        fillListWithData();
        firstFillFlag = false;
        boolean zeroInputDataFlag = true;

        for (int i = 0; i < listOfListsWithData.size(); i++) {
            if (listOfListsWithData.get(i).size() != 0) {
                zeroInputDataFlag = false;
                break;
            }
        }

        if (zeroInputDataFlag) {
            return new ArrayList<>();
        }

        detectLowerBoundTask();

        if (lowerBoundFlag) {
            return refillingTaskHandler(lowerBoundValue);
        } else {
            lowerBoundIndex = -1;
            String upperBoundMax = findUpperBoundMax();
            return refillingTaskHandler(upperBoundMax);
        }
    }

    private void fillListWithData() {
        for (int i = 0; i < listOfDataLoaders.size(); i++) {
            if (i == lowerBoundIndex) {
                lowerBoundIndex = -1;
                continue;
            }

            DataLoader currentDataLoader = listOfDataLoaders.get(i);

            if (currentDataLoader == null) {
                continue;
            }

            List<String> currentListWithData = listOfListsWithData.get(i);
            String previousValue = listOfPreviousValues.get(i);
            currentListWithData.addAll(currentDataLoader.loadBundle(quantityBorderForLoaders, firstFillFlag, previousValue));

            if (currentDataLoader.isEndOfFile() || currentDataLoader.isErrorReadingFile()) {
                listOfDataLoaders.set(i, null);
                System.out.printf(Messages.END_OF_WORK_WITH_FILE_MESSAGE.getMessage(), currentDataLoader.getDataFilePath());
            }
        }
    }

    private void detectLowerBoundTask() {
        String lowerBoundMax = findLowerBoundMax();

        for (int i = 0; i < listOfListsWithData.size(); i++) {
            if (i == lowerBoundIndex) {
                continue;
            }

            List<String> currentDataList = listOfListsWithData.get(i);

            if (currentDataList.size() == 0) {
                continue;
            }

            if (compareIt(lowerBoundMax,currentDataList.get(currentDataList.size() - 1)) * typeOfSort > 0) {
                lowerBoundFlag = true;
                lowerBoundValue = lowerBoundMax;
            } else {
                lowerBoundValue = null;
                lowerBoundFlag = false;
                break;
            }

        }
    }

    private String findLowerBoundMax() {
        String lowerBoundMax = "";

        for (int i = 0; i < listOfListsWithData.size(); i++) {
            if (listOfListsWithData.get(i).size() > 0) {
                lowerBoundMax = listOfListsWithData.get(i).get(0);
                break;
            }
        }

        for (int i = 0; i < listOfListsWithData.size(); i++) {
            int compareResult;
            List<String> currentListWithData = listOfListsWithData.get(i);

            if (currentListWithData.size() == 0) {
                continue;
            }

            compareResult = compareIt(lowerBoundMax, currentListWithData.get(0));

            if (compareResult * typeOfSort < 0) {
                lowerBoundMax = currentListWithData.get(0);
                lowerBoundIndex = i;
            }

        }

        return lowerBoundMax;
    }

    private List<String> refillingTaskHandler(String border) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < listOfListsWithData.size(); i++) {
            if (i == lowerBoundIndex) {
                continue;
            }

            DataLoader currentDataLoader = listOfDataLoaders.get(i);
            List<String> currentListWithData = listOfListsWithData.get(i);

            if ((currentDataLoader == null) & (currentListWithData.size() ==0)) {
                continue;
            }

            result.addAll(currentListWithData);

            if (!(currentDataLoader == null)) {

                if (currentListWithData.size() > 0) {
                    String previousValue = currentListWithData.get(currentListWithData.size() - 1);
                    listOfPreviousValues.set(i, previousValue);
                }

                result.addAll(currentDataLoader.loadUntilBorder(border, listOfPreviousValues.get(i)));

                if (currentDataLoader.isEndOfFile() || currentDataLoader.isErrorReadingFile()) {
                    listOfDataLoaders.set(i, null);
                    System.out.printf(Messages.END_OF_WORK_WITH_FILE_MESSAGE.getMessage(), currentDataLoader.getDataFilePath());
                }
            }

            currentListWithData.clear();
        }

        return result;
    }

    private String findUpperBoundMax() {
        String upperBoundMax = "";

        for (int i = 0; i < listOfListsWithData.size(); i++) {
            if (listOfListsWithData.get(i).size() > 0) {
                upperBoundMax = listOfListsWithData.get(i).get(listOfListsWithData.get(i).size()-1);
                break;
            }
        }

        for (int i = 0; i < listOfListsWithData.size(); i++) {
            int compareResult;
            List<String> currentListWithData = listOfListsWithData.get(i);

            if (currentListWithData.size() == 0) {
                continue;
            }

            compareResult = compareIt(upperBoundMax, currentListWithData.get(currentListWithData.size() - 1));

            if (compareResult * typeOfSort < 0) {
                upperBoundMax = currentListWithData.get(currentListWithData.size() - 1);
            }

        }

        return upperBoundMax;
    }

    private int compareIt(String first, String second) {
        if (typeOfData.getType().equals("strings")) {
            return first.compareTo(second);
        } else {
            return comparator.compare(first, second);
        }
    }

    public int getTypeOfSort() {
        return typeOfSort;
    }
}
