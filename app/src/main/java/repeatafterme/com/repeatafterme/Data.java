package repeatafterme.com.repeatafterme;

import java.lang.reflect.Array;
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

    public String initStrings(ArrayList arr){
        int arrLength = arr.size();
        int randomIndex = randInt(0, arrLength-1);
        String RandomEntry = arr.get(randomIndex).toString();
        return RandomEntry;
    }

    // Add entries for Listen and Repeat game mode
    private void initListenAndRepeat(){
        ListenAndRepeat.add("Hello");
        ListenAndRepeat.add("Test");
        ListenAndRepeat.add("Water");
        ListenAndRepeat.add("House");
        ListenAndRepeat.add("The sixth sick sheik's sixth sheep's sick.");
        ListenAndRepeat.add("I want to be a juror on a rural brewery robbery case.");
        ListenAndRepeat.add("How many boards\n" +
                "Could the Mongols hoard\n" +
                "If the Mongol hordes got bored?");
        ListenAndRepeat.add("How can a clam cram in a clean cream can?");
        ListenAndRepeat.add("The thirty-three thieves thought that they thrilled the throne throughout Thursday.");
        ListenAndRepeat.add("Can you can a can as a canner can can a can?");



    }

    // Add entries for Read and Repeat game mode
    private void initReadAndRepeat(){
//        ReadAndRepeat.add("Hello");
//        ReadAndRepeat.add("Test");
//        ReadAndRepeat.add("Water");
//        ReadAndRepeat.add("House");
//        ReadAndRepeat.add("Desk");
//        ReadAndRepeat.add("Tree");
        ReadAndRepeat.add("Seth at Sainsbury's sells thick socks.");
        ReadAndRepeat.add("Roberta ran rings around the Roman ruins.");
        ReadAndRepeat.add("Stupid superstition!");
        ReadAndRepeat.add("There was a fisherman named Fisher\n" +
                "who fished for some fish in a fissure.\n" +
                "Till a fish with a grin,\n" +
                "pulled the fisherman in.\n" +
                "Now they're fishing the fissure for Fisher.");
    }


    public static int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
