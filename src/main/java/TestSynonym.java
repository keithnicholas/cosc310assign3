import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class TestSynonym {
    public static void main(String[] arg) {

        String path = "C:\\Program Files (x86)\\WordNet\\2.1\\dict";
        System.setProperty("wordnet.database.dir", path);

        WordNetDatabase database = WordNetDatabase.getFileInstance();
        String word = "desire";
        Synset[] synsets = database.getSynsets(word);
        //  Display the word forms and definitions for synsets retrieved
        Set<String> uniqueSynonym = null;
        if (synsets.length > 0) {
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
                for (String s : uniqueSynonym) {
                    System.out.println(s);
                }
            }
        } else {
            System.err.println("No synsets exist that contain the word form '" + word + "'");
        }


    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }
}
