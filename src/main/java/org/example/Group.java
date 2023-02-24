package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Group { // Number of group with more than 1 element

    private final String filename;
    private final String resultFilename;

    public Group(String filename, String resultFilename) {
        this.filename = filename;
        this.resultFilename = resultFilename;
    }

    private final FileSortingByLines fileSortingByLines = new FileSortingByLines();


    public String numOfGroupMoreThanOne() {
        int countOfGroup = 0;

        try {
            List<List<String>> linesList = fileSortingByLines.findLineGroups(Files.lines(Paths.get(filename)).toList());

            for (List<String> strings : linesList) {

                int numOfLines = 0;
                for (String ignored : strings) {
                    numOfLines++;
                }
                if (numOfLines > 1) {
                    countOfGroup++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Количество групп с более чем одним элементом: " + countOfGroup;
    }

    public void groupWriter() throws IOException { // Временный костыль
        List<List<String>> linesList = fileSortingByLines.findLineGroups(Files.lines(Paths.get(filename)).toList());
        List<List<String>> newList = new ArrayList<>();
        List<List<String>> newList2 = new ArrayList<>();
        List<List<String>> newList3 = new ArrayList<>();

        HashMap<Integer, List<List<String>>> map = new HashMap<>();

        for (List<String> stringList : linesList) {
            int numOfLines = 0;
            for (String ignored : stringList) {
                numOfLines++;
            }
            if (numOfLines == 1) {
                newList.add(stringList);
            } else if (numOfLines == 2) {
                newList2.add(stringList);
            } else {
                newList3.add(stringList);
            }
        }

        try (FileWriter fileWriter = new FileWriter(resultFilename)) {

            int groupNumber = 0;

            fileWriter.write(numOfGroupMoreThanOne());
            fileWriter.append('\n').append('\n');


            for (int i = 0; i < newList3.size(); i++) {
                groupNumber++;
                fileWriter.write("Группа " + groupNumber);
                fileWriter.append('\n');

                for (String line : newList3.get(i)) {
                    fileWriter.write(line);
                    fileWriter.append('\n');
                }
                fileWriter.append('\n');
            }

            for (int i = 0; i < newList2.size(); i++) {
                groupNumber++;
                fileWriter.write("Группа " + groupNumber);
                fileWriter.append('\n');

                for (String line : newList2.get(i)) {
                    fileWriter.write(line);
                    fileWriter.append('\n');
                }
                fileWriter.append('\n');
            }

            for (List<String> strings : newList) {
                groupNumber++;
                fileWriter.write("Группа " + groupNumber);
                fileWriter.append('\n');

                for (String line : strings) {
                    fileWriter.write(line);
                    fileWriter.append('\n');
                }
                fileWriter.append('\n');
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

