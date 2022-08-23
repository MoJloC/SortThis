package net.mojloc.sortthis;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class ArgumentParser {
    private final List<Path> inputFilesPaths = new ArrayList<>();
    private int typeOfSort = 1;
    private String typeOfInput;
    private TypeOfData typeOfData;
    private Path outputFilePath;

    void parse(String[] arguments) {

        if (arguments.length < 3) {
            System.out.println(Messages.ERROR_NOT_ENOUGH_ARGUMENTS.getMessage() + "\n" + Messages.HELP_MESSAGE.getMessage());
            System.exit(1);
        } else {
            try {
                Set<String> verificationSet = Set.of(arguments);

                if (verificationSet.contains("-s") & verificationSet.contains("-i")) {
                    System.out.println(Messages.ERROR_MULTI_TYPE_OF_INPUT.getMessage() + "\n"
                                       + Messages.HELP_MESSAGE.getMessage());
                    System.exit(4);
                }

                if (verificationSet.contains("-d") & verificationSet.contains("-a")) {
                    System.out.println(Messages.ERROR_MULTI_TYPE_OF_SORTING.getMessage() + "\n"
                                      + Messages.HELP_MESSAGE.getMessage());
                    System.exit(4);
                }

            } catch (IllegalArgumentException exception) {
                System.out.println(Messages.ERROR_DOUBLING_OF_ARGUMENTS.getMessage() + "\n"
                                  + Messages.HELP_MESSAGE.getMessage());
                System.exit(2);
            }

            if (arguments.length==3) {
                if (arguments[0].equals("-i")) {
                    typeOfInput = "numbers";
                } else if (arguments[0].equals("-s")) {
                    typeOfInput = "strings";
                } else {
                    System.out.println(Messages.ERROR_TYPE_OF_DATA_NOT_SPECIFIED.getMessage() + "\n");
                    System.exit(3);
                }
                outputFilePath = Paths.get(arguments[1]);
                inputFilesPaths.add(Paths.get(arguments[2]));
            }

            if (arguments[0].equals("-d") || arguments[1].equals("-d")) {
                typeOfSort = -1;
            }

            if (arguments[0].equals("-i") || arguments[1].equals("-i")) {
                typeOfInput = "numbers";

            } else if (arguments[0].equals("-s") || arguments[1].equals("-s")) {
                typeOfInput = "strings";
            } else {
                System.out.println(Messages.ERROR_TYPE_OF_DATA_NOT_SPECIFIED.getMessage() + "\n");
                System.exit(3);
            }

            outputFilePath = Paths.get(arguments[2]);

            for (int i = 3; i < arguments.length; i++) {
                inputFilesPaths.add(Paths.get(arguments[i]));
            }

            typeOfData = Arrays.stream(TypeOfData.values()).filter(c -> c.getType().contains(typeOfInput)).findFirst().get();
        }
    }

    public int getTypeOfSort() {
        return typeOfSort;
    }

    public TypeOfData getTypeOfData() {
        return typeOfData;
    }

    public Path getOutputFilePath() {
        return outputFilePath;
    }

    public List<Path> getInputFilesPaths() {
        return inputFilesPaths;
    }
}
