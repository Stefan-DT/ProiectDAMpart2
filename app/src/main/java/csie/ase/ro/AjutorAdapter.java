package csie.ase.ro;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.zip.Inflater;

public class AjutorAdapter extends ArrayAdapter<Ajutor> {
    private Context context;

    private List<Ajutor> listaAjutoare;
    private int layoutId;
    private LayoutInflater inflater;


    public AjutorAdapter(@NonNull Context context, int resource, @NonNull List<Ajutor> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.listaAjutoare = objects;
        this.layoutId = resource;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(layoutId,parent,false);
        Ajutor listaElement = listaAjutoare.get(position);

        TextView textViewNume = view.findViewById(R.id.textViewNume);
        TextView textViewProblema = view.findViewById(R.id.textViewProblema);

        textViewNume.setText(listaElement.getNumeAjutor());
        textViewProblema.setText(listaElement.getDescriereAjutor());

        textViewNume.setTextSize(20);
        textViewNume.setTextColor(Color.GREEN);
        textViewNume.setTypeface(textViewNume.getTypeface(), Typeface.BOLD);
        textViewProblema.setTypeface(textViewNume.getTypeface(), Typeface.BOLD);


        return view;
    }


}