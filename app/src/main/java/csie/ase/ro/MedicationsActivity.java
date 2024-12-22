package csie.ase.ro;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MedicationsActivity extends AppCompatActivity {
    private EditText editTextName, editTextDosage, editTextInstructions;
    private DatabaseReference database;
    private ListView listView;
    private MedicationAdapter adapter;
    private List<Medication> medicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        editTextName = findViewById(R.id.editTextName);
        editTextDosage = findViewById(R.id.editTextDosage);
        editTextInstructions = findViewById(R.id.editTextInstructions);
        listView = findViewById(R.id.medicationsListView);

        database = FirebaseDatabase.getInstance().getReference("medications");

        medicationList = new ArrayList<>();
        adapter = new MedicationAdapter(this, medicationList);
        listView.setAdapter(adapter);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);

        btnSave.setOnClickListener(view -> {
            String name = editTextName.getText().toString().trim();
            String dosage = editTextDosage.getText().toString().trim();
            String instructions = editTextInstructions.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(dosage) || TextUtils.isEmpty(instructions)) {
                Toast.makeText(this, "Te rugăm să completezi toate câmpurile", Toast.LENGTH_SHORT).show();
            } else {
                String id = database.push().getKey();
                if (id != null) {
                    Medication medication = new Medication(name, dosage, instructions);
                    database.child(id).setValue(medication).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MedicationsActivity.this, "Medicamentul a fost salvat cu succes", Toast.LENGTH_SHORT).show();
                            loadMedications();

                            editTextName.setText("");
                            editTextDosage.setText("");
                            editTextInstructions.setText("");
                        } else {
                            Toast.makeText(MedicationsActivity.this, "Salvarea medicamentului a eșuat", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MedicationsActivity.this, "Eroare la generarea ID-ului", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDeleteAll.setOnClickListener(view -> {
            database.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(MedicationsActivity.this, "Toate datele au fost șterse cu succes", Toast.LENGTH_SHORT).show();
                    medicationList.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MedicationsActivity.this, "Ștergerea datelor a eșuat", Toast.LENGTH_SHORT).show();
                }
            });
        });

        loadMedications();
    }

    private void loadMedications() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medicationList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Medication medication = snapshot.getValue(Medication.class);
                    if (medication != null) {
                        medicationList.add(medication);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MedicationsActivity.this, "Eroare la preluarea datelor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
