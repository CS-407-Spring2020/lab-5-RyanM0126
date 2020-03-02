package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String user;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences(
                "c.sakshi.lab5", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString(usernameKey, "").equals("")) {
            // the value returned by sharedPreferences for the usernameKey was NOT "", meaning a
            // username key is already being stored
            user = sharedPreferences.getString(usernameKey, "");
            Intent loginIntent = new Intent(this, SecondActivity.class);
            loginIntent.putExtra("user", user);
            startActivity(loginIntent);
        } else {
            // SharedPreferences object has no username key set, so start main activity to have
            // user log in
            setContentView(R.layout.activity_main);
        }

    }

    public void login(View view) {
        user = ((EditText)findViewById(R.id.username)).getText().toString();
        password = ((EditText)findViewById(R.id.password)).getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(
                "c.sakshi.lab5", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", user).apply();

        Intent loginIntent = new Intent(this, SecondActivity.class);
        loginIntent.putExtra("user", user);
        startActivity(loginIntent);
    }
}
