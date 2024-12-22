package csie.ase.ro;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FirebaseService {
    private final DatabaseReference reference;
    private static FirebaseService firebaseService;

    private FirebaseService() {
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseService getInstance() {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }

    public void insert(Medication medication) {
        if (medication == null || medication.getId() != null) {
            return;
        }
        String id = reference.push().getKey();
        medication.setId(id);
        reference.child(medication.getId()).setValue(medication);
    }

    public void update(Medication medication) {
        if (medication == null || medication.getId() == null) {
            return;
        }
        reference.child(medication.getId()).setValue(medication);
    }

    public void delete(Medication medication) {
        if (medication == null || medication.getId() == null) {
            return;
        }
        reference.child(medication.getId()).removeValue();
    }

    public void addMedicationsListener(Callback<List<Medication>> callback) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Medication> medications = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Medication medication = data.getValue(Medication.class);
                    if (medication != null) {
                        medications.add(medication);
                    }
                }
                callback.runOnUI(medications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Medications not available!");
            }
        });
    }
}
