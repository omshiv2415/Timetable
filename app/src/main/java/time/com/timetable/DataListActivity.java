package time.com.timetable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import time.com.timetable.DB.DataProvider;
import time.com.timetable.DB.ListDataAdapter;
import time.com.timetable.DB.TimeTableDbHelper;


public class DataListActivity extends Activity {
    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    TimeTableDbHelper timeTableDbHelper;
    Cursor cursor;
    ListDataAdapter listDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        listView = (ListView) findViewById(R.id.list_view);
        listDataAdapter = new ListDataAdapter(getApplicationContext(), R.layout.row_layout);
        listView.setAdapter(listDataAdapter);

        timeTableDbHelper = new TimeTableDbHelper(getApplicationContext());
        sqLiteDatabase = timeTableDbHelper.getReadableDatabase();
        cursor = timeTableDbHelper.getTimeTable(sqLiteDatabase);


        if (cursor.moveToFirst()) {

            do {

                String mCode, mStartTime, mFinishTime, mShiftDate;
                mCode = cursor.getString(0);
                mStartTime = cursor.getString(1);
                mFinishTime = cursor.getString(2);
                mShiftDate = cursor.getString(3);
                DataProvider dataProvider = new DataProvider(mCode, mStartTime, mFinishTime,mShiftDate);
                listDataAdapter.add(dataProvider);


            } while (cursor.moveToNext());

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final TextView moduleCode = (TextView) view.findViewById(R.id.textModuleCode);
                final TextView mStartDate = (TextView) view.findViewById(R.id.textStartTime);
                final TextView mFinishTime = (TextView) view.findViewById(R.id.textFinishTime);
                final TextView mSendDate = (TextView) view.findViewById(R.id.textViewShiftDate);

                AlertDialog.Builder builder = new AlertDialog.Builder(DataListActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to send an Email ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        sendEmail(moduleCode.getText().toString(),mStartDate.getText().toString(),mFinishTime.getText().toString()
                                  , mSendDate.getText().toString());

                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


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
        emailIntent.setPackage("com.google.android.gm");
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Worked Hours Time Sheet");
        emailIntent.putExtra(Intent.EXTRA_TEXT, str);

        try {
            startActivity(emailIntent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DataListActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
