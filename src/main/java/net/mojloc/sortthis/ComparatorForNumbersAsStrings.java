package net.mojloc.sortthis;

public class ComparatorForNumbersAsStrings {
    public int compare(String numberAsStringOne, String numberAsStringTwo) {
        int result;

        if (numberAsStringOne.startsWith("-") & numberAsStringTwo.startsWith("-")) {
            String temp1 = numberAsStringOne;
            String temp2 = numberAsStringTwo;
            temp1 = temp1.replace("-","");
            temp2 = temp2.replace("-","");
            result = (-1)*compareNumbersInString(temp1, temp2);
        } else if (numberAsStringOne.startsWith("-")) {
            result = -1;
        } else if (numberAsStringTwo.startsWith("-")) {
            result = 1;
        } else {
            result = compareNumbersInString(numberAsStringOne, numberAsStringTwo);
        }

        return result;
    }

    private int compareNumbersInString(String first, String second) {
        int result = 0;
        String[] firstArray = first.split("");
        String[] secondArray = second.split("");

        if (first.compareTo(second)==0) {
            result = 0;
        } else if (firstArray.length > secondArray.length) {
            result = 1;
        } else if (firstArray.length < secondArray.length) {
            result = -1;
        } else {
            for (int i = 0; i < firstArray.length; i++) {
                int numberFromFirst = Integer.parseInt(firstArray[i]);
                int numberFromSecond = Integer.parseInt(secondArray[i]);

                if (numberFromFirst == numberFromSecond) {
                    continue;
                } else if (numberFromFirst > numberFromSecond) {
                    result = 1;
                    break;
                } else {
                    result = -1;
                    break;
                }
            }
        }

        return result;
    }
}
