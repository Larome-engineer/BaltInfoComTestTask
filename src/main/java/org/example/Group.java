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

    public void groupWriter() throws IOException { // ОООЧЕНЬ жесткий временный костыль
        List<List<String>> linesList = fileSortingByLines.findLineGroups(Files.lines(Paths.get(filename)).toList());
        HashMap<Integer, List<List<List<String>>>> map = new HashMap<>();

        for (List<String> stringList : linesList) {
            int numOfLines = 0;
            for (String ignored : stringList) {
                numOfLines++;
            }
            if (!map.containsKey(numOfLines)) {
                List<List<String>> tmp2 = new ArrayList<>();
                tmp2.add(stringList);

                List<List<List<String>>> tmp3 = new ArrayList<>();
                tmp3.add(tmp2);

                map.put(numOfLines, tmp3);

            } else {
                List<List<String>> tmp2 = new ArrayList<>();
                tmp2.add(stringList);
                map.get(numOfLines).add(tmp2);

            }
        }

        try (FileWriter fileWriter = new FileWriter(resultFilename)) {

            int groupNumber = 1;

            fileWriter.write(numOfGroupMoreThanOne());
            fileWriter.append('\n').append('\n');

            for (int i = map.keySet().size(); i > 0; i--) {
                if(map.get(i) == null) continue;
                for (List<List<String>> listOfListLines : map.get(i)) {
                    fileWriter.write("Группа " + groupNumber);
                    fileWriter.append('\n');
                    for (List<String> listOfLines : listOfListLines) {
                        for (String line: listOfLines) {
                            fileWriter.write(line);
                            fileWriter.append('\n');
                        }
                    }
                    groupNumber++;
                    fileWriter.append('\n');
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

