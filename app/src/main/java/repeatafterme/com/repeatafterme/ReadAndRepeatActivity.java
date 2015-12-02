package repeatafterme.com.repeatafterme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
This class represents the view and logic for the second game mode, where the user reads an English sentence and repeats it, but doesn't get to hear it.

Shared logic with other modes will be placed in the Engine.class
 */
public class ReadAndRepeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_and_repeat);
    }
}
