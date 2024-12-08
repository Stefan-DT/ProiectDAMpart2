package csie.ase.ro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AjutorParser {
    private static final String ID = "id";
    private static final String NUME_AJUTOR = "numeAjutor";
    private static final String DESCRIERE_AJUTOR = "descriereAjutor";

    public static List<Ajutor> parsareJSON(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareAjutoare(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Ajutor> parsareAjutoare(JSONArray jsonArray) throws JSONException {
        List<Ajutor> ajutoare = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ajutoare.add(parsareAjutor(jsonArray.getJSONObject(i)));
        }
        return ajutoare;
    }

    private static Ajutor parsareAjutor(JSONObject jsonObject) throws JSONException {
        Long id = jsonObject.optLong(ID, 0);
        String numeAjutor = jsonObject.getString(NUME_AJUTOR);
        String descriereAjutor = jsonObject.getString(DESCRIERE_AJUTOR);

        return new Ajutor(id, numeAjutor, descriereAjutor);
    }
}
