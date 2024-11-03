package csie.ase.ro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Spinner spinnerSex = findViewById(R.id.spinnerSex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapter);

        Button bRegister = findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etEmail = findViewById(R.id.etNewEmail);
                EditText etPassword = findViewById(R.id.etNewPassword);
                EditText etName = findViewById(R.id.etName);
                EditText etSurname = findViewById(R.id.etSurname);
                EditText etAge = findViewById(R.id.etAge);
                EditText etHeight = findViewById(R.id.etHeight);
                Spinner spinnerSex = findViewById(R.id.spinnerSex);

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String name = etName.getText().toString();
                String surname = etSurname.getText().toString();
                String age = etAge.getText().toString();
                String height = etHeight.getText().toString();
                String sex = spinnerSex.getSelectedItem().toString();

                if (email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() ||
                        age.isEmpty() || height.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "Te rog completează toate câmpurile!", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences sharedPreferences = getSharedPreferences("userProfile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("name", name);
                    editor.putString("surname", surname);
                    editor.putString("age", age);
                    editor.putString("height", height);
                    editor.putString("sex", sex);
                    editor.apply();

                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
