package csie.ase.ro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditAjutorActivity extends AppCompatActivity {
    private EditText editTextNume;
    private EditText editTextProblema;
    private Button buttonSave;
    private int pozitie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ajutor);

        editTextNume = findViewById(R.id.editTextNume);
        editTextProblema = findViewById(R.id.editTextProblema);
        buttonSave = findViewById(R.id.buttonSave);

        Ajutor ajutor = (Ajutor) getIntent().getSerializableExtra("ajutorData");
        pozitie = getIntent().getIntExtra("position", -1);

        editTextNume.setText(ajutor.getNumeAjutor());
        editTextProblema.setText(ajutor.getDescriereAjutor());

        buttonSave.setOnClickListener(v -> {
            String numeNou = editTextNume.getText().toString();
            String problemaNoua = editTextProblema.getText().toString();

            Ajutor ajutorModificat = new Ajutor(numeNou, problemaNoua);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedAjutor", ajutorModificat);
            resultIntent.putExtra("position", pozitie);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
