package net.mojloc.sortthis.validators;

import net.mojloc.sortthis.ComparatorForNumbersAsStrings;

import java.util.ArrayList;
import java.util.List;

public class DataValidatorForWholeNumbers implements DataValidator {
    private final ComparatorForNumbersAsStrings comparator = new ComparatorForNumbersAsStrings();
    private int sortingErrorCount;
    private int spaceErrorCount;
    private int nonIntegerErrorCount;

    @Override
    public List<String> validateAll(List<String> input, int typeOfSort, boolean firstFillFlag, String previousValue) {
        List<String> tempList = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < input.size(); i++) {
            String tempString = input.get(i);
            if (tempString.contains(" ")) {
                tempString = tempString.replace(" ", "");
                spaceErrorCount++;
            }

            if (!tempString.matches("-?\\d+")) {
                nonIntegerErrorCount++;
                continue;
            }

            if (tempList.size() == 0) {
                if (!firstFillFlag) {
                    if (comparator.compare(tempString, previousValue) * typeOfSort < 0) {
                        continue;
                    }
                }
                if (i == (input.size() - 1)) {
                    tempList.add(tempString);
                    continue;
                }
                tempList.add(tempString);
                counter++;
                continue;
            } else if (tempList.size() == 1) {
                if (comparator.compare(tempList.get(0), tempString) * typeOfSort > 0) {
                    sortingErrorCount++;
                    continue;
                }
            } else if (comparator.compare(tempList.get(counter - 1), tempString) * typeOfSort > 0) {
                sortingErrorCount++;
                continue;
            }

            if (comparator.compare(tempList.get(counter - 1), tempString) * typeOfSort <= 0) {
                tempList.add(tempString);
                counter++;
            }
        }

        return tempList;
    }

    @Override
    public List<String> validateList(List<String> input, int typeOfSort, String previousValue) {
        List<String> tempList = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < input.size(); i++) {
            String tempString = input.get(i);

            if (comparator.compare(tempString, previousValue) * typeOfSort < 0) {
                continue;
            }

            if (tempList.size() == 0) {
                if (comparator.compare(tempString, previousValue) * typeOfSort < 0) {
                    continue;
                }
                tempList.add(tempString);
                counter++;
                continue;
            } else if (tempList.size() == 1) {
                if (comparator.compare(tempList.get(0), tempString) * typeOfSort > 0) {
                    sortingErrorCount++;
                    continue;
                }
            } else if (comparator.compare(tempList.get(counter - 1), tempString) * typeOfSort > 0) {
                sortingErrorCount++;
                continue;
            }

            if (comparator.compare(tempList.get(counter - 1), tempString) * typeOfSort <= 0) {
                tempList.add(tempString);
                counter++;
            }
        }

        return tempList;
    }
}
