package repeatafterme.com.repeatafterme;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
This class represents the entry point to the application. It also represents the home screen view and some of its view based logic.
 <p>On the first start it shows a login screen. After successful validation the main activity view is loaded.
 After completion of a game mode the main activity view is loaded also.</p>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set view to main instead of login based on intent extra
        /**
         * Intent to determine if main view or login view is to be loaded
         */
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

    /** Hide Android keyboard if no view in focus*/
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * <h5>
     *     Validates the user
     * </h5>
     * <p>
     *     Currently the validation is only based on the length of the user name. More advanced validation can be added here.
     * </p>
     *
     * @param user(required) username that was entered in the login form
     * @return boolean of (un)successful validation
     */
    public boolean validateUser(String user) {
        return user.length() > 5;
        //TODO Add more validation logic
    }

    /**
     * <h5>
     *     Validates the password
     * </h5>
     * <p>
     *     Currently the validation is only based on the length of the password. More advanced validation can be added here.
     * </p>
     *
     * @param password(required) password that was entered in the login form
     * @return boolean of successful validation
     */
    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    /**
     * <h5>
     *     Generate individual welcome message
     * </h5>
     * <p>
     *     Takes the username from the input form and generates a greetings message which is placed on the home view of the app.
     * </p>
     *
     * @param user(required) username that was entered in the login form
     */
    private void welcomeMessage(String user) {
        final TextView welcomeText = (TextView) findViewById(R.id.text_welcome);
        welcomeText.setText(
                "Hallo " + user + "! Was willst du machen?");
    }

    /**
     * <h5>
     *     Login user
     * </h5>
     * <p>
     *     This Method is part of the login process. It generates a toast to indicate the success of the login process and calls another function to set up the main view.
     * </p>
     *
     */
    private void doLogin(){
        Toast.makeText(getApplicationContext(), "Login erfolgreich!", Toast.LENGTH_SHORT).show();

        // Setup main view
        createMainView();
    }

    /**
     * <h5>
     *     Set up main view
     * </h5>
     * <p>
     *     This Method is part of the login process. It sets the view to the layout of the main activity.
     * </p>
     *
     */
    public void createMainView(){

        // Set view to main activity view
        setContentView(R.layout.activity_main);

    }

    // Start a game mode activity based on selection

    /**
     *  <h5>
     *      Start a game mode
     *  </h5>
     *  <p>
     *      Gets the game mode from the supplied view's tag and starts the corresponding game mode.
     *  </p>
     * @param v(required)   View element with tag which describes the game mode to be started
     */
    public void startGame(View v){
        String mode = v.getTag().toString();

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
