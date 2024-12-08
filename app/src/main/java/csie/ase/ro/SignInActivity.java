package csie.ase.ro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in_act);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button bLogin = findViewById(R.id.bLogin);

        bLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            new Thread(() -> {
                HttpsLoginValidator httpsValidator = new HttpsLoginValidator(email, password);
                boolean validOnServer = httpsValidator.validateLogin();

                if (validOnServer) {
                    runOnUiThread(() -> {
                        Toast.makeText(SignInActivity.this, "Te-ai conectat cu succes la server!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(intent);
                    });
                } else {
                    UserDB db = UserDB.getInstance(SignInActivity.this);
                    UserDAO userDao = db.userDao();
                    User user = userDao.getUser(email, password);

                    runOnUiThread(() -> {
                        if (user != null) {
                            Toast.makeText(SignInActivity.this, "Te-ai conectat cu succes din baza de date!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignInActivity.this, "Email sau parolă incorecte!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        });

    }
}
