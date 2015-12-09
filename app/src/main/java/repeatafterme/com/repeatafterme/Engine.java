package repeatafterme.com.repeatafterme;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.TextView;
import android.widget.Toast;

/*
This class represents all of the shared logic for the game modes as well as other necessary functionality such as System Read/Write
 */
public class Engine {
    private int levelCounter = 0;
    public Engine(){

    }

    // Check if text of view matches spoken text
    public boolean checkSpeech (TextView target ,String data){
        String targetText = target.getText().toString().toUpperCase();
        String speechInput = data.toUpperCase();
        if (targetText.equals(speechInput)){
            return true;
        }else {
            return false;
        }
    }


//    public boolean levelCheck(boolean answer){
//
//        return true;
//    }

}
