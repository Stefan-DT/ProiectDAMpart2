package csie.ase.ro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpsReviews {
    private String url;

    public HttpsReviews(String url) {
        this.url = url;
    }

    public String procesareReviews() {
        try {
            return obtinereReviews();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String obtinereReviews() throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(url).openConnection();
        httpsURLConnection.setRequestMethod("GET");
        int responseCode = httpsURLConnection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Eroare la conectare: " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}
