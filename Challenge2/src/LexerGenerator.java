import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.regex.*;

/**
 * This class generates a lexer array of the code provided. It is basically and array that contains
 * all the symbols found in the input file.
 */
public class LexerGenerator {

  // Variables for storing the input and the output.
  BufferedReader inputBuffer;
  List<Map.Entry<String, String>> lexerArray = new ArrayList<>();

  // Constants for the symbols.
  String CLEAR = "CLEAR";
  String INCR = "INCR";
  String DECR = "DECR";
  String WHILE = "WHILE";
  String NOT = "NOT";
  String DO = "DO";
  String SEMICOLON = "SEMICOLON";
  String NAME = "NAME";
  String END = "END";

  public LexerGenerator(BufferedReader br) {
    this.inputBuffer = br;
  }

  /** Print the input recived line by line. Used for debugging. */
  public void printBuffer() {
    try {
      for (String line; (line = inputBuffer.readLine()) != null; ) {
        System.out.println(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Print the output array with all the symbols generated. */
  public void printList() {
    for (Map.Entry<String, String> entry : lexerArray) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }
  }

  /**
   * Lexer generator method. Uses regex to identify all the symbols and builds a map with their
   * names and order.
   *
   * @return The generated map of all the symbols found in the input file.
   */
  public List<Map.Entry<String, String>> generate() {
    Matcher matcher;
    String regexClear = "\\Bclear|clear\\B";
    String regexIncr = "\\Bincr|incr\\B";
    String regexDecr = "\\Bdecr|decr\\B";
    String regexWhile = "\\Bwhile|while\\B";

    try {
      for (String line; (line = inputBuffer.readLine()) != null; ) {
        // Remove all whitespaces
        line = line.replaceAll("\\s", "");

        // Matchers for clear, increase, decrease and while loops.
        matcher = generateMatcher(line, regexClear);
        addEntryLexerArray(matcher, CLEAR, "clear", line);

        matcher = generateMatcher(line, regexIncr);
        addEntryLexerArray(matcher, INCR, "incr", line);

        matcher = generateMatcher(line, regexDecr);
        addEntryLexerArray(matcher, DECR, "decr", line);

        matcher = generateMatcher(line, regexWhile);

        // Special processing for whiles because they can have negation.
        if (matcher.find()) {
          processWhileMatch(line);
        }

        // Additional processing of the end symbol.
        if (line.equals("end;")) {
          processEndMatch();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return lexerArray;
  }

  /**
   * Helper method that generates matchers for the regexes and lines given.
   *
   * @param line The line of code to apply the matcher to.
   * @param regex The regex expresion to apply to the line.
   * @return The matcher generated.
   */
  private Matcher generateMatcher(String line, String regex) {
    Matcher matcher;
    Pattern pattern;

    pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    matcher = pattern.matcher(line);

    return matcher;
  }

  /**
   * Processor method that adds a symbol and its values to the map. It also takes as input the key
   * and value for the operation symbol associated with the variable.
   *
   * @param matcher the matcher generated with the line and regex operation.
   * @param key the key for the map entry.
   * @param value the value to be saved for that entry.
   * @param line the line to take value from.
   */
  private void addEntryLexerArray(Matcher matcher, String key, String value, String line) {
    if (matcher.find()) {
      Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<String, String>(key, value);
      lexerArray.add(entry);

      // Save characters until the semicolon.
      Pattern pattern = Pattern.compile(value + "(.*?);");
      matcher = pattern.matcher(line);
      while (matcher.find()) {
        entry = new AbstractMap.SimpleEntry<String, String>(NAME, matcher.group(1));
        lexerArray.add(entry);
      }
      entry = new AbstractMap.SimpleEntry<String, String>(SEMICOLON, ";");
      lexerArray.add(entry);
    }

    return;
  }

  /**
   * Helper method that adds the while statement and the variable it references to the map.
   *
   * @param line the line to process.
   */
  private void processWhileMatch(String line) {
    Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<String, String>(WHILE, "while");
    lexerArray.add(entry);

    addEntryLexerArrayBetweenStrings(line, "while", "not");

    entry = new AbstractMap.SimpleEntry<String, String>(NOT, "not");
    lexerArray.add(entry);

    addEntryLexerArrayBetweenStrings(line, "not", "do");

    entry = new AbstractMap.SimpleEntry<String, String>(DO, "do");
    lexerArray.add(entry);

    entry = new AbstractMap.SimpleEntry<String, String>(SEMICOLON, ";");
    lexerArray.add(entry);
  }

  /** Helper method that adds the end statement to the map. */
  private void processEndMatch() {
    Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<String, String>(END, "end");
    lexerArray.add(entry);
    entry = new AbstractMap.SimpleEntry<String, String>(SEMICOLON, ";");
    lexerArray.add(entry);
  }

  /**
   * Processor method that takes the variables between two symbols in the line. It adds the found
   * variable or value to the map.
   *
   * @param line the line to process.
   * @param start the symbol to start from.
   * @param end the symbol to stop at.
   */
  private void addEntryLexerArrayBetweenStrings(String line, String start, String end) {
    Pattern pattern = Pattern.compile(start + "(.*?)" + end);
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      Map.Entry<String, String> entry =
          new AbstractMap.SimpleEntry<String, String>(NAME, matcher.group(1));
      lexerArray.add(entry);
    }

    return;
  }
}
