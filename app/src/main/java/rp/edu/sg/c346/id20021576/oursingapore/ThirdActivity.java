package rp.edu.sg.c346.id20021576.oursingapore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etName, etDesc, etArea;
    Button btnCancel, btnUpdate, btnDelete;
    RatingBar rtBarEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_third));

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etID = (EditText) findViewById(R.id.etID);
        etName = (EditText) findViewById(R.id.etName);
        etDesc = (EditText) findViewById(R.id.etDesc);
        etArea = (EditText) findViewById(R.id.etArea);
        rtBarEdit = findViewById(R.id.ratingBarEdit);

        Intent i = getIntent();
        final Island currentIsland = (Island) i.getSerializableExtra("island");

        etID.setText(currentIsland.getId()+"");
        etName.setText(currentIsland.getName());
        etDesc.setText(currentIsland.getDesc());
        etArea.setText(currentIsland.getislandArea()+"");

        rtBarEdit.setRating(currentIsland.getStars());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentIsland.setName(etName.getText().toString().trim());
                currentIsland.setDesc(etDesc.getText().toString().trim());
                int area = 0;
                try {
                    area = Integer.valueOf(etArea.getText().toString().trim());
                } catch (Exception e){
                    Toast.makeText(ThirdActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentIsland.setislandArea(area);

                int result = dbh.updateIsland(currentIsland);
                if (result>0){
                    Toast.makeText(ThirdActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DBHelper dbh = new DBHelper(ThirdActivity.this);
//                int result = dbh.deleteIsland(currentIsland.getId());
//                if (result>0){
//                    Toast.makeText(ThirdActivity.this, "Song deleted", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent();
//                    setResult(RESULT_OK);
//                    finish();
//                } else {
//                    Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("DANGER");
                myBuilder.setMessage("Are you sure you want to delete the island");
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        int result = dbh.deleteIsland(currentIsland.getId());
                        if (result>0){
                            Toast.makeText(ThirdActivity.this, "Island deleted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ThirdActivity.this, SecondActivity.class);
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Island failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                            myBuilder.setTitle("DANGER");
                            myBuilder.setMessage("Are you sure you want to discard the changes");
                            myBuilder.setCancelable(false);
                            myBuilder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etName.setText("");
                                    etDesc.setText("");
                                    etArea.setText("");
                                    rtBarEdit.setRating(0);
                                }
                            });
                            myBuilder.setNegativeButton("DO NOT DISCARD", null);

                            AlertDialog myDialog = myBuilder.create();
                            myDialog.show();
                        }
                });
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}