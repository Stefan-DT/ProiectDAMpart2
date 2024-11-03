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

public class HomeActivity extends AppCompatActivity {

    private ArrayList<String> profilData;
    private ArrayList<Somn> somnDataList;
    private SomnAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        profilData = new ArrayList<>();
        profilData.add("Nume: Ion Popescu");
        profilData.add("Email: ion.popescu@example.com");

        somnDataList = new ArrayList<>();
        ListView listView = findViewById(R.id.listViewSomnData);
        adapter = new SomnAdapter(this, somnDataList);
        listView.setAdapter(adapter);

        Button goToDateSomnButton = findViewById(R.id.bGoToDateSomn);
        goToDateSomnButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DateSomn.class);
            startActivityForResult(intent, 1);
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
                intentProfil.putStringArrayListExtra("profilData", profilData);
                startActivity(intentProfil);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Somn somnData = (Somn) data.getSerializableExtra("somnData");
            somnDataList.add(somnData);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meniu, menu);
        return true;
    }
}
