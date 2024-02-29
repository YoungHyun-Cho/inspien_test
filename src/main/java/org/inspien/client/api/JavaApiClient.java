package org.inspien.client.api;

import org.inspien.enums.CharSet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/*
* # JavaApiClient.class
*   - Java Network API를 활용한 APIClient 클래스
* */

public class JavaApiClient implements ApiClient {

    private static final Integer CONN_TIMEOUT = 5000;
    private static final Integer READ_TIMEOUT = 3000;

    // 인스피언의 API 서버로 요청을 보내고, 응답을 문자열로 변환하여 리턴한다.
    @Override
    public String sendApiRequest(String data, String apiUrl) throws IOException {

        URL url = new URL(apiUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        setConnProperties(urlConnection);

        OutputStream outputStream = urlConnection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, CharSet.UTF_8.getName());
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(data);
        bufferedWriter.flush();

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream(), CharSet.UTF_8.getName());
            bufferedReader = new BufferedReader(inputStreamReader);

            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(readLine).append("\n");
            }
        }
        else recordErrorInfo(stringBuilder, urlConnection);

        closeAll(bufferedWriter, bufferedReader, outputStream);

        return stringBuilder.toString();
    }

    // 연결에 필요한 설정 정보를 설정한다.
    private void setConnProperties(HttpURLConnection urlConnection) throws ProtocolException {
        urlConnection.setRequestMethod("POST");
        urlConnection.setConnectTimeout(CONN_TIMEOUT);
        urlConnection.setReadTimeout(READ_TIMEOUT);
        urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        urlConnection.setDoOutput(true);
    }

    // API 요청 실패 시 인스피언의 API 서버가 제공한 정보를 문자열에 삽입한다.
    private void recordErrorInfo(StringBuilder stringBuilder, HttpURLConnection urlConnection) throws IOException {
        stringBuilder.append("code : ");
        stringBuilder.append(urlConnection.getResponseCode()).append("\n");
        stringBuilder.append("message : ");
        stringBuilder.append(urlConnection.getResponseMessage()).append("\n");
    }

    // 모든 자원을 닫는다.
    private void closeAll(BufferedWriter bufferedWriter, BufferedReader bufferedReader, OutputStream outputStream) throws IOException {
        if (bufferedWriter != null) bufferedWriter.close();
        if (bufferedReader != null) bufferedReader.close();
        if (outputStream != null) outputStream.close();
    }
}