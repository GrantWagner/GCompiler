package net.grantwagner.gcompiler.parser.specs;

import java.io.IOException;

import net.grantwagner.gcompiler.parser.ParseException;
import net.grantwagner.gcompiler.tokenizer.Tokenizer;
import net.grantwagner.gcompiler.tokenizer.model.KeyWords;
import net.grantwagner.gcompiler.tokenizer.model.Token;
import net.grantwagner.gcompiler.tokenizer.model.TokenType;

/**
 * PackageSpec => PackageKeyword
 *                (Identifier PackageSeperator)* 
 *                Identifier
 *                CommandSeperator
 *                
 * Returns: the package name
 */
public class PackageSpec {
  public static String parse(Tokenizer tokenizer) throws ParseException {
    String packName = "";

    Token token = tokenizer.nextToken();
    if (token.getType() != TokenType.KEYWORD
      || !token.getValue().equals(KeyWords.PACKAGE)) {
      //do error handling
      return "";
    }

    while (true) {
      token = tokenizer.nextToken();
      if (token.getType() != TokenType.IDENTIFIER ) {
        //do error handling
      }
      packName += token.getValue();
  
      token = tokenizer.nextToken();
      if (token.getType() == TokenType.PackageSeperator) {
        packName += ".";
      } else if (token.getType() == TokenType.CommandSeperator) {
        break;
      } else {
        //TODO error checking
      }
    }
    
    return packName;
  }
}
