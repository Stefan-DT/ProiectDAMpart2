package csie.ase.ro;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AjutorActivity extends AppCompatActivity {

    private AjutorDAO ajutorDAO;
    private EditText editTextInfo1;
    private EditText editTextInfo2;
    private Button buttonTrimite;
    private ListView listViewInfo;
    private List<Ajutor> listaMare = new ArrayList<>();
    private AjutorAdapter adapter;

    private SharedPreferences sharedPreferences;

    private static final String URLAjutor = "https://www.jsonkeeper.com/b/34WX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajutor);

        editTextInfo1 = findViewById(R.id.editTextInfo1);
        editTextInfo2 = findViewById(R.id.editTextInfo2);
        buttonTrimite = findViewById(R.id.buttonTrimite);
        listViewInfo = findViewById(R.id.listViewInfo);

        LayoutInflater inflater = getLayoutInflater();
        adapter = new AjutorAdapter(this, R.layout.list_item, listaMare, inflater);
        listViewInfo.setAdapter(adapter);

        ajutorDAO = AjutorDB.getInstance(this).getAjutorDAO();
        listaMare.clear();
        adapter.notifyDataSetChanged();

        loadAjutoareFromDB();
        loadAjutoareFromNetwork();

        buttonTrimite.setOnClickListener(v -> {
            String nume = editTextInfo1.getText().toString();
            String problema = editTextInfo2.getText().toString();

            if (!nume.isEmpty() && !problema.isEmpty()) {
                Ajutor ticket = new Ajutor(null, nume, problema);

                ajutorDAO.insertAjutor(ticket);

                listaMare.add(ticket);
                adapter.notifyDataSetChanged();

                editTextInfo1.setText("");
                editTextInfo2.setText("");
            }
        });

        listViewInfo.setOnItemClickListener((parent, view, position, id) -> {
            Ajutor selectedAjutor = listaMare.get(position);

            Intent intent = new Intent(AjutorActivity.this, EditAjutorActivity.class);
            intent.putExtra("ajutorData", selectedAjutor);
            intent.putExtra("position", position);
            startActivityForResult(intent, 1);
        });

        listViewInfo.setOnItemLongClickListener((parent, view, position, id) -> {
            Ajutor ajutorToDelete = listaMare.get(position);

            ajutorDAO.deleteAjutorById(ajutorToDelete.getId());
            listaMare.remove(position);
            adapter.notifyDataSetChanged();

            return true;
        });
    }

    private void loadAjutoareFromDB() {
        listaMare.clear();
        listaMare.addAll(ajutorDAO.getAjutor());
        adapter.notifyDataSetChanged();
    }

    private void loadAjutoareFromNetwork() {
        Thread thread = new Thread(() -> {
            HttpsAjutor httpsAjutor = new HttpsAjutor(URLAjutor);
            String result = httpsAjutor.procesareAjutor();

            runOnUiThread(() -> {
                if (result != null) {
                    preluareAjutorDinJson(result);
                } else {
                    Toast.makeText(AjutorActivity.this, "Nu am reușit să încărcăm datele din rețea", Toast.LENGTH_SHORT).show();
                }
            });
        });
        thread.start();
    }

    private void preluareAjutorDinJson(String json) {
        Log.d("AjutorActivity", "Răspuns JSON: " + json);
        List<Ajutor> ajutoareDinJson = AjutorParser.parsareJSON(json);

        listaMare.addAll(ajutoareDinJson);
        adapter.notifyDataSetChanged();

        Toast.makeText(this, "Datele Ajutor au fost încărcate din rețea!", Toast.LENGTH_SHORT).show();
    }






}