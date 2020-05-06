package com.yoji.tasktimelimits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private CalendarView startCalendarView;
    private CalendarView endCalendarView;
    private Button chooseStartDateButton;
    private Button chooseEndDateButton;
    private Button okButton;

    private long startDate;
    private String startDateText;
    private String endDateText;
    private GregorianCalendar gregorianCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        startCalendarView = findViewById(R.id.startDateCalendarId);
        endCalendarView = findViewById(R.id.endDateCalendarId);
        chooseStartDateButton = findViewById(R.id.chooseStartDateButtonId);
        chooseEndDateButton = findViewById(R.id.chooseEndDateButtonId);
        okButton = findViewById(R.id.okButtonId);

        chooseStartDateButton.setOnClickListener(chooseStartDateButtonOnClickListener);
        chooseEndDateButton.setOnClickListener(chooseEndDateButtonOnClickListener);
        okButton.setOnClickListener(okButtonOnClickListener);

        startCalendarView.setOnDateChangeListener(startCalendarOnDateChangeListener);
        endCalendarView.setOnDateChangeListener(endCalendarOnDateChangeListener);

        chooseStartDateButton.setText(getString(R.string.start_date_and_time, ""));
        chooseEndDateButton.setText(getString(R.string.end_date_and_time, ""));

        startCalendarView.setVisibility(View.GONE);
        endCalendarView.setVisibility(View.GONE);
    }

    View.OnClickListener chooseStartDateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startCalendarView.setVisibility(View.VISIBLE);
            endCalendarView.setVisibility(View.GONE);
        }
    };

    View.OnClickListener chooseEndDateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startCalendarView.setVisibility(View.GONE);
            endCalendarView.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener okButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String toastMessage = getString(R.string.toast_message, startDateText, endDateText);
            Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG).show();
            chooseStartDateButton.setText(getString(R.string.start_date_and_time, ""));
            chooseEndDateButton.setText(getString(R.string.end_date_and_time, ""));
        }
    };

    CalendarView.OnDateChangeListener startCalendarOnDateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
            startDateText = i+"-"+i1+"-"+i2;
            chooseStartDateButton.setText(getString(R.string.start_date_and_time, startDateText));
            gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(i, i1, i2);
            startDate = gregorianCalendar.getTimeInMillis();
            startCalendarView.setVisibility(View.GONE);
            chooseEndDateButton.setEnabled(true);
        }
    };

    CalendarView.OnDateChangeListener endCalendarOnDateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
            endDateText = i+"-"+i1+"-"+i2;
            chooseEndDateButton.setText(getString(R.string.end_date_and_time, endDateText));
            gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(i, i1, i2);
            long endDate = gregorianCalendar.getTimeInMillis();
            endCalendarView.setVisibility(View.GONE);
            if (startDate > endDate){
                Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                chooseEndDateButton.setText(getString(R.string.end_date_and_time, ""));
                endDate = 0;
            }else{
                okButton.setEnabled(true);
            }
        }
    };
}
