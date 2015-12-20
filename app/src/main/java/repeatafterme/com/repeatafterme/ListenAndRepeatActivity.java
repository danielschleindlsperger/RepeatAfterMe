package repeatafterme.com.repeatafterme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    Engine Engine;
    Data Package;
    protected static final int REQUEST_OK = 1;
    TextToSpeech t1;
    TextView textToRead;
    TextView EndTxt;
    private ImageView EndImg;
    private ProgressBar progressBar;
    private int progressStatus;
    private Handler handler = new Handler();
    private TextView progressText;
    Button confirmButton;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Engine = new Engine();

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listen_and_repeat);
            textToRead = (TextView) findViewById(R.id.textToRead);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressText = (TextView) findViewById(R.id.textView1);

            // Get Package with game mode speech data, set levels of mode
            Package = new Data("ListenAndRepeat",10);
            progressStatus = 0;

            initLevel(textToRead);

            Log.d("Contents of Package: ", Package.Data.toString());
            findViewById(R.id.button1).setOnClickListener(this);
            createTextToSpeech();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Es gab einen Fehler beim Initialisieren des gewählten Modus", Toast.LENGTH_SHORT).show();
        }

        // Start long running operation in a background thread
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressText = (TextView) findViewById(R.id.textView1);
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor ("#ffd600"), PorterDuff.Mode.SRC_IN);
        defaultProgressbar();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        // Toast with basic game mode instructions
        final Toast instruction = Toast.makeText(getBaseContext(), "Wenn du auf \"Vorsprechen\" klickst wird dir das aktuelle Wort vorgelesen. " +
                "Um es selbst zu wiederholen drücke auf das Mikrofon und warte auf den Ton.", Toast.LENGTH_SHORT);
        instruction.show();
        new CountDownTimer(9000, 1000)
        {
            public void onTick(long millisUntilFinished) {instruction.show();}
            public void onFinish() {instruction.show();}
        }.start();
    }

    protected void createTextToSpeech(){
    t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if(status != TextToSpeech.ERROR) {
                t1.setLanguage(Locale.UK);
            }
            else {
                Toast.makeText(getApplicationContext(), "Fehler beim Initialisieren der Sprachengine.", Toast.LENGTH_SHORT).show();
            }
        }
    });
    }

    // Start SpeechToText Engine
    public void onClick(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-UK");
        try {
            startActivityForResult(i, REQUEST_OK);

        } catch (Exception e) {
            Toast.makeText(this, "Fehler beim Initialisieren der Sprachengine.", Toast.LENGTH_LONG).show();
        }

    }

    // Get results from SpeechToText Engine
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView recognizedText = (TextView) findViewById(R.id.recognized_text);
        TextView textToRead = (TextView) findViewById(R.id.textToRead);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String speech = thingsYouSaid.get(0).toString();
            Boolean outcome = Engine.checkSpeech(textToRead, speech);
            if(outcome){
                recognizedText.setText("Richtig. Sehr gut!");
                Toast.makeText(getApplicationContext(),"Richtig. Sehr gut!", Toast.LENGTH_SHORT).show();
                Package.incrementLevel(outcome);
                initLevel(textToRead);
            }else{
                Toast.makeText(getApplicationContext(),"Leider falsch.", Toast.LENGTH_SHORT).show();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Du hast gesagt: ");
                stringBuilder.append(speech);
                stringBuilder.append(". Leider falsch!");
                String output = stringBuilder.toString();
                recognizedText.setText(output);
                Package.incrementLevel(outcome);
                initLevel(textToRead);
            }
            incProgress();
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
            Toast.makeText(this, "Fehler beim Initialisieren der Sprachengine.", Toast.LENGTH_LONG).show();
        }
    }

    private void initLevel(TextView view){
        String nextEntry = Package.getSpeechString(Package.getCurrentLevel());
        if (nextEntry == "MODE_FINISHED"){
            finishMode();
        }
        else{
            view.setText(nextEntry);
            // Toast.makeText(getApplicationContext(), "Current Level: " + Package.getCurrentLevel(), Toast.LENGTH_SHORT).show();
        }
    }

    // To be called when all levels of game mode have been completed
    private void finishMode(){
        setContentView(R.layout.mode_finished);
        confirmButton = (Button) findViewById(R.id.confirm_finish);
        confirmButton.setOnClickListener(confirmListener);
        setScoreboard();
        int corrects = Package.getCorrect();
        int incorrects = Package.getIncorrect();
        //Toast.makeText(getApplicationContext(), "Mode complete! Congrats!\n Correct: " + corrects + "\n Incorrect: " + incorrects, Toast.LENGTH_SHORT).show();
    }

    // Onclick event to return to main view after finishing a game mode
    View.OnClickListener confirmListener = new View.OnClickListener(){
        public void onClick(View v){
            Intent finished = new Intent(getApplicationContext(), MainActivity.class);
            finished.putExtra("VIEW", "main");
            Log.d("VIEW", finished.getStringExtra("VIEW"));
            startActivity(finished);
        }
    };

    private void setScoreboard(){
        int corrects = Package.getCorrect();
        int incorrects = Package.getIncorrect();

        TextView correct = (TextView) findViewById(R.id.finish_correct);
        TextView incorrect = (TextView) findViewById(R.id.finish_incorrect);

        correct.setText("Richtige Antworten: " + corrects);
        incorrect.setText("Falsche Antworten: " + incorrects);
        setImg();
    }
    public void setImg(){
        EndImg = (ImageView) findViewById(R.id.congrats_image);
        EndTxt = (TextView) findViewById(R.id.congrats_heading);
        int corrects = Package.getCorrect();
        int incorrects = Package.getIncorrect();
        if (incorrects == 0){

    EndImg.setImageResource(R.drawable.thumb_up_gold);
    EndTxt.setText("Perfekt! Du sprichst die Wörter sehr deutlich aus");
}else if (corrects > incorrects){
        EndImg.setImageResource(R.drawable.thumb_up);
        EndTxt.setText("Gute Arbeit!");
        }else if (corrects <= incorrects && corrects != 0){
        EndImg.setImageResource(R.drawable.thumb_right);
        EndTxt.setText("Nicht Schlecht! Versuch die Wörte klarer auszusprechen");
        }else if(corrects == 0){
        EndImg.setImageResource(R.drawable.thumb_down);
        EndTxt.setText("Versuch es noch einmal! Training ist alles");
        }

    }

}