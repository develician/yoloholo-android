package com.material.materialdesign2.insert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.material.materialdesign2.R;

import java.util.Date;

public class SummaryActivity extends AppCompatActivity {

    private Date arriveDate, departDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();
        arriveDate = (Date) intent.getSerializableExtra("arriveDate");
        departDate = (Date) intent.getSerializableExtra("departDate");


        Toast.makeText(this, arriveDate + ", " + departDate, Toast.LENGTH_SHORT).show();
    }
}
