package dietgerpieters.werkstuk.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.R;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private Button aanmeldBtn;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();


    }

    private void initComponents() {
        this.mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();

        this.username = (EditText) findViewById(R.id.gebruikersnaam);
        this.aanmeldBtn = (Button) findViewById(R.id.aanmeldBtn);

        if (getIntent() != null || getIntent().getStringExtra("Username").trim().length() > 0) {
            username.setText(getIntent().getStringExtra("Username"));
        }
    }

    public void doLogin(View v) {
        String username = this.username.getText().toString();
        if (mDb.userDAO().getUserByName(username) != null) {
            mDb.userDAO().loginUser(username);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(LoginActivity.this, "Deze user bestaat niet", Toast.LENGTH_SHORT).show();
        }

    }
}
