package net.mojloc.sortthis;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Temp {
    public static void main(String[] args) {
        List<Path> list = Arrays.stream(new String[]{"src\\main\\resources\\int1.txt", "src\\main\\resources\\int2.txt"
                , "src\\main\\resources\\int3.txt", "src\\main\\resources\\int4.txt"}).map(Paths::get).collect(Collectors.toList());
        DataLoadHandler dataLoadHandler = new DataLoadHandler(1, TypeOfData.NUMBERS, list);

//        System.out.println(dataLoadHandler.getListOfDataLoaders());
//        for (List<String> s : dataLoadHandler.getListOfListsWithData()) {
//            System.out.println(s);
//        }
    }
}
