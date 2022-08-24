package net.mojloc.sortthis;

import java.nio.file.Path;
import java.util.List;

public class SortThis {

    public static void main(String[] args) {
        //TODO remove args from method body after debugging
//        args = new String[]{"-i", "-a", "src\\main\\resources\\result.txt", "src\\main\\resources\\int1.txt"
//                           , "src\\main\\resources\\int2.txt", "src\\main\\resources\\int3.txt", "src\\main\\resources\\int4.txt"};

//        args = new String[]{"-i", "-d", "src\\main\\resources\\result1.txt", "src\\main\\resources\\int5.txt"
//                , "src\\main\\resources\\int6.txt", "src\\main\\resources\\int7.txt", "src\\main\\resources\\int8.txt"};

        args = new String[]{"-s", "-a", "src\\main\\resources\\result2.txt", "src\\main\\resources\\str1.txt"
                , "src\\main\\resources\\str2.txt", "src\\main\\resources\\str3.txt", "src\\main\\resources\\str4.txt"};

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