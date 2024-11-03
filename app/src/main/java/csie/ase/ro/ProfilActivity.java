package csie.ase.ro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfilActivity extends AppCompatActivity {

    private ListView listViewProfil;
    private ProfilAdapter profilAdapter;
    private List<String> profilData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Asigură-te că ai un layout corespunzător

        // Obține ListView-ul din layout
        listViewProfil = findViewById(R.id.listViewProfil);

        // Obține datele din SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE);

        // Adaugă datele în profilData, doar dacă nu sunt goale
        String email = sharedPreferences.getString("email", "");
        if (!email.isEmpty()) {
            profilData.add("Email: " + email);
        }

        String name = sharedPreferences.getString("name", "");
        if (!name.isEmpty()) {
            profilData.add("Name: " + name);
        }

        String surname = sharedPreferences.getString("surname", "");
        if (!surname.isEmpty()) {
            profilData.add("Surname: " + surname);
        }

        String age = sharedPreferences.getString("age", "");
        if (!age.isEmpty()) {
            profilData.add("Age: " + age);
        }

        String height = sharedPreferences.getString("height", "");
        if (!height.isEmpty()) {
            profilData.add("Height: " + height);
        }

        String sex = sharedPreferences.getString("sex", "");
        if (!sex.isEmpty()) {
            profilData.add("Sex: " + sex);
        }

        // Configurarea adapter-ului pentru ListView
        profilAdapter = new ProfilAdapter(this, R.layout.profil_list_item, profilData, getLayoutInflater());
        listViewProfil.setAdapter(profilAdapter);
    }
}
