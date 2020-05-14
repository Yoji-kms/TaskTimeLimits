package com.yoji.tasktimelimits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

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

    private View.OnClickListener chooseStartDateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startCalendarView.setVisibility(View.VISIBLE);
            endCalendarView.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener chooseEndDateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startCalendarView.setVisibility(View.GONE);
            endCalendarView.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener okButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String toastMessage = getString(R.string.toast_message, startDateText, endDateText);
            Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG).show();
            chooseStartDateButton.setText(getString(R.string.start_date_and_time, ""));
            chooseEndDateButton.setText(getString(R.string.end_date_and_time, ""));
        }
    };

    private CalendarView.OnDateChangeListener startCalendarOnDateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
            startDateText = year + "-" + month +"-" + dayOfMonth;
            chooseStartDateButton.setText(getString(R.string.start_date_and_time, startDateText));
            gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(year, month, dayOfMonth);
            startDate = gregorianCalendar.getTimeInMillis();
            startCalendarView.setVisibility(View.GONE);
            chooseEndDateButton.setEnabled(true);
        }
    };

    private CalendarView.OnDateChangeListener endCalendarOnDateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
            endDateText = year + "-" + month + "-" + dayOfMonth;
            chooseEndDateButton.setText(getString(R.string.end_date_and_time, endDateText));
            gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(year, month, dayOfMonth);
            long endDate = gregorianCalendar.getTimeInMillis();
            endCalendarView.setVisibility(View.GONE);
            if (startDate > endDate){
                Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                chooseEndDateButton.setText(getString(R.string.end_date_and_time, ""));
            }else{
                okButton.setEnabled(true);
            }
        }
    };
}
