package repeatafterme.com.repeatafterme;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
This class represents the home screen view and all of its logic.

 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show login view first. After successful login show main view
        setContentView(R.layout.login);
        // Set username and password input hints
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");

        // Button onclick bind
        final Button loginbtn = (Button) findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                // Get username and password input
                String username = usernameWrapper.getEditText().getText().toString();
                String password = passwordWrapper.getEditText().getText().toString();

                Log.d("username", username);
                Log.d("password", password);

                // User and password validation
                if (!validateUser(username)) {
                    usernameWrapper.setError("Not a valid username! At least 6 characters required.");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password! At least 6 characters required.");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    doLogin();
                    welcomeMessage(username);
                }
            }
        });


        }

    // Hide Android keyboard if no view in focus
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // Username validation
    public boolean validateUser(String user) {
        return user.length() > 5;
        //TODO Add more validation logic
    }
// Make individual welcome message
    private void welcomeMessage(String user) {
        final TextView textViewToChange = (TextView) findViewById(R.id.text_welcome);
        textViewToChange.setText(
                "Welcome " + user + "! What do you want to do?");
    }

    // Password validation
    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    private void doLogin(){
        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();

        // Setup main view
        createMainView();
    }
    private void createMainView(){

        // Set view to main activity view
        setContentView(R.layout.activity_main);

    }
    public void startGame(View v){
        String mode = v.getTag().toString();
        Toast.makeText(getApplicationContext(), mode, Toast.LENGTH_SHORT).show();
        Log.d("Mode selected", mode);
//        Intent intent = new Intent(getApplicationContext(),ReadAndRepeatActivity.class);
//        startActivity(intent);
        if (mode.equalsIgnoreCase("ListenAndRepeat")){
            Intent LARIntent = new Intent(getApplicationContext(), ListenAndRepeatActivity.class);
            startActivity(LARIntent);
        }
        if (mode.equalsIgnoreCase("ReadAndRepeat")){
            Intent RARIntent = new Intent(getApplicationContext(), ReadAndRepeatActivity.class);
            startActivity(RARIntent);
        }
    }
}
