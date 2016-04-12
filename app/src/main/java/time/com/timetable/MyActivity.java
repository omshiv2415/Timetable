package time.com.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity {

    private Button AddTimeTable;
    private Button VieTimeTable;
    private Button DeleteTimeTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        AddTimeTable = (Button) findViewById(R.id.btnAdd);
        VieTimeTable = (Button) findViewById(R.id.btnViewTimeTable);

        DeleteTimeTable = (Button) findViewById(R.id.btnDetelteTimeTable);

        AddTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserToAddTimeTable = new Intent(MyActivity.this, TimeTableModule.class);
                startActivity(takeUserToAddTimeTable);
            }
        });
        VieTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserToViewTimeTable = new Intent(MyActivity.this, DataListActivity.class);
                startActivity(takeUserToViewTimeTable);

            }
        });

        DeleteTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeUserToAddTimeTable = new Intent(MyActivity.this, DataListActivity.class);
                startActivity(takeUserToAddTimeTable);
            }
        });

    }


}
