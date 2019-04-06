import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Gui {
    private static Bot sandra;
    private JTextField textField1;
    private JButton proceedButton;
    private JTextArea textArea1;
    private JLabel label1;
    private JTextField inputTextField;
    private JButton sendButton;
    private JPanel panelMain;
    private JTextField textField3;
    private JRadioButton standardRadio;
    private JRadioButton clientRadioButton;
    private ServerSocket realServerSocket;
    private Socket realClientSocket;
    private PrintWriter realServerOut;
    private BufferedReader realServerIn;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isServer = false;

    public Gui() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputTextField.getText();
                processUserInput(text);

            }
        });
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputTextField.getText();
                processUserInput(text);
            }
        });
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        standardRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        clientRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gui");
        frame.setContentPane(new Gui().panelMain);
        frame.setSize(768, 1024);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        sandra = new Bot("Sandra");

        //any specials with a key have separate checking conditions for response
        //to get key/index in list, you can say the statement and then say: /get match
        //NOTE: you can split words using // for different word matches
        // (ie. what//how => can mean either what or how can be used. Be sure not to use the same word!)

        String[][] keywordsandAswers = {
                {"how//what", "is", "weather", sandra.getWeather("Kelowna")},
                {"how", "is", "weather", "in", "[city/place]"}, //key=1, special
                {"how", "is", "food", "I'm a robot, I don't eat food!"},
                {"where", "do", "you", "live", "On your hard drive"},
                {"what", "is", "your", "name", sandra.getName() + ", what's yours?"},
                {"who", "is", "president", "USA", "Donald Trump"},
                {"who", "is", "president", "canada", "Trudeau"},
                {"what", "is", "favourite", "movie", "Forrest Gump"},
                {"I", "am", "tired", "Remember quitters never win, and winners never quit"},
                {"That", "is", "true", "I know, I'm a robot. We're mostly right all the time"},
                {"Can", "you", "keep", "secret", "go ahead"},
                {"Hello//hi//sup", "We've said hi already..."},
                {"I", "was", "reading", "news", "oh yeah?"},
                {"They", "are", "building", "new", "cafe", "on", "campus", "oh I heard about that! Do you want to go have coffee sometime?"},
                {"You", "can't", "have", "coffee", "you're", "a", "robot", "oh yeah... That's true..."},
                {"Anyways", "what's", "up?", "not much, just talking with you"},
                {"You're", "fun!", "thanks"},
                {"What's", "for", "dinner", "do you only ever talk about food?"},
                {"Sorry", "it's okay. Anyways, how was your day?"},
                {"It", "was", "good", "that's nice, what did you do?"},
                {"Went", "to", "the", "mall", "Nice. Did you get anything?"},
                {"I", "got", "shoes", "Nice! If I had a computer vision algorithm, I could see them",},
                {"Do", "you", "want", "to", "play", "a", "game?", "sure, what's the game?"},
                {"I'll", "tell", "you", "movie", "quotes,", "you'll", "tell", "me", "where,", "they're", "from", "okay, but I'm a robot, I'm unbeatable"},
                {"Stop", "bragging.", "let", "it", "go,", "let", "it", "go", "too easy! The cold never bothered me anyway."},
                {"Life", "is", "like", "a", "box", "of", "chocolates", "that's literally from my favorite movie"},
                {"Who", "gonna", "save", "the", "world", "tonight?", "that's a song..."},
                {"Tsamina", "mina", "eh", "eh", "waka waka, eh eh"},
                {"You're", "pretty", "good", "thanks"},
                {"Your", "turn", "May the force be with you"},
                {"Ah", "see", "you're", "not", "that", "smart", "Sorry, I could not... Hehehe just kiddig I got that."},
                {"How", "are", "you", "good thanks"},
                {"do", "you", "know", "politic", "I don't know politics"},
                {"why//what", "are//is", "politic//politics", "good//bad", "I'm uniformed about politic"},
                {
                        "do//does", "you", "like", "sport", "I like sport"
                },
                {"what", "can", "you", "do", "I can do anything to make you happy"},
                {"what", "is", "a", "life", "Dunno, its too philosophical"},
                {"where", "is", "location", "canada", "north of USA"},
                {"do", "you", "know", "soccer", "Yeah,I like UEFA and worldcup"},
                {"are", "you", "dumb", "I am a smart dumb hehe"},
                {"who", "made", "you", "5 other people whom I don't know"},
                {"what", "do", "you", "think", "about", "math", "Math is hard"},
                {"can", "you", "write", "code", "I cannot write code"},
                {"can", "you", "play", "guitar", "I cannot play instrument"},
                {"what", "do", "you", "know", "about", "car", "I like fast car"},
                {"do", "you", "know", "car", "top speed", "ford mustang top speed is about 280 km/h"},
                {"what", "is", "mustang", "engine", "displacement", "mustang uses V8 vroom vromo"},
                {"what", "is", "football", "a sport that is done with a foot"},
                {"do", "you", "play", "football", "I cant play football, I dont have legs"},
                {"what", "soccer", "team", "do", "you", "know", "viva la madrid"},
                {"what", "basketball", "team", "you", "like", "Lakers is pretty good"},
                {"can", "you", "play", "basketball", "sport", "I cannot play sport, I am an AI"}
        };
        // in this 2D array, we are storing the keywords and the answer they match to.
        // Each row contains one answer, and all the corresponding keywords
        sandra.createResponseList(keywordsandAswers);
        /*Scanner in = new Scanner(System.in); // to read user's input
        while (true) {
            String currentInput = in.nextLine(); // read the user's input

            //quit the program if user says "bye"
            if (currentInput.toLowerCase().equals("bye"))
                break;

            //get bot response
            sandra.getResponse(currentInput);
        }
        in.close();
        System.out.println("I'll see you");*/
    }

    private void processUserInput(String text) {
        inputTextField.setText("");
        //Stop user entered nothing
        if (textArea1.getText().equals("") || inputTextField.getText().equals(null) || inputTextField.getText().equals("")) {
            //return;
        }
        textArea1.append("You: " + text + "\n");
        //JOptionPane.showMessageDialog(null,text);
        //Update text area with new user text
        String botResp = sandra.getResponse(text);
        textArea1.append(sandra.getName() + ": " + botResp + "\n");

    }
}
