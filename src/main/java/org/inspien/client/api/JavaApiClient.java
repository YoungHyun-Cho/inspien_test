package org.inspien.client.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class JavaApiClient implements ApiClient {

    private static final Integer CONN_TIMEOUT = 5000;
    private static final Integer READ_TIMEOUT = 3000;

    @Override
    public String sendApiRequest(String data, String apiUrl) throws IOException {
        URL url = null;
        String readLine = null;
        StringBuilder buffer = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        HttpURLConnection urlConnection = null;

        url = new URL(apiUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setConnectTimeout(CONN_TIMEOUT);
        urlConnection.setReadTimeout(READ_TIMEOUT);
        urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        urlConnection.setDoOutput(true);

        outputStream = urlConnection.getOutputStream();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        bufferedWriter.write(data);
        bufferedWriter.flush();

        buffer = new StringBuilder();
        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((readLine = bufferedReader.readLine()) != null) {
                buffer.append(readLine).append("\n");
            }
        }
        else {
            buffer.append("code : ");
            buffer.append(urlConnection.getResponseCode()).append("\n");
            buffer.append("message : ");
            buffer.append(urlConnection.getResponseMessage()).append("\n");
        }

        if (bufferedWriter != null) bufferedWriter.close();
        if (bufferedReader != null) bufferedReader.close();
        if (outputStream != null) outputStream.close();

        return buffer.toString();
    }
}