package net.grantwagner.gcompiler.parser.specs;

import java.io.IOException;

import net.grantwagner.gcompiler.parser.model.ProgramSymbols;
import net.grantwagner.gcompiler.tokenizer.Tokenizer;

public class FileSpec {

  public static ProgramSymbols parse(Tokenizer tokenizer) throws IOException {
    ProgramSymbols symbols = new ProgramSymbols();

    String packName = PackageSpec.parse(tokenizer);
    System.out.print("Package is " + packName);
    
    return symbols;
  }

}
