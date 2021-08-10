package rp.edu.sg.c346.id20021576.oursingapore;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Island> islandList;
    CustomAdapter adapter;
    String moduleCode;
    int requestCode = 9;
    Button btn5Stars, btnaddIsl;
    Spinner spnYear;
    ArrayList<String> spnAl;
    ArrayAdapter<String> spnAa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " +  getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
        spnYear = (Spinner) this.findViewById(R.id.spnArea);
        btnaddIsl = (Button) this.findViewById(R.id.buttonaddIsl);

        DBHelper dbh = new DBHelper(this);
        islandList = dbh.getAllIslands();
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, islandList);
        lv.setAdapter(adapter);

        String [] array = getResources().getStringArray(R.array.spinnerYear);
        spnAl = new ArrayList<String>();
        spnAa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spnAl);
        spnYear.setAdapter(spnAa);
        spnAa.add("Filter Years");
        for (int i = 0; i < islandList.size(); i++) {
            spnAa.add(String.valueOf(islandList.get(i).getislandArea()));
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("island", islandList.get(position));
                startActivityForResult(i, requestCode);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                islandList.clear();
                islandList.addAll(dbh.getAllIslandsByStars(5));
                adapter.notifyDataSetChanged();
            }
        });

        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Island> alFilt;
                alFilt = new ArrayList<Island>();
                int yearFind = 0;

                if(spnYear.getSelectedItemPosition() == 0) {
                    lv.setAdapter(adapter);
                } else {
                    yearFind = Integer.parseInt(spnYear.getSelectedItem().toString());
                    for (int i = 0; i < islandList.size(); i++) {
                        if (islandList.get(i).getislandArea() == yearFind) {
                            alFilt.add(islandList.get(i));
                        }
                    }
                    CustomAdapter aaFilt = new CustomAdapter(SecondActivity.this, R.layout.row, alFilt);
                    lv.setAdapter(aaFilt);


                    aaFilt.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        btnaddIsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(SecondActivity.this);
        islandList.clear();
        islandList.addAll(dbh.getAllIslands());
        adapter.notifyDataSetChanged();
    }
}