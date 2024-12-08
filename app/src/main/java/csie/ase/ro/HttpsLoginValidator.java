package csie.ase.ro;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpsLoginValidator {
    private String email;
    private String password;

    public HttpsLoginValidator(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean validateLogin() {
        try {
            String urlString = "https://www.jsonkeeper.com/b/WH12";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userJson = jsonArray.getJSONObject(i);
                String serverEmail = userJson.getString("email");
                String serverPassword = userJson.getString("password");

                if (serverEmail.equals(email) && serverPassword.equals(password)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
