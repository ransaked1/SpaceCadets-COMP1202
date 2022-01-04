import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A Bare Bones language interpreter written in Java driver class.
 *
 * @param args Standard terminal input.
 */
public class BareBones {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
      LexerGenerator lexer = new LexerGenerator(br);
      lexer.generate();

      // Debug prints
      // lexer.printBuffer();
      // lexer.printList();

      Compiler comp = new Compiler(lexer);
      comp.compileCode();
      comp.printState();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
