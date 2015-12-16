package repeatafterme.com.repeatafterme;

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
    private int maxLevel;
    // Construct data set based on supplied mode
    public Data(String mode, int maxLevel) throws Exception {
        Data = new ArrayList<String>();
        levelCount = 0;
        this.maxLevel = maxLevel;
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
    public String getSpeechString(int level){
        if (level < maxLevel){
            String Entry = this.Data.get(level).toString();
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

    public int getMaxLevel(){
        return maxLevel;
    }

    // Add entries for Listen and Repeat game mode
    private void initListenAndRepeat(){
        Data.add("Pencil");
        Data.add("Teacher");
        Data.add("January");
        Data.add("Team");
        Data.add("People");
        Data.add("Map");
        Data.add("Thanks");
        Data.add("Group");
        Data.add("Food");
        Data.add("Bird");
    }

    // Add entries for Read and Repeat game mode
    private void initReadAndRepeat(){
        Data.add("Hello");
        Data.add("Dog");
        Data.add("Blue");
        Data.add("Chair");
        Data.add("Desk");
        Data.add("Tree");
        Data.add("Window");
        Data.add("School");
        Data.add("A tree has many leaves");
        //Data.add("Stupid answer");
        Data.add("If you want respect you have to earn it");
    }
}
