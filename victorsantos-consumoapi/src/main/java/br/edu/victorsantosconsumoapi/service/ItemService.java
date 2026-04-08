package br.edu.victorsantosconsumoapi.service;

import br.edu.victorsantosconsumoapi.ItemResponse;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;

public class ItemService {

    private final String URL = "http://localhost:7000";

    public ItemResponse httpRequest(String urlString, String method, String payload, String contentType) throws IOException {
        disableSSLverification();
        String entitiesURL = URL+urlString;

        URL url = new URL(entitiesURL);

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod(method);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if(payload != null && ("POST".equals(method)) || ("PUT".equals(method))) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", contentType != null ? contentType : "application/json;charset=utf-8");

            try(DataOutputStream dos = new DataOutputStream(connection.getOutputStream())){
                byte[] sendData = payload.getBytes(StandardCharsets.UTF_8);

                dos.write(sendData);
                dos.flush();
            }
        }

        StringBuilder response = new StringBuilder();

        String allowedMethods = connection.getHeaderField("Allow");

        if("OPTIONS".equals(method)) {
            response.append("Métodos Permitidos: " + allowedMethods);
        }

        int responseCode = connection.getResponseCode();

        String responseMessage = connection.getResponseMessage();

        BufferedReader reader = null;

        if(responseCode >= 200 && responseCode < 300) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
        }

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        connection.disconnect();

        return new ItemResponse(responseCode, responseMessage, response.toString());
    }

    private static void disableSSLverification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
