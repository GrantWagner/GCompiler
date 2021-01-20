package net.grantwagner.gcompiler.parser.specs;

import java.io.IOException;
import java.util.List;

import net.grantwagner.gcompiler.parser.ParseException;
import net.grantwagner.gcompiler.parser.ParserUtil;
import net.grantwagner.gcompiler.parser.model.ProgramSymbols;
import net.grantwagner.gcompiler.tokenizer.Tokenizer;

public class FileSpec {

  public static ProgramSymbols parse(Tokenizer tokenizer) throws IOException {
    ProgramSymbols symbols = new ProgramSymbols();

    String packName;
    try {
      packName = PackageSpec.parse(tokenizer);
    } catch (ParseException e1) {
      throw new RuntimeException("File {x} has a malformed package decloration.");
    }
    System.out.println("Package is " + packName);

    
    List<String> imports = ParserUtil.parseZeroToN(tokenizer, (localTokenizer) -> ImportSpec.parse(localTokenizer));
    System.out.println("Imports are " + imports);

    return symbols;
  }
}
