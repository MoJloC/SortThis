package net.mojloc.sortthis.validators;

import java.util.ArrayList;
import java.util.List;

public class DataValidatorForStrings implements DataValidator {
    private int sortingErrorCount;
    private int spaceErrorCount;
    private int emptyStringCount;

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

            if (tempString.equals("")) {
                emptyStringCount++;
                continue;
            }

            if (tempList.size() == 0) {
                if (!firstFillFlag) {
                    if (tempString.compareTo(previousValue) * typeOfSort < 0) {
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
                if ((tempList.get(0).compareTo(tempString)) * typeOfSort > 0) {
                    sortingErrorCount++;
                    continue;
                }
            } else if ((tempList.get(counter - 1).compareTo(tempString)) * typeOfSort > 0) {
                sortingErrorCount++;
                continue;
            }

            if ((tempList.get(counter - 1).compareTo(tempString)) * typeOfSort <= 0) {
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

            if (tempString.compareTo(previousValue) * typeOfSort < 0) {
                continue;
            }

            if (tempList.size() == 0) {
                if (tempString.compareTo(previousValue) * typeOfSort < 0) {
                    continue;
                }
                tempList.add(tempString);
                counter++;
                continue;
            } else if (tempList.size() == 1) {
                if ((tempList.get(0).compareTo(tempString)) * typeOfSort > 0) {
                    sortingErrorCount++;
                    continue;
                }
            } else if ((tempList.get(counter - 1).compareTo(tempString)) * typeOfSort > 0) {
                sortingErrorCount++;
                continue;
            }

            if ((tempList.get(counter - 1).compareTo(tempString)) * typeOfSort <= 0) {
                tempList.add(tempString);
                counter++;
            }
        }

        return tempList;
    }
}
