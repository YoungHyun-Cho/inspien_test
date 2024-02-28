package org.inspien.util;

import java.io.*;

/*
* # FileHandler.class
*   - 파일 입출력을 담당하는 유틸리티 클래스
*       - 파일 입력 : String read(String filePath)
*       - 파일 출력 : void write(String path, String content)
* */

public class FileHandler {

    // 파일 경로를 입력 받아 파일을 불러온 다음, 파일의 내용을 문자열로 리턴한다.
    public static String read(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        bufferedReader.close();
        fileReader.close();

        return stringBuilder.toString();
    }

    // 데이터를 입력 받아 파일로 출력한다.
    public static void write(String path, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(content);

        bufferedWriter.close();
        fileWriter.close();
    }
}
