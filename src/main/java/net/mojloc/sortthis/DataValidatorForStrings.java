package net.mojloc.sortthis;

import java.util.ArrayList;
import java.util.List;

class DataValidatorForStrings implements DataValidator {
    private int sortingErrorCount;
    private int spaceErrorCount;

    @Override
    public List<String> validateAll(List<String> input, int typeOfSort) {
        List<String> tempList = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < input.size(); i++) {
            String tempString = input.get(i);
            if (tempString.contains(" ")) {
                tempString = tempString.replace(" ","");
                spaceErrorCount++;
            }

            if (tempList.size()==0) {
                if (i == (input.size()-1)) {
                    tempList.add(tempString);
                    continue;
                }
                tempList.add(tempString);
                counter++;
                continue;
            } else if (tempList.size()==1) {
                if ((tempList.get(0).compareTo(tempString))*typeOfSort > 0) {
                    tempList.set(0, tempString);
                    sortingErrorCount++;
                    continue;
                }
            } else if ((tempList.get(counter-1).compareTo(tempString))*typeOfSort > 0) {
                sortingErrorCount++;
                continue;
            }

            if ((tempList.get(counter-1).compareTo(tempString))*typeOfSort <= 0) {
                tempList.add(tempString);
                counter++;
            }
        }

        return tempList;
    }

    @Override
    public List<String> validateList(List<String> input, int typeOfSort) {
        List<String> tempList = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < input.size(); i++) {
            String tempString = input.get(i);

            if (tempList.size()==0) {
                if (i == (input.size()-1)) {
                    tempList.add(tempString);
                    continue;
                }
                tempList.add(tempString);
                counter++;
                continue;
            } else if (tempList.size()==1) {
                if ((tempList.get(0).compareTo(tempString))*typeOfSort > 0) {
                    tempList.set(0, tempString);
                    sortingErrorCount++;
                    continue;
                }
            } else if ((tempList.get(counter-1).compareTo(tempString))*typeOfSort > 0) {
                sortingErrorCount++;
                continue;
            }

            if ((tempList.get(counter-1).compareTo(tempString))*typeOfSort <= 0) {
                tempList.add(tempString);
                counter++;
            }
        }

        return tempList;
    }
}
