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

public class SubscriptionActivity extends AppCompatActivity {
    private EditText editTextName, editTextType, editTextDuration;
    private DatabaseReference database;
    private ListView listView;
    private List<Subscription> subscriptionList;
    private SubscriptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        editTextName = findViewById(R.id.editTextName);
        editTextType = findViewById(R.id.editTextType);
        editTextDuration = findViewById(R.id.editTextDuration);
        listView = findViewById(R.id.subscriptionsListView);

        database = FirebaseDatabase.getInstance().getReference("subscriptions");

        subscriptionList = new ArrayList<>();
        adapter = new SubscriptionAdapter(this, subscriptionList);
        listView.setAdapter(adapter);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);

        btnSave.setOnClickListener(view -> {
            String name = editTextName.getText().toString().trim();
            String type = editTextType.getText().toString().trim();
            String duration = editTextDuration.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(type) || TextUtils.isEmpty(duration)) {
                Toast.makeText(this, "Te rugăm să completezi toate câmpurile", Toast.LENGTH_SHORT).show();
            } else {
                String id = database.push().getKey();
                if (id != null) {
                    Subscription subscription = new Subscription(name, type, duration);
                    database.child(id).setValue(subscription).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(SubscriptionActivity.this, "Abonamentul a fost salvat cu succes", Toast.LENGTH_SHORT).show();
                            loadSubscriptions();

                            editTextName.setText("");
                            editTextType.setText("");
                            editTextDuration.setText("");
                        } else {
                            Toast.makeText(SubscriptionActivity.this, "Salvarea abonamentului a eșuat", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(SubscriptionActivity.this, "Eroare la generarea ID-ului", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDeleteAll.setOnClickListener(view -> {
            database.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(SubscriptionActivity.this, "Toate datele au fost șterse cu succes", Toast.LENGTH_SHORT).show();
                    subscriptionList.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SubscriptionActivity.this, "Ștergerea datelor a eșuat", Toast.LENGTH_SHORT).show();
                }
            });
        });

        loadSubscriptions();
    }

    private void loadSubscriptions() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subscriptionList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Subscription subscription = snapshot.getValue(Subscription.class);
                    if (subscription != null) {
                        subscriptionList.add(subscription);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SubscriptionActivity.this, "Eroare la preluarea datelor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
