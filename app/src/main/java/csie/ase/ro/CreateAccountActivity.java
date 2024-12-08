package csie.ase.ro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateAccountActivity extends Activity {

    private EditText etEmail, etPassword, etName, etSurname, etAge, etHeight;
    private Spinner spinnerSex;
    private UserDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etEmail = findViewById(R.id.etNewEmail);
        etPassword = findViewById(R.id.etNewPassword);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        spinnerSex = findViewById(R.id.spinnerSex);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapter);

        UserDB db = UserDB.getInstance(this);
        userDao = db.userDao();

        Button bRegister = findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String heightStr = etHeight.getText().toString().trim();
        String sex = spinnerSex.getSelectedItem().toString();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty() ||
                ageStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(CreateAccountActivity.this, "Te rog completează toate câmpurile!", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        float height = Float.parseFloat(heightStr);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setAge(age);
        newUser.setHeight(height);
        newUser.setSex(sex);

        new Thread(() -> {
            userDao.insertUser(newUser);
            runOnUiThread(() -> {
                Toast.makeText(CreateAccountActivity.this, "Cont creat cu succes!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }).start();
    }
}
