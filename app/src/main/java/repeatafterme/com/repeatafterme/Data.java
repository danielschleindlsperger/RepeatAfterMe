package repeatafterme.com.repeatafterme;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 This class represents the data sets for the different game modes as well as various related methods
 */
public class Data {

    ArrayList<String> Data;
    private int levelCount;
    private int correct;
    private int incorrect;
    // Construct data set based on supplied mode
    public Data(String mode) throws Exception {
        Data = new ArrayList<String>();
        levelCount = 0;
        if (mode == "ListenAndRepeat"){
            initListenAndRepeat();

        }else if (mode == "ReadAndRepeat"){
            initReadAndRepeat();
        }
        else{
            throw new NullPointerException();
        }
    }


    // Return dataset for next level
    public String getSpeechString(int level, ArrayList arr){
        if (level < arr.size()){
            String Entry = arr.get(level).toString();
            return Entry;
        }
        else{
            return "MODE_FINISHED";
        }
    }

    public void incrementLevel(Boolean answer){
        if (answer){
            correct++;
        }
        if(!answer){
            incorrect++;
        }
         levelCount++;
    }


    public int getCurrentLevel(){
        return levelCount;
    }

    public int getCorrect(){
        return correct;
    }

    public int getIncorrect(){
        return incorrect;
    }

    // Add entries for Listen and Repeat game mode
    private void initListenAndRepeat(){
        Data.add("Hello");
        Data.add("Test");
        Data.add("Water");
        Data.add("Team");
//        Data.add("People");
//        Data.add("Map");
//        Data.add("Thanks");
//        Data.add("Group");
//        Data.add("Food");
//        Data.add("Bird");
    }

    // Add entries for Read and Repeat game mode
    private void initReadAndRepeat(){
        Data.add("Hello");
        Data.add("Test");
        Data.add("Water");
        Data.add("House");
        Data.add("Desk");
        Data.add("Tree");
        Data.add("A tree has many leaves");
//        ReadAndRepeat.add("Roberta ran rings around the Roman ruins.");
        Data.add("Stupid answer");
        Data.add("If you want respect you have to earn it");
    }


    public static int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
