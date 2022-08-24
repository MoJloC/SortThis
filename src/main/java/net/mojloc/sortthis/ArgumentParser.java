package net.mojloc.sortthis;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class ArgumentParser {
    private final List<Path> sourceFilesPaths = new ArrayList<>();
    private int typeOfSort = 1;
    private String typeOfInput;
    private TypeOfData typeOfData;
    private Path targetFilePath;

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

                try {
                    targetFilePath = Paths.get(arguments[1]);
                } catch (InvalidPathException e) {
                    System.out.printf(Messages.ERROR_INCORRECT_TARGET_FILE_NAME.getMessage(), arguments[1]);
                    System.exit(5);
                }

                try {
                    sourceFilesPaths.add(Paths.get(arguments[2]));
                } catch (InvalidPathException e) {
                    System.out.printf(Messages.ERROR_INCORRECT_SOURCE_FILE_NAME.getMessage(), arguments[2]);
                    System.out.println(Messages.ERROR_NO_SOURCE_FILES_FOR_WORK);
                    System.exit(6);
                }


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

            try {
                targetFilePath = Paths.get(arguments[2]);
            } catch (InvalidPathException e) {
                System.out.printf(Messages.ERROR_INCORRECT_TARGET_FILE_NAME.getMessage(), arguments[2]);
                System.exit(5);
            }

            for (int i = 3; i < arguments.length; i++) {
                try {
                    sourceFilesPaths.add(Paths.get(arguments[i]));
                } catch (InvalidPathException e) {
                    System.out.printf(Messages.ERROR_INCORRECT_SOURCE_FILE_NAME.getMessage(), arguments[i]);
                }
            }

            if (sourceFilesPaths.size() == 0) {
                System.out.println(Messages.ERROR_NO_SOURCE_FILES_FOR_WORK);
                System.exit(6);
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

    public Path getTargetFilePath() {
        return targetFilePath;
    }

    public List<Path> getSourceFilesPaths() {
        return sourceFilesPaths;
    }
}
