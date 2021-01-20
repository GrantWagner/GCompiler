package net.grantwagner.gcompiler.parser;

import java.util.ArrayList;
import java.util.List;

import net.grantwagner.gcompiler.tokenizer.Tokenizer;
import net.grantwagner.gcompiler.tokenizer.TokenizerState;

public class ParserUtil {

  @FunctionalInterface
  public interface ParserFunction<State> {
    State apply(Tokenizer tokenizer) throws ParseException;
  }

  public static <State> List<State> parseZeroToN(Tokenizer tokenizer, ParserUtil.ParserFunction<State> parser) {
    List<State> imports = new ArrayList<>();
    while(true) {
      TokenizerState tokenizerState = null;
      try {
        tokenizerState = tokenizer.getState();
        State importName = parser.apply(tokenizer);
        imports.add(importName);
      } catch (ParseException e) {
        tokenizer.setState(tokenizerState);
        break;
      }
    }
    
    return imports;
  }

}
