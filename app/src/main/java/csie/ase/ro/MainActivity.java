package csie.ase.ro;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("token", "token1234");
        editor.apply();

        Button bSignIn = findViewById(R.id.bSignIn);
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        Button bCreateAccount = findViewById(R.id.bCreateAccout);
        bCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        Button bInfo = findViewById(R.id.bInfo);
        bInfo.setOnClickListener(v->{
            Toast.makeText(this,"Dumitru Ștefan - 1094D",Toast.LENGTH_LONG).show();
        });
    }
}
