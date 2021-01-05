package net.grantwagner.gcompiler.parser.specs;

import java.io.IOException;

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


  public static String parse(Tokenizer tokenizer) throws IOException {
    String packName = "";

    Token token = tokenizer.nextToken();
    if (token.getType() != TokenType.KEYWORD
      || !token.getValue().equals(KeyWords.PACKAGE)) {
      //do error writting
      return "";
    }

    while (true) {
      token = tokenizer.nextToken();
      if (token.getType() != TokenType.IDENTIFIER ) {
        //do error writting
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
