package net.grantwagner.gcompiler.parser.specs;

import net.grantwagner.gcompiler.parser.ParseException;
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
  public static String parse(Tokenizer tokenizer) throws ParseException {
    String importName = "";

    Token token = tokenizer.nextToken();
    if (token.getType() != TokenType.KEYWORD
      || !token.getValue().equals(KeyWords.IMPORT)) {
      throw new ParseException();
    }

    while (true) {
      token = tokenizer.nextToken();
      if (token.getType() != TokenType.IDENTIFIER ) {
        throw new ParseException();
      }
      importName += token.getValue();
  
      token = tokenizer.nextToken();
      if (token.getType() == TokenType.PackageSeperator) {
        importName += ".";
      } else if (token.getType() == TokenType.CommandSeperator) {
        break;
      } else {
        throw new ParseException();
      }
    }
    
    return importName;
  }
}
