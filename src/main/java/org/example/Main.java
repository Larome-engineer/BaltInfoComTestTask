package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{

        Group group = new Group(args[0], "D:\\test.txt"); // Файл test - локальный файл на ПК для записи результата по группам
        long start = System.currentTimeMillis();

        group.groupWriter();

        long end = System.currentTimeMillis();
        System.out.println("Program execution time: " + ((double) end - start)/1000 + " seconds");

    }
}
