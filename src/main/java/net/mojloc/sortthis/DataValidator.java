package net.mojloc.sortthis;

import java.util.List;

public interface DataValidator {
    List<String> validateAll(List<String> input, int typeOfSort);
    List<String> validateList(List<String> input, int typeOfSort);
}
