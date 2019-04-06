import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("Duplicates")
/**
 * Created by Agent on 2019-02-12.
 */
public class Bot {
    String name;
    String[][] keyAns;
    int bestMatch;
    String currentUserSentence = "";
    SpellCheck spell;
    private JPanel panelMain;
    private JTextArea textArea1;

    Bot(String name) {
        this.name = name;
        //System.out.println("Hi, my name is Sandra");
        bestMatch = -1;
        //initialize spell checker
        try {
            spell = new SpellCheck("");
        } catch (Exception e) {
        }
        System.setProperty("wordnet.database.dir", "C:\\Program Files (x86)\\WordNet\\2.1\\dict");
    }

    public void createResponseList(String[][] list) {
        keyAns = list;
        for (int x = 0; x < keyAns.length; x++) {
            for (int y = 0; y < keyAns[x].length - 1; y++) {
                keyAns[x][y] = keyAns[x][y].replaceAll("[^A-Za-z0-9 ]", "");
            }
        }
        System.out.println(Arrays.deepToString(keyAns));
    }

    /**
     * Process Responses
     *
     * @param input User text/query
     * @return bot Responses
     */
    public String getResponse(String input) {
        //for getting location of previous match
        int bestMatch = -1;
        if (input.toLowerCase().equals("/get match")) {
            if (bestMatch != -1)
                System.out.println("this bestmac: " + bestMatch);
            //return;
        }

        //try to check for spelling errors
        try {
            spell.setNewSentence(input);
        } catch (Exception e) {
        }
        input = spell.getCorrectedSentence();
        //remove any non-alphabet/space characters
        input = input.replaceAll("[^A-Za-z0-9 ]","");
        //split input into separate words
        String[] in = input.split(" ");


        //find best match for response
        bestMatch = -1;
        int okMatch = -1;
        int matchCount = 0;
        int oldCount = -1;
        //first loop goes through list of sentences to match
        for (int x = 0; x < keyAns.length; x++) {
            int pos = 0;
            //goes through list of input words
            for (int y = 0; y < in.length; y++) {
                //split all words with a backslash (/) and compare each split word individually

                String[] keys = keyAns[x][pos].split("//");

                for (int i = 0; i < keys.length; i++) {
                    //if there is a match, the position increments, and once all
                    //words from keywords are matched it is the best match

                    /*
                    Get synonyms for each input and vocab words, if both synonyms contain something in commmon, then we consider it as the same
                     */
                    ArrayList<String> synonymCorpus = getSynonym(keys[i]);
                    ArrayList<String> synonymText = getSynonym(in[y]);
                    Boolean SynonymMatch = containsSimilarWord(synonymCorpus, synonymText);
                    if (in[y].toLowerCase().equals((keys[i]).toLowerCase()) || SynonymMatch == true || StringUtils.containsIgnoreCase(in[y], keys[i])) {
                        pos++;
                        System.out.println(pos);
                        //if all keywords are matched
                        if (pos >= keyAns[x].length - 1 && matchCount < pos) {
                            matchCount = pos;
                            bestMatch = x;
                            y = in.length;
                            System.out.println("best match and y: " + pos + " " + bestMatch +" "+y);

                        }
                        if (pos >= 2 && oldCount < pos) {
                            okMatch = x;
                        }
                    }
                }
            }
            oldCount = pos;
        } //end of loops
        //check if there is a special case for these keywords
        String sp = this.getSpecial(bestMatch, in);
        String toReturn = "";
        System.out.println("bot responding: " + matchCount);
        if (sp.length() > 0) {

            toReturn = sp;
            return toReturn;
        } else if (bestMatch != -1 || okMatch != -1) {

            //System.out.println(keyAns[bestMatch][keyAns[bestMatch].length - 1]);
            //toReturn= keyAns[bestMatch][keyAns[bestMatch].length - 1];
            int which;
            if (bestMatch != -1) {
                which = bestMatch;
            } else {
                which = okMatch;
            }
            toReturn = keyAns[which][keyAns[which].length - 1];
        } else {

            toReturn = "Sorry, I could not understand what you are saying.";
        }
        return toReturn;
    }

    //this method checks for special cases of input which require further processing
    private String getSpecial(int match, String[] input) {
        switch (match) {
            case 1:
                String city = "Kelowna";
                for (int x = 0; x < input.length; x++)
                    if (input[x].toLowerCase().equals("in") && x < input.length - 1)
                        city = input[x + 1];
                return getWeather(city);
        }

        return "";
    }

    public String getWeather(String city) {
        Weather w = new Weather(city);
        return "We have " + w.getWeatherDesc() + " in " + city + " and the temperature is " + w.getTemp() + " °C";
    }

    /**
     * Get current user sentence
     *
     * @return current user sentence
     */
    public String getCurrentUserSentence() {
        return currentUserSentence;
    }

    /**
     * Get bot name
     * @return bot name
     */
    public String getName() {
        return name;
    }

    /**
     * Return synonym for each word
     *
     * @param word
     * @return
     */
    public ArrayList<String> getSynonym(String word) {
        String path = "C:\\Program Files (x86)\\WordNet\\2.1\\dict";
        System.setProperty("wordnet.database.dir", path);

        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(word);
        //  Display the word forms and definitions for synsets retrieved
        Set<String> uniqueSynonym = new HashSet<>();
        if (synsets != null && synsets.length > 0) {
            ArrayList<String> al = new ArrayList<String>();
            // add elements to al, including duplicates
            HashSet hs = new HashSet();
            for (int i = 0; i < synsets.length; i++) {
                String[] wordForms = synsets[i].getWordForms();
                for (int j = 0; j < wordForms.length; j++) {
                    al.add(wordForms[j]);
                }


                //removing duplicates
                hs.addAll(al);
                al.clear();
                al.addAll(hs);
                uniqueSynonym = new HashSet<String>();
                //removing duplicate using set
                for (int f = 0; f < al.size(); f++) {
                    uniqueSynonym.add(al.get(f));
                }

            }
        } else {
            uniqueSynonym.add("73c84bd6516a15cf46ced59ffd7498d2"); //not used
        }

        return new ArrayList<String>(uniqueSynonym);

    }

    private boolean containsSimilarWord(ArrayList<String> oneArray, ArrayList<String> second) {
        for (int i = 0; i < oneArray.size(); i++) {
            if (second.contains(i)) {
                return true;
            }
        }
        return false;
    }
}
