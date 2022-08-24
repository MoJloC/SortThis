package net.mojloc.sortthis;

import net.mojloc.sortthis.filehandlers.DataUploader;
import net.mojloc.sortthis.sorters.Sorter;

import java.nio.file.Path;
import java.util.List;

public class SortTaskHandler {
    private final int typeOfSort;
    private final TypeOfData typeOfData;
    private final Path targetFilePath;
    private final List<Path> sourceFilesPaths;
    private final Sorter sorter;

    public SortTaskHandler(int typeOfSort, TypeOfData typeOfData, Path targetFilePath, List<Path> sourceFilesPaths) {
        this.typeOfSort = typeOfSort;
        this.typeOfData = typeOfData;
        this.targetFilePath = targetFilePath;
        this.sourceFilesPaths = sourceFilesPaths;
        sorter = typeOfData.getSorter();
    }

    void doTask () {
        DataUploader dataUploader = new DataUploader(targetFilePath);
        DataLoadHandler dataLoadHandler = new DataLoadHandler(typeOfSort,typeOfData, sourceFilesPaths);
        List<String> listForCurrentBundleOfData;
        String[] arrayForCurrentBundleOfData;

        for (;;) {
            listForCurrentBundleOfData = dataLoadHandler.loadData();

            if (listForCurrentBundleOfData.size() == 0) {
                dataUploader.closeFile();
                System.out.printf(Messages.WORK_IS_FINISHED.getMessage(), targetFilePath);
                break;
            }

            arrayForCurrentBundleOfData = listForCurrentBundleOfData.toArray(new String[0]);
            sorter.sort(arrayForCurrentBundleOfData, typeOfSort);
            dataUploader.uploadData(arrayForCurrentBundleOfData);
        }

    }
}
