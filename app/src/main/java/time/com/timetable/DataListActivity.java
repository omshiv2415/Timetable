package time.com.timetable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

                String mCode, mStartTime, mFinishTime;
                mCode = cursor.getString(0);
                mStartTime = cursor.getString(1);
                mFinishTime = cursor.getString(2);
                DataProvider dataProvider = new DataProvider(mCode, mStartTime, mFinishTime);
                listDataAdapter.add(dataProvider);


            } while (cursor.moveToNext());

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                final TextView moduleCode = (TextView) view.findViewById(R.id.textModuleCode);
                AlertDialog.Builder builder = new AlertDialog.Builder(DataListActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {


                        String deleteMCode = moduleCode.getText().toString().trim();
                        timeTableDbHelper.deleteRowFromTimeTable(deleteMCode);
                        Toast.makeText(DataListActivity.this, "Module Code " + deleteMCode + " deleted ",
                                Toast.LENGTH_SHORT).show();
                        Intent takeUserToHome = new Intent(DataListActivity.this, MyActivity.class);
                        startActivity(takeUserToHome);
                        dialog.dismiss();
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

}
