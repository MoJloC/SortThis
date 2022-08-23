package net.mojloc.sortthis;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortThis {

    public static void main(String[] args) {
        args = new String[]{"-i", "-a", "src\\main\\resources\\result.txt", "src\\main\\resources\\int1.txt"
                           , "src\\main\\resources\\int2.txt", "src\\main\\resources\\int3.txt", "src\\main\\resources\\int4.txt"};
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parse(args);
        TypeOfData typeOfData = argumentParser.getTypeOfData();
        Sorter sorter = typeOfData.getSorter();
        DataValidator dataValidator = typeOfData.getDataValidator();
        int typeOfSort = argumentParser.getTypeOfSort();
        Path outputFilePath = argumentParser.getOutputFilePath();
        List<Path> inputFilesPaths = argumentParser.getInputFilesPaths();
        DataLoadHandler dataLoadHandler = new DataLoadHandler(typeOfSort, typeOfData, inputFilesPaths);

        try (FileReader fileReader = new FileReader(inputFilesPaths.get(3).toFile());
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            List<String> inputList = bufferedReader.lines().collect(Collectors.toList());
            System.out.println(inputList);
            List<?> tempList = dataValidator.validateAll(inputList, typeOfSort);
            System.out.println(tempList.toString());
        } catch (FileNotFoundException exception) {
            System.out.println("File not found. Aborting");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Integer[] array1 = new Integer[]{-2, 1, 3, 5, 13, -2, -4, -4, 50, 60, 32, 1, 2, 3, 4, 7, 0, 1, 10};
        String[] array2 = Arrays.stream(array1).map(String::valueOf).toArray(String[]::new);
        Arrays.sort(array1);
        System.out.println(Arrays.toString(array1));
        sorter.sort(array2, typeOfSort);
        System.out.println(Arrays.toString(array2));



//        String[] array1 = new String[]{"sdgahg", "asdga25", "20948", "adfhadh", ";khj", "l;ihjafh", "sjghdfhg"
//                , "a[pe,vy", "dmghfydskf", "s,djdhgjd"};
//        String[] array2 = new String[]{"sdgahg", "asdga25", "20948", "adfhadh", ";khj", "l;ihjafh", "sjghdfhg"
//                , "a[pe,vy", "dmghfydskf", "s,djdhgjd"};
//        Arrays.sort(array1);
//        System.out.println(Arrays.toString(array1));
//        sorter.sort(array2, typeOfSort);
//        System.out.println(Arrays.toString(array2));
    }
}