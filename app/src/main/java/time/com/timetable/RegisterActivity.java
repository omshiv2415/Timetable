package time.com.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Client mKinveyClient = new Client.Builder("kid_HkLFlSK7e", "51f28035747f4e1dbdd4da5131367cb4"
                , this.getApplicationContext()).build();
        final EditText userName = (EditText)findViewById(R.id.Username);
        final EditText userPassword = (EditText)findViewById(R.id.editTextPassword);

        Button Register = (Button)findViewById(R.id.button) ;
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKinveyClient.user().create(userName.getText().toString(), userPassword.getText().toString(), new KinveyUserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        CharSequence text = user.getUsername() + ", your account has been created.";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        Intent takeUserToHome = new Intent(RegisterActivity.this,MyActivity.class);
                        startActivity(takeUserToHome);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        CharSequence text = "Could not sign up.";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        Button Login = (Button)findViewById(R.id.button2);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserToHome = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(takeUserToHome);
            }
        });

        Button Logout = (Button)findViewById(R.id.button3);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKinveyClient.user().logout().execute();
            }
        });

}}
