package time.com.timetable;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import time.com.timetable.DB.TimeTableDbHelper;


public class TimeTableModule extends Activity {
    static final int TIME_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID_FINISH = 1;
    private static final String TIME09T018HOURS_PATTERN =
            "(0[9]|1[0-7]):[0-5][0-9]";
    Context context = this;
    TimeTableDbHelper timeTableDbHelper;
    SQLiteDatabase sqLiteDatabase;
    private EditText ModuleCode;
    private EditText StartTime;
    private EditText FinishTime;
    private EditText mDate;
    private Button AddModule;
    private int pHour;
    private int pMinute;
    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    /**
     * Callback received when the user "picks" a time in the dialog
     */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    updateDisplay();
                    displayToastStart();
                }
            };
    private TimePickerDialog.OnTimeSetListener mFinishTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    updateDisplay_finish();
                    displayToastFinsih();
                }
            };

    /**
     * Add padding to numbers less than ten
     */
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_module);

        ModuleCode = (EditText) findViewById(R.id.editTextModuleCode);
        StartTime = (EditText) findViewById(R.id.editTextStarTime);
        FinishTime = (EditText) findViewById(R.id.editTextFinishTime);
        AddModule = (Button) findViewById(R.id.btnAddModule);
        mDate = (EditText)findViewById(R.id.editTextDate);

        StartTime.setKeyListener(null);
        FinishTime.setKeyListener(null);
        mDate.setKeyListener(null);
        /** Listener for click event of the button */
        StartTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        FinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID_FINISH);

            }
        });
        /** Get the current time */
        final Calendar cal = Calendar.getInstance();
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);

        /** Display the start time */
        updateDisplay();
        /** Display the finish time */
        updateDisplay_finish();

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        AddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mCode = ModuleCode.getText().toString();
                String mStartTime = StartTime.getText().toString();
                String mFinishTime = FinishTime.getText().toString();
                String mShiftDate = mDate.getText().toString();

                if (!mCode.matches("^[A-Za-z]{2}[0-9]{3}$")) {

                    Toast.makeText(getBaseContext(), "Please try 2 Letters and 3 Numbers", Toast.LENGTH_LONG).show();


                } else if (!mStartTime.matches(TIME09T018HOURS_PATTERN)) {

                    Toast.makeText(getBaseContext(), "Invalid StartTime Must be 9 AM To 6 PM", Toast.LENGTH_LONG).show();


                } else if (!mFinishTime.matches(TIME09T018HOURS_PATTERN)) {


                    Toast.makeText(getBaseContext(), "Invalid Finish Time Must be 9 AM To 6 PM", Toast.LENGTH_LONG).show();


                } else {

                    String str = mStartTime;
                    String str1 = mFinishTime;

                    DateFormat formatter = new SimpleDateFormat("HH:MM");
                    try {
                        Date sTime = formatter.parse(str);
                        Date fTime = formatter.parse(str1);
                        if (fTime.before(sTime)) {

                            Toast.makeText(getBaseContext(), "Invalid Finish Time ", Toast.LENGTH_LONG).show();
                        } else {

                            timeTableDbHelper = new TimeTableDbHelper(context);
                            sqLiteDatabase = timeTableDbHelper.getWritableDatabase();
                            timeTableDbHelper.addTimeTableDetail(mCode, mStartTime, mFinishTime,mShiftDate, sqLiteDatabase);
                            Toast.makeText(getBaseContext(), "Time Table saved", Toast.LENGTH_LONG).show();
                            timeTableDbHelper.close();

                            sendEmail(mCode,mStartTime,mFinishTime,mShiftDate);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
    protected void sendEmail(String mCode, String mStartTime, String mFinishTime, String mShiftDate ) {

        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String str = "Module Code: "+ mCode + System.getProperty("line.separator")
                   + "Start Time: " + mStartTime + System.getProperty("line.separator")
                   + "Finish Time: " + mFinishTime + System.getProperty("line.separator")
                   + "Work Date: " + mShiftDate;
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, str);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(TimeTableModule.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Updates the time in the TextView
     */
    private void updateDisplay() {
        StartTime.setText(
                new StringBuilder()
                        .append(pad(pHour)).append(":")
                        .append(pad(pMinute)));

    }

    /**
     * Updates the time in the TextView
     */
    private void updateDisplay_finish() {

        FinishTime.setText(
                new StringBuilder()
                        .append(pad(pHour)).append(":")
                        .append(pad(pMinute)));
    }

    /**
     * Displays a notification when the time is updated
     */
    private void displayToastStart() {
        Toast.makeText(this, new StringBuilder().append("Time chosen is ").append(StartTime.getText()),
                Toast.LENGTH_SHORT).show();
    }
    private void displayToastFinsih() {
        Toast.makeText(this, new StringBuilder().append("Time chosen is ").append(FinishTime.getText()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, pHour, pMinute, false);

            case TIME_DIALOG_ID_FINISH:

                return new TimePickerDialog(this,
                        mFinishTimeSetListener, pHour, pMinute, false);
            case DATE_DIALOG_ID:
                // set date picker as current date
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                return new DatePickerDialog(this, datePickerListener,
                        mYear, mMonth,mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            mDate.setText(new StringBuilder().append(day)
                    .append("-").append(month +1).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            //dpResult.init(year, month, day, null);

        }
    };

}
