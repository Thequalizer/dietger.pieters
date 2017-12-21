package dietgerpieters.werkstuk.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;

public class WedstrijdDetailActivity extends AppCompatActivity {

    Wedstrijd w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedstrijd_detail);

        w = (Wedstrijd) getIntent().getSerializableExtra("wedstrijd");

        TextView textView = findViewById(R.id.titelValue);
        textView.setText(w.getTitel());
    }
}
