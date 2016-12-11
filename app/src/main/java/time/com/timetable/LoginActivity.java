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

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userName = (EditText)findViewById(R.id.editTextLoginUserName);
        final EditText userPassword = (EditText)findViewById(R.id.editTextUserLoginPassword);
        Button userLogin = (Button)findViewById(R.id.btnLogin);
        Button userRegister = (Button)findViewById(R.id.btnRegister);

        final Client mKinveyClient = new Client.Builder("kid_HkLFlSK7e", "51f28035747f4e1dbdd4da5131367cb4"
                , this.getApplicationContext()).build();

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKinveyClient.user().login(userName.getText().toString(), userPassword.getText().toString(), new KinveyUserCallback() {
                    @Override
                    public void onFailure(Throwable t) {
                        CharSequence text = "Wrong username or password.";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(User u) {
                        CharSequence text = "Welcome back," + u.getUsername() + ".";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        Intent takeUserToHome = new Intent(LoginActivity.this,MyActivity.class);
                        startActivity(takeUserToHome);
                    }
                });
            }
        });


        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserToRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(takeUserToRegister);
            }
        });
    }

}

