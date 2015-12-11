package repeatafterme.com.repeatafterme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/*
This class represents the view and logic for the first game mode, where the user listens to an English sentence and repeats it.

Shared logic with other modes will be placed in the Engine.class
 */
public class ListenAndRepeatActivity extends Activity implements View.OnClickListener {
    public final static String VIEW = "repeatafterme.com.repeatafterme.VIEWTOLOAD";
    Engine Engine;
    Data Package;
    protected static final int REQUEST_OK = 1;
    TextToSpeech t1;
    TextView textToRead;
    private ProgressBar progressBar;
    private int progressStatus;
    private Handler handler = new Handler();
    private TextView progressText;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Engine = new Engine();

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listen_and_repeat);
            textToRead = (TextView) findViewById(R.id.textToRead);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressText = (TextView) findViewById(R.id.textView1);
            Package = new Data("ListenAndRepeat", 5);
            progressStatus = 0;

            initLevel(textToRead);

            Log.d("Contents of Package: ", Package.Data.toString());
            findViewById(R.id.button1).setOnClickListener(this);
            createTextToSpeech();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "There was an error initializing the mode you selected", Toast.LENGTH_SHORT).show();
        }
        // Start long running operation in a background thread
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressText = (TextView) findViewById(R.id.textView1);
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor ("#ffd600"), PorterDuff.Mode.SRC_IN);
        defaultProgressbar();


    }

    protected void createTextToSpeech(){
    t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if(status != TextToSpeech.ERROR) {
                t1.setLanguage(Locale.UK);
            }
            else {
                Toast.makeText(getApplicationContext(), "Sorry, there was an error", Toast.LENGTH_SHORT).show();
            }
        }
    });
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
            //((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
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
                stringBuilder.append(". Incorrect.");
                String output = stringBuilder.toString();
                outputText.setText(output);
                Package.incrementLevel(outcome);
                initLevel(textToRead);
            }
            incProgress();
            //((TextView)findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
        }
    }

// ProgressBar logic
    public void incProgress() {
        progressBar.setMax(Package.getMaxLevel());
        new Thread(new Runnable() {
            public void run() {
                if (progressStatus <= progressBar.getMax()) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            progressText.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
public void defaultProgressbar(){
    progressBar.setMax(Package.getMaxLevel());
    progressBar.setProgress(progressStatus);
    progressText.setText(progressStatus + "/" + progressBar.getMax());
}
    public String getData(){
       TextView textToRead = (TextView) findViewById(R.id.textToRead);
       String textAsString = (String) textToRead.getText();
       return textAsString;
    }

    public void talkText(View v){

        String data = getData();
        //String TextAsSting = textToRead.getText().toString();
        try {
            t1.speak(data, TextToSpeech.QUEUE_FLUSH, null);
        }catch(Exception e){
            Toast.makeText(this, "Error initializing talk text engine.", Toast.LENGTH_LONG).show();
        }

    }

    private void initLevel(TextView view){
        String nextEntry = Package.getSpeechString(Package.getCurrentLevel());
        if (nextEntry == "MODE_FINISHED"){
            finishMode();
        }
        else{
            view.setText(nextEntry);
            Toast.makeText(getApplicationContext(), "Current Level: " + Package.getCurrentLevel(), Toast.LENGTH_SHORT).show();
        }
    }

    private void finishMode(){
        setContentView(R.layout.mode_finished);
        confirmButton = (Button) findViewById(R.id.confirm_finish);
        confirmButton.setOnClickListener(confirmListener);
        int corrects = Package.getCorrect();
        int incorrects = Package.getIncorrect();
        Toast.makeText(getApplicationContext(), "Mode complete! Congrats!\n Correct: " + corrects + "\n Incorrect: " + incorrects, Toast.LENGTH_SHORT).show();
    }

    // Onclick event to return to main view after finishing a game mode
    View.OnClickListener confirmListener = new View.OnClickListener(){
        public void onClick(View v){
            Intent finished = new Intent(getApplicationContext(), MainActivity.class);
            finished.putExtra(VIEW, "main");
            startActivity(finished);
        }
    };
}