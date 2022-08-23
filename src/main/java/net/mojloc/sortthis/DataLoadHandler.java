package net.mojloc.sortthis;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO 1. Вывод ошибок при обработке файлов (пробелы и т.п.) запилить выводом в консоль по результату выполнения задачи. 2. Заменить quantityBorderForLoaders.

class DataLoadHandler {
    private final int quantityBorderForLoaders = 25;
    private final int typeOfSort;
    private final TypeOfData typeOfData;
    private final List<Path> inputFilesPaths;
    private boolean lowerBoundFlag = false;
    private boolean upperBoundFlag = false;
    private String boundValue;
    private List<DataLoader> listOfDataLoaders = new ArrayList<>();
    private List<List<String>> listOfListsWithData = new ArrayList<>();

    public DataLoadHandler(int typeOfSort, TypeOfData typeOfData, List<Path> inputFilesPaths) {
        this.typeOfSort = typeOfSort;
        this.typeOfData = typeOfData;
        this.inputFilesPaths = inputFilesPaths;
        fillListOfDataLoaders();
        fillListWithData();
    }

    private void fillListOfDataLoaders() {
        Class dataLoaderClass = typeOfData.getDataLoaderClass();
        for (Path filePath : inputFilesPaths) {
            DataLoader currentDataLoader;

            try {
                currentDataLoader = (DataLoader) Arrays.stream(dataLoaderClass.getDeclaredConstructors()).findFirst()
                                                       .get().newInstance(filePath, this);

            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (!currentDataLoader.isSuccessOpenFile()) {
                continue;
            }

            listOfDataLoaders.add(currentDataLoader);
        }

        System.out.println(listOfDataLoaders + "\n");
    }

    private void fillListWithData() {
        System.out.println("listOfDataLoaders.size(): " + listOfDataLoaders.size());
        for (int i = 0; i < listOfDataLoaders.size(); i++) {
            System.out.println("i = " + i);
            listOfListsWithData.add(listOfDataLoaders.get(i).loadBundle(quantityBorderForLoaders));
            System.out.println(listOfListsWithData.get(i).toString());
        }
        System.out.println("listOfListsWithData.size(): " + listOfListsWithData.size());
    }

    void removeDataLoaderFromList (DataLoader dataLoader) {
        listOfDataLoaders.remove(dataLoader);
    }

    List<String> loadData() {
        List<String> resultList = new ArrayList<>();



        return resultList;
    }

//    private List<String> lowerBoundHandler


    public int getTypeOfSort() {
        return typeOfSort;
    }

    public TypeOfData getTypeOfData() {
        return typeOfData;
    }

    public List<DataLoader> getListOfDataLoaders() {
        return listOfDataLoaders;
    }

    public List<List<String>> getListOfListsWithData() {
        return listOfListsWithData;
    }
}
