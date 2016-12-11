package time.com.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kinvey.android.Client;


public class MyActivity extends Activity {


    private Button AddTimeTable;
    private Button VieTimeTable;
    private Button DeleteTimeTable;
    public Client mKinveyClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mKinveyClient = new Client.Builder("kid_HkLFlSK7e", "51f28035747f4e1dbdd4da5131367cb4"
                , this.getApplicationContext()).build();
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mKinveyClient.user().logout().execute();
    }
}
