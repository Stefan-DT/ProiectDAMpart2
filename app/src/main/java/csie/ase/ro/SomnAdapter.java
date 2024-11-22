package csie.ase.ro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SomnAdapter extends ArrayAdapter<Somn> {
    public SomnAdapter(Context context, ArrayList<Somn> somnList) {
        super(context, 0, somnList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Somn somn = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.somn_list_item, parent, false);
        }


        TextView textViewData = convertView.findViewById(R.id.textViewData);
        TextView textViewOra = convertView.findViewById(R.id.textViewOra);
        TextView textViewDurata = convertView.findViewById(R.id.textViewDurata);
        TextView textViewCalitateSomnului = convertView.findViewById(R.id.textViewCalitateSomnului);
        TextView textViewNota = convertView.findViewById(R.id.textViewNota);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        String dataString = dateFormat.format(somn.getDataSomnului());
        String oraString = timeFormat.format(somn.getOraTrezirii());

        textViewData.setText(dataString);
        textViewOra.setText(oraString);
        textViewDurata.setText(String.valueOf(somn.getDurataSomnului()));
        textViewCalitateSomnului.setText(String.valueOf(somn.getCalitateSomnului()));
        textViewNota.setText(somn.getNote());

        if (somn.getDurataSomnului() < 8) {
            textViewDurata.setTextColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
        } else {
            textViewDurata.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
        }

        return convertView;
    }
}