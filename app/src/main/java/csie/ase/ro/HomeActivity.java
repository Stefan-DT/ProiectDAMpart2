package csie.ase.ro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> somnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = findViewById(R.id.listView);
        somnList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, somnList);
        listView.setAdapter(adapter);

        Intent intent1 = getIntent();
        String toastMessage = intent1.getStringExtra("toastMessage");
        if (toastMessage != null) {

            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
        }

        Button buttonDateSomn = findViewById(R.id.buttonDateSomn);
        buttonDateSomn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DateSomn.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Somn somn = (Somn) data.getSerializableExtra("somnData");
            if (somn != null) {
                somnList.add(somn.toString()); // Adaugă datele în listă
                adapter.notifyDataSetChanged(); // Actualizează ListView-ul
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meniu, menu);
        return true;
    }
}
