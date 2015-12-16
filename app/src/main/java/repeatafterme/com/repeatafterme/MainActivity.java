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

        // Set view to main instead of login based on intent extra
        Intent viewIntent = getIntent();
        String view = viewIntent.getStringExtra("VIEW");

        if (null != view && view.matches("main")){
            setContentView(R.layout.activity_main);
        }else {
            setContentView(R.layout.login);

            // Set username and password input hints
            final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
            final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
            usernameWrapper.setHint("Benutzername");
            passwordWrapper.setHint("Passwort");

            // Button onclick bind
            final Button loginBtn = (Button) findViewById(R.id.loginbtn);
            loginBtn.setOnClickListener(new View.OnClickListener() {
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
                        usernameWrapper.setError("Kein gültiger Benutzername. Mindestens 6 Zeichen benötigt.");
                    } else if (!validatePassword(password)) {
                        passwordWrapper.setError("Kein gültiges Passwort. Mindestens 6 Zeichen benötigt.");
                    } else {
                        usernameWrapper.setErrorEnabled(false);
                        passwordWrapper.setErrorEnabled(false);
                        doLogin();
                        welcomeMessage(username);
                    }
                }
            });

        }
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

    // Password validation
    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    // Make individual welcome message
    private void welcomeMessage(String user) {
        final TextView welcomeText = (TextView) findViewById(R.id.text_welcome);
        welcomeText.setText(
                "Hallo " + user + "! Was willst du machen?");
    }

    private void doLogin(){
        Toast.makeText(getApplicationContext(), "Login erfolgreich!", Toast.LENGTH_SHORT).show();

        // Setup main view
        createMainView();
    }

    public void createMainView(){

        // Set view to main activity view
        setContentView(R.layout.activity_main);

    }

    // Start a game mode activity based on selection
    public void startGame(View v){
        String mode = v.getTag().toString();
        // Toast.makeText(getApplicationContext(), mode, Toast.LENGTH_SHORT).show();
        Log.d("Mode selected", mode);

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
