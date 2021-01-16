package net.grantwagner.gcompiler.parser.specs;

import java.io.IOException;

import net.grantwagner.gcompiler.tokenizer.Tokenizer;
import net.grantwagner.gcompiler.tokenizer.model.KeyWords;
import net.grantwagner.gcompiler.tokenizer.model.Token;
import net.grantwagner.gcompiler.tokenizer.model.TokenType;

/**
 * ImportSpec => ImportKeyWord 
 *               (Identifier PackageSeperator)* 
 *               TypeIdentifier
 *               CommandSeperator
 *               
 * Returns: the package name and type name
 */
public class ImportSpec {
  public static String parse(Tokenizer tokenizer) throws IOException {
    String importName = "";

    Token token = tokenizer.nextToken();
    if (token.getType() != TokenType.KEYWORD
      || !token.getValue().equals(KeyWords.IMPORT)) {
      //do error handling
      return "";
    }

    while (true) {
      token = tokenizer.nextToken();
      if (token.getType() != TokenType.IDENTIFIER ) {
        //do error handling
      }
      importName += token.getValue();
  
      token = tokenizer.nextToken();
      if (token.getType() == TokenType.PackageSeperator) {
        importName += ".";
      } else if (token.getType() == TokenType.CommandSeperator) {
        break;
      } else {
        //TODO error checking
      }
    }
    
    return importName;
  }
}
