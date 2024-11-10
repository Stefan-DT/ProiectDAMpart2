package csie.ase.ro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;

public class DateSomn extends Activity {

    private EditText editTextDataSomn, editTextOraTrezirii, editTextDurataSomnului, editTextCalitateSomnului, editTextNote;
    private Button buttonSave;
    private Somn somn;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_somn);

        editTextDataSomn = findViewById(R.id.editTextDataSomn);
        editTextOraTrezirii = findViewById(R.id.editTextOraTrezirii);
        editTextDurataSomnului = findViewById(R.id.editTextDurataSomnului);
        editTextCalitateSomnului = findViewById(R.id.editTextCalitateSomnului);
        editTextNote = findViewById(R.id.editTextNote);
        buttonSave = findViewById(R.id.buttonSave);

        editTextDataSomn.setText("2024-11-01");
        editTextOraTrezirii.setText("07:00");
        editTextDurataSomnului.setText("7");
        editTextCalitateSomnului.setText("8");
        editTextNote.setText("Somn odihnitor");

        Intent intent = getIntent();
        somn = (Somn) intent.getSerializableExtra("somnData");
        position = intent.getIntExtra("position", -1);

        if (somn != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            editTextDataSomn.setText(dateFormat.format(somn.getDataSomnului()));
            editTextOraTrezirii.setText(timeFormat.format(somn.getOraTrezirii()));
            editTextDurataSomnului.setText(String.valueOf(somn.getDurataSomnului()));
            editTextCalitateSomnului.setText(String.valueOf(somn.getCalitateSomnului()));
            editTextNote.setText(somn.getNote());
        }

        buttonSave.setOnClickListener(v -> {
            String dataSomnului = editTextDataSomn.getText().toString();
            String oraTrezirii = editTextOraTrezirii.getText().toString();
            int durataSomnului = Integer.parseInt(editTextDurataSomnului.getText().toString());
            int calitateSomnului = Integer.parseInt(editTextCalitateSomnului.getText().toString());
            String note = editTextNote.getText().toString();

            Somn updatedSomn = new Somn(Somn.parseDate(dataSomnului), Somn.parseTime(oraTrezirii), durataSomnului, calitateSomnului, note);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("somnData", updatedSomn);
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
