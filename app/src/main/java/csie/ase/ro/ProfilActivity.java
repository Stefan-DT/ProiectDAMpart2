package csie.ase.ro;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfilActivity extends AppCompatActivity {

    private ListView listViewProfil;
    private ProfilAdapter profilAdapter;
    private List<String> profilData = new ArrayList<>();
    private UserDAO userDao;

    private static final String URLProfil = "https://www.jsonkeeper.com/b/WH12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        listViewProfil = findViewById(R.id.listViewProfil);

        loadUserDataFromDB();

        loadUserDataFromNetwork();
    }

    private void loadUserDataFromDB() {
        UserDB db = UserDB.getInstance(this);
        userDao = db.userDao();

        new Thread(() -> {
            List<User> userList = userDao.getAllUsers();
            if (userList != null && !userList.isEmpty()) {
                for (User user : userList) {
                    profilData.add("Email: " + user.getEmail());
                    profilData.add("Name: " + user.getName());
                    profilData.add("Surname: " + user.getSurname());
                    profilData.add("Age: " + user.getAge());
                    profilData.add("Height: " + user.getHeight());
                    profilData.add("Sex: " + user.getSex());
                    profilData.add("----------------------------------------------------------------------------");
                }

                runOnUiThread(() -> {
                    profilAdapter = new ProfilAdapter(this, R.layout.item_profil, profilData);
                    listViewProfil.setAdapter(profilAdapter);
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(ProfilActivity.this, "Nu există date în baza de date", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void loadUserDataFromNetwork() {
        Thread thread = new Thread(() -> {
            HttpsProfil httpsProfil = new HttpsProfil(URLProfil);
            String result = httpsProfil.procesareProfil();

            runOnUiThread(() -> {
                if (result != null) {
                    parseJsonData(result);
                } else {
                    Toast.makeText(ProfilActivity.this, "Nu am reușit să încărcăm datele din rețea", Toast.LENGTH_SHORT).show();
                }
            });
        });
        thread.start();
    }

    private void parseJsonData(String json) {
        Log.d("ProfilActivity", "Răspuns JSON: " + json);
        List<User> userList = UserParser.parseJSON(json);

        if (userList.isEmpty()) {
            Log.d("ProfilActivity", "Nicio înregistrare găsită!");
        }

        for (User user : userList) {
            profilData.add("Email: " + user.getEmail());
            profilData.add("Name: " + user.getName());
            profilData.add("Surname: " + user.getSurname());
            profilData.add("Age: " + user.getAge());
            profilData.add("Height: " + user.getHeight());
            profilData.add("Sex: " + user.getSex());
            profilData.add("----------------------------------------------------------------------------");
        }

        runOnUiThread(() -> profilAdapter.notifyDataSetChanged());
    }
}
