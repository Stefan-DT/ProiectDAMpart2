package csie.ase.ro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserParser {
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String AGE = "age";
    private static final String HEIGHT = "height";
    private static final String SEX = "sex";

    public static List<User> parseJSON(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parseUsers(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<User> parseUsers(JSONArray jsonArray) throws JSONException {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            users.add(parseUser(jsonArray.getJSONObject(i)));
        }
        return users;
    }

    private static User parseUser(JSONObject jsonObject) throws JSONException {
        int id = (int) jsonObject.optLong(ID, 0);
        String email = jsonObject.getString(EMAIL);
        String password = jsonObject.getString(PASSWORD);
        String name = jsonObject.getString(NAME);
        String surname = jsonObject.getString(SURNAME);
        int age = jsonObject.getInt(AGE);
        float height = (float) jsonObject.getDouble(HEIGHT);
        String sex = jsonObject.getString(SEX);

        return new User(id, email, password, name, surname, age, height, sex);
    }
}
