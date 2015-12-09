package repeatafterme.com.repeatafterme;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/*
This class represents the view and logic for the second game mode, where the user reads an English sentence and repeats it, but doesn't get to hear it.
Shared logic with other modes will be placed in the Engine.class
 */

public class ReadAndRepeatActivity extends Activity implements View.OnClickListener{
    Data Package;
    Engine Engine;
    protected static final int REQUEST_OK = 1;
    TextView textToRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Engine = new Engine();

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_read_and_repeat);
            textToRead = (TextView) findViewById(R.id.textToRead);
            Package = new Data("ReadAndRepeat");

            initLevel(textToRead);

            Log.d("Contents of Package", Package.Data.toString());
            findViewById(R.id.button1).setOnClickListener(this);

        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "There was an error initializing the mode you selected", Toast.LENGTH_SHORT).show();
        }

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
            Boolean outcome = Engine.checkSpeech(textToRead, speech);
            if(outcome){
                outputText.setText("Correct. Very good!");
                Package.incrementLevel(outcome);
                initLevel(textToRead);
            }else{
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("You said: ");
                stringBuilder.append(speech);
                stringBuilder.append(". Incorrect. Try again!");
                String output = stringBuilder.toString();
                outputText.setText(output);
                Package.incrementLevel(outcome);
                initLevel(textToRead);
            }
        }
    }

    private void initLevel(TextView view){
        String nextEntry = Package.getSpeechString(Package.getCurrentLevel(),Package.Data);
        if (nextEntry == "MODE_FINISHED"){
            finishMode();
        }
        else{
            view.setText(nextEntry);
            Toast.makeText(getApplicationContext(), "Current Level: " + Package.getCurrentLevel(), Toast.LENGTH_SHORT).show();
        }
    }

    private void finishMode(){
        int corrects = Package.getCorrect();
        int incorrects = Package.getIncorrect();
        Toast.makeText(getApplicationContext(), "Mode complete! Congrats!\n Correct: " + corrects + "\n Incorrect: " + incorrects, Toast.LENGTH_SHORT).show();
    }
}
