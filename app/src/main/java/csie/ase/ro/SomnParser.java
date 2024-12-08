package csie.ase.ro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SomnParser {
    private static final String ID = "id";
    private static final String DATA_SOMN = "dataSomnului";
    private static final String ORA_TREZIRII = "oraTrezirii";
    private static final String DURATA_SOMN = "durataSomnului";
    private static final String CALITATE_SOMN = "calitateSomnului";
    private static final String NOTE = "note";

    public static List<Somn> parsareJSON(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareSomnuri(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Somn> parsareSomnuri(JSONArray jsonArray) throws JSONException {
        List<Somn> somnuri = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            somnuri.add(parsareSomn(jsonArray.getJSONObject(i)));
        }
        return somnuri;
    }

    private static Somn parsareSomn(JSONObject jsonObject) throws JSONException {
        Long id = jsonObject.optLong(ID, 0);
        String dataSomn = jsonObject.getString(DATA_SOMN);
        String oraTrezirii = jsonObject.getString(ORA_TREZIRII);
        int durataSomnului = jsonObject.getInt(DURATA_SOMN);
        int calitateSomnului = jsonObject.getInt(CALITATE_SOMN);
        String note = jsonObject.optString(NOTE, "");

        return new Somn(
                Somn.parseDate(dataSomn),
                Somn.parseTime(oraTrezirii),
                durataSomnului,
                calitateSomnului,
                note
        );
    }
}
