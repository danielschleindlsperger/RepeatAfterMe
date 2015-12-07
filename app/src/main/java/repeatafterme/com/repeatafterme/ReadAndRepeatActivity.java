package repeatafterme.com.repeatafterme;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import repeatafterme.com.repeatafterme.Data;
import repeatafterme.com.repeatafterme.R;

/*
This class represents the view and logic for the second game mode, where the user reads an English sentence and repeats it, but doesn't get to hear it.
Shared logic with other modes will be placed in the Engine.class
 */

public class ReadAndRepeatActivity extends Activity implements View.OnClickListener{

    protected static final int REQUEST_OK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_and_repeat);
        TextView textToRead = (TextView) findViewById(R.id.textToRead);
        Data firstPackage = new Data("ReadAndRepeat");
        Log.d("Contents of Package: ", firstPackage.ReadAndRepeat.toString());

        findViewById(R.id.button1).setOnClickListener(this);
        textToRead.setText(firstPackage.initStrings(firstPackage.ReadAndRepeat));
    }
    public void onClick(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, REQUEST_OK);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView outputText = (TextView) findViewById(R.id.text1);
        TextView textToRead = (TextView) findViewById(R.id.textToRead);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
            String speech = thingsYouSaid.get(0).toString();
            if(checkSpeech(textToRead, speech) == 1){
                outputText.setText("Correct. Very good!");
            }else{
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("You said: ");
                stringBuilder.append(speech);
                stringBuilder.append(". Incorrect. Try again!");
                String output = stringBuilder.toString();
                outputText.setText(output);
            }
        }
    }
    protected int checkSpeech (TextView target ,String data){
        String targetText = target.getText().toString().toUpperCase();
        String speechInput = data.toUpperCase();
        if (targetText.equals(speechInput)){
            return 1;
        }else {
            return 0;
        }
    }
}
