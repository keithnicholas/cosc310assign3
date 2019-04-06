Class Structure

GUI Class:
-Constructor(): Initialize appropriate listener for every GUI Component.
-processUserInput(): Controller that send the user query to Bot class for processing and add the typed text to the screen.
-Main(): Initialize Jframe and Bot class. Feed keyword and responses as the bot references/model. 

Bot Class:
-Bot(name): initializing method, sets name of bot
-createResponseList(list): Initializes and sets the keys and responses list
-getSpecial(match,input): this method returns any special cases of responses
    that require additional conditions and variables
-getWeather(city): this method builds a string for the weather information
    of a given city
-getName(): this method returns the name of the bot
-getResponse(input): this method does the pattern matching and input checking.
    If there are any special input matches it calls getSpecial(). It returns a string containing the bot response.

Weather Class: Gather weather information of a city
-weather(location): Initialize Weather Class and perform a GET request to open weather API
-getTemp(): return the temperature of the city
-getWeatherMain(): return the main description of the weather
-getWeatherDesc(): this method returns a more thorough description of the city's weather
-setWeatherInfo(): this method processes parse the given JSON and sets the weather information fields
-setTemp(): parse given JSON and gets the weather temperature

SpellCheck class : Perform a spelling check on a sentence
-SpellCheck(): Constructor for class and call setNewSentence method
-setNewSentence(sentence): set new sentence for the class and call process method
-process(): process the given sentence and set fixed sentence member field with the corrected sentence (if any)
-getCorrectedSentence(): returns the corrected sentence
-getRawSentence(): returns the unprocessed sentence, if needed

5 points: At the end of your README file, include:
o a list of each feature you programmed for this assignment
o for each item on that list, explain briefly how you used that feature to
improve your agent's conversation or your overall system
o for each explanation, give a snippet of a conversation that demonstrates
your feature

List of feature programmed:
Note: Spellcheck for error checking was completed with group not individual
A. added several new topics
B. GUI for seeing recent conversation
C. Synonyms
D. 5 different reasonable responses when the user enters something outside the two topic

Explanation:
A. My bot is now able to understand more diverse topics
B. Program is more user friendly and users may see their recent conversations with the bot
C. My algorithm works by checking for every tokenized user words whether the same words appears in the array of responses vocabulary or not. Synonym recognition allows my algorithm to work without having these words exactly similar (desire/like) so it is more intelligent.
D. Bot is now more "human like" as it is able to try guess responses.

Example:
A. 
User: What do you know about car
Bot: I like fast car

You: what is best league
Sandra: Barclay is good

C. You: do you feel starving
Sandra: I cannot feel hunger

You: do you feel hungry
Sandra: I cannot feel hunge

D. You: do you play
Sandra: I cant play football, I dont have legs

Note: it tries to match with the most similar sentences
