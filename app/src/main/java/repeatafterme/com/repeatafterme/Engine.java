package repeatafterme.com.repeatafterme;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.TextView;
import android.widget.Toast;

/**
This class represents all of the shared logic for the game modes as well as other necessary functionality such as System Read/Write
 */
public class Engine {

    /**
     * Constructor.
     */
    public Engine(){

    }


    /**
     * <p>Checks user speech input against original text</p>
     * @param target word to be read
     * @param data speech input
     * @return correctness of speech input
     */
    public boolean checkSpeech (TextView target ,String data){
        String targetText = target.getText().toString().toUpperCase();
        String speechInput = data.toUpperCase();
        if (targetText.equals(speechInput)){
            return true;
        }else {
            return false;
        }
    }
}
