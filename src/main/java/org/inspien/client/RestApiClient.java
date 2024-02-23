package org.inspien.client;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestApiClient {

    public String request() {
        URL url = null;
        String readLine = null;
        StringBuilder buffer = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        HttpURLConnection urlConnection = null;

        int connTimeout = 5000;
        int readTimeout = 3000;

        String sendData =
                "{" +
                        "\"NAME\" : \"조영현테스트\"," +
                        "\"PHONE_NUMBER\" : \"010-9512-8646\"," +
                        "\"E_MAIL\" : \"psyhyun1030@gmail.com\"" +
                        "}";
        String apiUrl = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";

        try {
            url = new URL(apiUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(connTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setDoOutput(true);

            outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(sendData);
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (bufferedWriter != null) bufferedWriter.close();
                if (bufferedReader != null) bufferedReader.close();
                if (outputStream != null) outputStream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return buffer.toString();
    }
}

