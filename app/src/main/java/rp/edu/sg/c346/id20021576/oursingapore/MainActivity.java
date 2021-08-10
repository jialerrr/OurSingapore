package rp.edu.sg.c346.id20021576.oursingapore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSingers, etYear;
    Button btnInsert, btnShowList;
    RatingBar rtBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_main));

        etTitle = findViewById(R.id.etName);
        etSingers = findViewById(R.id.etDesc);
        etYear = findViewById(R.id.etArea);
        btnInsert = findViewById(R.id.btnInsertIsland);
        btnShowList = findViewById(R.id.btnShowList);
        rtBar = findViewById(R.id.ratingBarList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etTitle.getText().toString().trim();
                String singers = etSingers.getText().toString().trim();
                if (title.length() == 0 || singers.length() == 0){
                    Toast.makeText(MainActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                    return;
                }

                String year_str = etYear.getText().toString().trim();
                int year = Integer.valueOf(year_str);
                float stars = getStars();

                DBHelper dbh = new DBHelper(MainActivity.this);
                long result = dbh.insertIsland(title, singers, year, stars);

                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Island inserted", Toast.LENGTH_LONG).show();
                    etTitle.setText("");
                    etSingers.setText("");
                    etYear.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                }


            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

    }

    private float getStars() {

        float stars = rtBar.getRating();
        return stars;
    }

}
