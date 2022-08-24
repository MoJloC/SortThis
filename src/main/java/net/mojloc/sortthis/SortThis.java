package net.mojloc.sortthis;

import java.nio.file.Path;
import java.util.List;

public class SortThis {

    public static void main(String[] args) {
        System.out.println(Messages.START_MESSAGE.getMessage());

        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parse(args);
        int typeOfSort = argumentParser.getTypeOfSort();
        TypeOfData typeOfData = argumentParser.getTypeOfData();
        Path targetFilePath = argumentParser.getTargetFilePath();
        List<Path> sourceFilesPaths = argumentParser.getSourceFilesPaths();

        SortTaskHandler sortTaskHandler = new SortTaskHandler(typeOfSort, typeOfData, targetFilePath, sourceFilesPaths);
        sortTaskHandler.doTask();
    }
}