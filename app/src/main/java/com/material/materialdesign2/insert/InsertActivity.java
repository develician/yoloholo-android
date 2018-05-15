package com.material.materialdesign2.insert;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.material.materialdesign2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InsertActivity extends AppCompatActivity implements OnSelectDateListener {

    private DrawerLayout insertDrawer;
    private CalendarView calendarView;
    private Date departDate;

    private Button selectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInsert);
        setSupportActionBar(toolbar);


        Calendar calendar = Calendar.getInstance();
        Date currentTime = Calendar.getInstance().getTime();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        insertDrawer = (DrawerLayout) findViewById(R.id.insertDrawer);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        try {
            calendarView.setDate(calendar);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }


        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                departDate = clickedDayCalendar.getTime();
            }
        });

//        calendarView.setOnDayClickListener(v -> {
//            departDate = calendarView.getSelectedDate().getTime();
////            for (Calendar calendar2 : calendarView.getSelectedDates()) {
////                System.out.println(calendar2.getTime().toString());
////
////                departDate = calendar2.getTime();
////
//////                Toast.makeText(getApplicationContext(),
//////                        calendar2.getTime().toString(),
//////                        Toast.LENGTH_SHORT).show();
////            }
//        });

        DatePickerBuilder builder = new DatePickerBuilder(this, this)
                .pickerType(CalendarView.RANGE_PICKER)
                .date(calendar);

        DatePicker rangePicker = builder.build();


        selectButton = (Button) findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(InsertActivity.this, ArriveDateActivity.class);
//                intent.putExtra("departDate", departDate);
//                startActivity(intent);
                rangePicker.show();
            }
        });



    }

    @Override
    public void onSelect(List<Calendar> calendars) {
        Stream.of(calendars).forEach(calendar ->
                Toast.makeText(getApplicationContext(),
                        calendar.getTime().toString(),
                        Toast.LENGTH_SHORT).show());
    }
}
