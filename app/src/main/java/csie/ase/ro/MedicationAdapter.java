package csie.ase.ro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MedicationAdapter extends ArrayAdapter<Medication> {
    private Context context;
    private List<Medication> medications;

    public MedicationAdapter(Context context, List<Medication> medications) {
        super(context, R.layout.item_medication, medications);
        this.context = context;
        this.medications = medications;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_medication, parent, false);
        }

        Medication medication = medications.get(position);

        TextView nameTextView = convertView.findViewById(R.id.textViewMedicationName);
        TextView dosageTextView = convertView.findViewById(R.id.textViewDosage);
        TextView instructionsTextView = convertView.findViewById(R.id.textViewFrequency);

        nameTextView.setText(medication.getName());
        dosageTextView.setText(medication.getDosage());
        instructionsTextView.setText(medication.getInstructions());

        return convertView;
    }
}
