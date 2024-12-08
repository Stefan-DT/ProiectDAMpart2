package csie.ase.ro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String URLSomn = "https://www.jsonkeeper.com/b/I6CF";

    private SomnDAO somnDAO;
    private ArrayList<Somn> somnDataList = new ArrayList<>();
    private SomnAdapter adapter;
    private ListView listViewSomnData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        somnDAO = SomnDB.getInstance(this).getSomnDAO();

        ListView listView = findViewById(R.id.listViewSomnData);
        adapter = new SomnAdapter(this, somnDataList);
        listView.setAdapter(adapter);

        loadSomnData();
        initComponente();
        incarcaSomnRetea();

        Button btnAddReview = findViewById(R.id.btnAddReview);

        btnAddReview.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ReviewActivity.class);
            startActivity(intent);
        });

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


    private void incarcaSomnRetea() {
        Thread thread = new Thread(() -> {
            HttpsSomn httpsSomn = new HttpsSomn(URLSomn);
            String rezultat = httpsSomn.procesareSomn();

            runOnUiThread(() -> {
                preluareSomnDinJson(rezultat);
            });
        });
        thread.start();
    }


    public void initComponente(){
        listViewSomnData = findViewById(R.id.listViewSomnData);
        //ArrayAdapter<Somn> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, somnDataList);
        listViewSomnData.setAdapter(adapter);

    }
    private void preluareSomnDinJson(String json) {
        somnDataList.clear();
        somnDataList.addAll(SomnParser.parsareJSON(json));
        runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
        });
    }



}
