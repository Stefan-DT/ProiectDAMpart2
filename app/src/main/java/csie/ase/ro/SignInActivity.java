package csie.ase.ro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.sing_in_act);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button bLogin = findViewById(R.id.bLogin);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);// Adaugă mesajul în Inten
                intent.putExtra("toastMessage", "Te-ai conectat cu succes!");
                startActivity(intent);
            }
        });

        Button bGoToMain = findViewById(R.id.bGoToMain);


        bGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide MainActivity
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
