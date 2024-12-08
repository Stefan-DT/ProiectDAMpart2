package csie.ase.ro;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpsSaveReview {
    private String url;
    private String review;
    private float rating;

    public HttpsSaveReview(String url, String review, float rating) {
        this.url = url;
        this.review = review;
        this.rating = rating;
    }

    public String procesareSaveReview() {
        try {
            return saveReview();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String saveReview() throws Exception {
        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        String jsonInputString = String.format("{\"review\": \"%s\", \"rating\": %.1f}", review, rating);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            return "Success";
        }
        return null;
    }
}
