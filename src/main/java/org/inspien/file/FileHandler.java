package org.inspien.file;

import java.io.*;

public class FileHandler {
    public static String read(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        fileReader.close();

        return stringBuilder.toString();
    }

    public static void write(String filePath, String fileData) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(fileData);
        fileWriter.close();
    }
}
