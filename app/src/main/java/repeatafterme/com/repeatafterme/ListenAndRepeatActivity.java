package repeatafterme.com.repeatafterme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
This class represents the view and logic for the first game mode, where the user listens to an English sentence and repeats it.

Shared logic with other modes will be placed in the Engine.class
 */
public class ListenAndRepeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_and_repeat);
    }
}
