package net.mojloc.sortthis;

public enum TypeOfData {

    STRINGS ("strings", new MergeSortForStrings(), new DataValidatorForStrings(), DataLoaderForStrings.class),
    NUMBERS ("numbers", new MergeSortForWholeNumbers(), new DataValidatorForWholeNumbers(), DataLoaderForWholeNumbers.class);

    private final String type;
    private final Sorter sorter;
    private final DataValidator dataValidator;
    private final Class dataLoaderClass;

    TypeOfData(String type, Sorter sorter, DataValidator dataValidator, Class dataLoaderClass) {
        this.type = type;
        this.sorter = sorter;
        this.dataValidator = dataValidator;
        this.dataLoaderClass = dataLoaderClass;
    }

    public String getType() {
        return type;
    }

    public Sorter getSorter() {
        return sorter;
    }

    public DataValidator getDataValidator() {
        return dataValidator;
    }

    public Class getDataLoaderClass() {
        return dataLoaderClass;
    }
}
