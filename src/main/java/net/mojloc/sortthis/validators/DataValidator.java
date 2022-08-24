package net.mojloc.sortthis.validators;

import java.util.List;

public interface DataValidator {
    List<String> validateAll(List<String> input, int typeOfSort, boolean firstFillFlag, String previousValue);
    List<String> validateList(List<String> input, int typeOfSort, String previousValue);
}
