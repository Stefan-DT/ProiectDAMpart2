package csie.ase.ro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsProfil {
    private HttpsURLConnection httpsURLConnection;
    private String URLProfil;

    public HttpsProfil(String URLProfil) {
        this.URLProfil = URLProfil;
    }


    public String procesareProfil() {
        try {
            return obtinereProfil();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }
        }
    }

    private String obtinereProfil() throws IOException {
        httpsURLConnection = (HttpsURLConnection) new URL(URLProfil).openConnection();
        httpsURLConnection.setRequestMethod("GET");
        int responseCode = httpsURLConnection.getResponseCode();

        if (responseCode != 200) {
            throw new IOException("Eroare la conectare: " + responseCode);
        }

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(httpsURLConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }


}
