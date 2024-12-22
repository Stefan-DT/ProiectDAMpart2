package csie.ase.ro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProfilAdapter extends ArrayAdapter<String> {

    private int layoutId;
    private List<String> profilData;
    private LayoutInflater inflater;

    public ProfilAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.layoutId = resource;
        this.profilData = objects;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(layoutId, parent, false);
        }

        String data = profilData.get(position);

        TextView textView = view.findViewById(R.id.textViewProfilData);
        if (textView != null) {
            textView.setText(data);
        } else {
            android.util.Log.e("ProfilAdapter", "TextView not found in layout at position: " + position);
        }

        return view;
    }
}
