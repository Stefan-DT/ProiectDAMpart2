package csie.ase.ro;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

        loadAjutoare();

        buttonTrimite.setOnClickListener(v -> {
            String nume = editTextInfo1.getText().toString();
            String problema = editTextInfo2.getText().toString();

            if (!nume.isEmpty() && !problema.isEmpty()) {
                Ajutor ticket = new Ajutor(nume, problema);
                listaMare.add(ticket);

                ajutorDAO.insertAjutor(ticket);

                adapter.notifyDataSetChanged();
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



    private void loadAjutoare() {
        listaMare.clear();
        listaMare.addAll(ajutorDAO.getAjutor());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            Ajutor updatedAjutor = (Ajutor) data.getSerializableExtra("updatedAjutor");
            int position = data.getIntExtra("position", -1);

            if (position != -1) {
                ajutorDAO.updateAjutor(updatedAjutor);

                listaMare.set(position, updatedAjutor);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
