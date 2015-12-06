package repeatafterme.com.repeatafterme;

import java.util.ArrayList;
import java.util.Random;

/**
 This class represents the data sets for the different game modes as well as various related methods
 */
public class Data {

    ArrayList<String> ListenAndRepeat = new ArrayList<String>();
    ArrayList<String> ReadAndRepeat = new ArrayList<String>();

    // Construct data set based on supplied mode
    public Data(String mode){
        if (mode == "ListenAndRepeat"){
            initListenAndRepeat();
        }else if (mode == "ReadAndRepeat"){
            initReadAndRepeat();
        }
    }

    public String initStrings(){
        int arrLength = ListenAndRepeat.size();
        int randomIndex = randInt(0, arrLength-1);
        String RandomEntry = ListenAndRepeat.get(randomIndex);
        return RandomEntry;
    }

    // Add entries for Listen and Repeat game mode
    private void initListenAndRepeat(){
        ListenAndRepeat.add("Hello");
        ListenAndRepeat.add("Test");
        ListenAndRepeat.add("Water");
        ListenAndRepeat.add("House");
        ListenAndRepeat.add("Desk");
        ListenAndRepeat.add("Tree");
    }

    // Add entries for Read and Repeat game mode
    private void initReadAndRepeat(){
        ListenAndRepeat.add("Hello");
        ListenAndRepeat.add("Test");
        ListenAndRepeat.add("Water");
        ListenAndRepeat.add("House");
        ListenAndRepeat.add("Desk");
        ListenAndRepeat.add("Tree");
    }


    public static int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
