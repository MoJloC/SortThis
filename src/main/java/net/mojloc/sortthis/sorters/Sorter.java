package net.mojloc.sortthis.sorters;

public interface Sorter<T> {
    void sort (T[] arrayForSorting, int typeOfSort);
}
