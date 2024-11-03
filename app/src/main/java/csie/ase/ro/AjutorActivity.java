package csie.ase.ro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AjutorActivity extends AppCompatActivity {

    private EditText editTextInfo1;
    private EditText editTextInfo2;
    private Button buttonTrimite;
    private ListView listViewInfo;
    private List<Ajutor> listaMare = new ArrayList<>();
    private AjutorAdapter adapter;

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

        buttonTrimite.setOnClickListener(v -> {
            String nume = editTextInfo1.getText().toString();
            String problema = editTextInfo2.getText().toString();
            Ajutor ticket = new Ajutor(nume, problema);
            listaMare.add(ticket);
            adapter.notifyDataSetChanged();
        });
    }
}
