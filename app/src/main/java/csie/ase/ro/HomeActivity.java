package csie.ase.ro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private SomnDAO somnDAO; // DAO pentru Room
    private ArrayList<Somn> somnDataList = new ArrayList<>();  // Modifică aici
    private SomnAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        // Inițializare DAO
        somnDAO = SomnDB.getInstance(this).getSomnDAO();

        // Inițializare ListView și Adapter
        ListView listView = findViewById(R.id.listViewSomnData);
        adapter = new SomnAdapter(this, somnDataList); // Se folosește ArrayList<Somn>
        listView.setAdapter(adapter);

        // Încărcarea datelor din baza de date
        loadSomnData();

        Button goToDateSomnButton = findViewById(R.id.bGoToDateSomn);
        goToDateSomnButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DateSomn.class);
            startActivityForResult(intent, 1);
        });

        Button buttonDeleteAll = findViewById(R.id.buttonDeleteAll);
        buttonDeleteAll.setOnClickListener(v -> {
            somnDAO.deleteAllSomn();
            loadSomnData();
            Toast.makeText(this, "Toate înregistrările au fost șterse!", Toast.LENGTH_SHORT).show();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Somn selectedSomn = somnDataList.get(position);
            Intent intent = new Intent(HomeActivity.this, DateSomn.class);
            intent.putExtra("somnData", selectedSomn);
            intent.putExtra("position", position);
            startActivityForResult(intent, 2);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Somn somnToDelete = somnDataList.get(position);

            somnDAO.deleteSomnById(somnToDelete.getId());

            somnDataList.remove(position);
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Înregistrare ștearsă!", Toast.LENGTH_SHORT).show();
            return true;
        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                Intent intentLogout = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intentLogout);
                finish();
                return true;
            } else if (item.getItemId() == R.id.action_help) {
                Intent intentHelp = new Intent(HomeActivity.this, AjutorActivity.class);
                startActivity(intentHelp);
                return true;
            } else if (item.getItemId() == R.id.action_about) {
                Toast.makeText(this, "Despre noi selectat", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.action_profil) {
                Intent intentProfil = new Intent(this, ProfilActivity.class);
                startActivity(intentProfil);
                return true;
            }
            return false;
        });
    }

    private void loadSomnData() {
        somnDataList.clear();
        somnDataList.addAll(somnDAO.getAllSomn());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meniu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Somn somnData = (Somn) data.getSerializableExtra("somnData");

                if (somnData != null) {
                    somnDataList.add(somnData);
                    somnDAO.insertSomn(somnData);

                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

}
