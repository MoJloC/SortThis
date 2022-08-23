package net.mojloc.sortthis;

class MergeSortForStrings implements Sorter<String> {
    private int typeOfSort;

    @Override
    public void sort(String[] arrayForSorting, int typeOfSort){
        String[] arraySorted = new String[arrayForSorting.length];
        this.typeOfSort = typeOfSort;
        recursionForMergeSort(arrayForSorting, arraySorted, 0, (arraySorted.length-1));
    }

    private void recursionForMergeSort (String[] arrayForSort, String[] arraySorted, int startOfPart, int endOfPart) {
        int startOfPart1, endOfPart1, startOfPart2, endOfPart2;
        if ((endOfPart-startOfPart) <= 0 ) {
            return;
        }
        else {
            startOfPart1 = startOfPart;
            endOfPart1 = startOfPart1+(endOfPart-startOfPart1)/2;
            startOfPart2 = (endOfPart1+1);
            endOfPart2 = endOfPart;
            recursionForMergeSort(arrayForSort, arraySorted, startOfPart1, endOfPart1);
            recursionForMergeSort(arrayForSort, arraySorted, startOfPart2, endOfPart2);
            arrayMerge(arrayForSort, arraySorted,startOfPart, endOfPart);
        }
    }

    private void arrayMerge (String[] arrayUnsorted, String[] arrayAfterMerge, int start, int end) {
        int i =0 , j = 0, k = 0;
        int middle = (start+((end-start)/2)+1);
        while (((start + i) < middle) & ((middle + j) <= end)) {
            if (typeOfSort*(arrayUnsorted[start + i].compareTo(arrayUnsorted[middle + j])) <= 0) {
                arrayAfterMerge[start + k] = arrayUnsorted[start+i];
                k++;
                i++;
            }
            else {
                arrayAfterMerge[start + k] = arrayUnsorted[middle+j];
                k++;
                j++;
            }
        }
        while ((start + i) < middle) {
            arrayAfterMerge[start + k] = arrayUnsorted[start+i];
            k++;
            i++;
        }
        while ((middle + j) <= end) {
            arrayAfterMerge[start + k] = arrayUnsorted[middle+j];
            k++;
            j++;
        }

        for (int n = start; n <= end; n++) {
            arrayUnsorted[n] = arrayAfterMerge[n];
        }
    }
}
