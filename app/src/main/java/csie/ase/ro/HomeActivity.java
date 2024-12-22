package csie.ase.ro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private static final String URLSomn = "https://www.jsonkeeper.com/b/I6CF";

    private SomnDAO somnDAO;
    private ArrayList<Somn> somnDataList = new ArrayList<>();
    private SomnAdapter adapter;
    private ListView listViewSomnData;
    private ActivityResultLauncher<Intent> launcherDateSomn;

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

        launcherDateSomn = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Somn somnData = (Somn) data.getSerializableExtra("somnData");
                            if (somnData != null) {
                                somnDAO.insertSomn(somnData);

                                loadSomnData();
                            }
                        }
                    }
                }
        );

        Button btnAddReview = findViewById(R.id.btnAddReview);
        btnAddReview.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ReviewActivity.class);
            launcherDateSomn.launch(intent);
        });

        Button goToDateSomnButton = findViewById(R.id.bGoToDateSomn);
        goToDateSomnButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DateSomn.class);
            launcherDateSomn.launch(intent);
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
            launcherDateSomn.launch(intent);
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
                launcherDateSomn.launch(intentLogout);
                finish();
                return true;
            } else if (item.getItemId() == R.id.action_help) {
                Intent intentHelp = new Intent(HomeActivity.this, AjutorActivity.class);
                launcherDateSomn.launch(intentHelp);
                return true;
            } else if (item.getItemId() == R.id.action_about) {
                Intent intentHelp = new Intent(HomeActivity.this, SubscriptionActivity.class);
                launcherDateSomn.launch(intentHelp);
                return true;

            } else if (item.getItemId() == R.id.action_profil) {
                Intent intentProfil = new Intent(this, ProfilActivity.class);
                launcherDateSomn.launch(intentProfil);
                return true;
            } else if (item.getItemId() == R.id.action_medication) {
                Intent intentMedicatie = new Intent(this, MedicationsActivity.class);
                launcherDateSomn.launch(intentMedicatie);
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

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(item.getTitle());
            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0);
            item.setTitle(spanString);
        }

        return true;
    }

    private void incarcaSomnRetea() {
        Thread thread = new Thread(() -> {
            HttpsSomn httpsSomn = new HttpsSomn(URLSomn);
            String rezultat = httpsSomn.procesareSomn();

            runOnUiThread(() -> preluareSomnDinJson(rezultat));
        });
        thread.start();
    }

    private void preluareSomnDinJson(String json) {
        ArrayList<Somn> somnList = (ArrayList<Somn>) SomnParser.parsareJSON(json);

        for (Somn somn : somnList) {
            somnDAO.insertSomn(somn);
        }

        somnDataList.addAll(somnList);
        adapter.notifyDataSetChanged();
    }

    public void initComponente() {
        listViewSomnData = findViewById(R.id.listViewSomnData);
        listViewSomnData.setAdapter(adapter);
    }
}
