package com.material.materialdesign2.insert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.material.materialdesign2.R;

import java.util.Calendar;
import java.util.Date;

public class ArriveDateActivity extends AppCompatActivity {
    private Date departDate;
    private CalendarView calendarView;
    private Date arriveDate;
    private Button selectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrive_date);
        Intent intent = getIntent();

        departDate = (Date) intent.getSerializableExtra("departDate");

        Toast.makeText(this, departDate + "", Toast.LENGTH_SHORT).show();

        calendarView = (CalendarView)findViewById(R.id.calendarViewArrive);

        Calendar calendar = Calendar.getInstance();
        Date currentTime = Calendar.getInstance().getTime();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        try {
            calendarView.setDate(calendar);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                arriveDate = clickedDayCalendar.getTime();
            }
        });

        selectButton = (Button) findViewById(R.id.selectButtonArrive);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArriveDateActivity.this, SummaryActivity.class);
                intent.putExtra("arriveDate", arriveDate);
                intent.putExtra("departDate", departDate);
                startActivity(intent);
            }
        });

    }
}
