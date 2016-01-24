package repeatafterme.com.repeatafterme;

import java.util.ArrayList;
import java.util.Random;

/**
 This class represents the data sets for the different game modes as well as various related methods
 */
public class Data {
    /**
     * Wordlist contained in array
     */
    ArrayList<String> Data;
    /**
     * Current game modes level count
     */
    private int levelCount;
    /**
     * Number of correct answers
     */
    private int correct;
    /**
     * Number of incorrect answers
     */
    private int incorrect;
    /**
     * Maximum level of game mode
     */
    private int maxLevel;

    /** Constructor
     * <p>Create data set based on supplied mode with corresponding word list array array</p>
     *
     * @param mode game mode that is to be started
     * @param maxLevel maximum level for game mode
     * @throws Exception if supplied game mode does not exist
     */
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

    /**
     * Get next entry for level
     * @param level current level of game mode
     * @return word for next level of game mode
     */
    public String getSpeechString(int level){
        if (level < maxLevel){
            String Entry = this.Data.get(level).toString();
            return Entry;
        }
        else{
            return "MODE_FINISHED";
        }
    }

    /**
     * Increments level counter and also (in)correct answer counter
     * @param answer Correctness of answer
     */
    public void incrementLevel(Boolean answer){
        if (answer){
            correct++;
        }
        if(!answer){
            incorrect++;
        }
         levelCount++;
    }

    /**
     *
     * @return counter of current level
     */
    public int getCurrentLevel(){
        return levelCount;
    }

    /**
     *
     * @return correct answers
     */
    public int getCorrect(){
        return correct;
    }

    /**
     *
     * @return incorrect answers
     */
    public int getIncorrect(){
        return incorrect;
    }

    /**
     *
     * @return maximum level of game mode
     */
    public int getMaxLevel(){
        return maxLevel;
    }


    /**
     * Add entries to data array for game mode Listen And Repeat
     */
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

    /**
     * Add entries to data array for game mode Read And Repeat
     */
    private void initReadAndRepeat(){
        Data.add("Hello");
        Data.add("Window");
        Data.add("Blue");
        Data.add("Chair");
        Data.add("Desk");
        Data.add("Dog");
        Data.add("Tree");
        Data.add("School");
        Data.add("A tree has many leaves");
        Data.add("If you want respect you have to earn it");
    }
}
