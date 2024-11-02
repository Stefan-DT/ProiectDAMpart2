package csie.ase.ro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSomn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_somn);

        EditText editTextDataSomn = findViewById(R.id.editTextDataSomn);
        EditText editTextOraTrezirii = findViewById(R.id.editTextOraTrezirii);
        EditText editTextDurataSomnului = findViewById(R.id.editTextDurataSomnului);
        EditText editTextCalitateSomnului = findViewById(R.id.editTextCalitateSomnului);
        EditText editTextNote = findViewById(R.id.editTextNote);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

                    Date dataSomnului = sdf.parse(editTextDataSomn.getText().toString());
                    Date oraTrezirii = timeFormat.parse(editTextOraTrezirii.getText().toString());
                    int durataSomnului = Integer.parseInt(editTextDurataSomnului.getText().toString());
                    int calitateSomnului = Integer.parseInt(editTextCalitateSomnului.getText().toString());
                    String note = editTextNote.getText().toString();


                    Somn somn = new Somn(dataSomnului, oraTrezirii, durataSomnului, calitateSomnului, note);

                    Intent intent = new Intent();
                    intent.putExtra("somnData", somn);
                    setResult(RESULT_OK, intent);


                    Toast.makeText(DateSomn.this, "Somn înregistrat", Toast.LENGTH_LONG).show();

                    finish();

                } catch (ParseException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
