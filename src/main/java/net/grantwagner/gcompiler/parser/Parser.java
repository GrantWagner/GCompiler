package net.grantwagner.gcompiler.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.grantwagner.gcompiler.parser.model.ProgramSymbols;
import net.grantwagner.gcompiler.parser.specs.FileSpec;
import net.grantwagner.gcompiler.tokenizer.Tokenizer;

public class Parser {
  public ProgramSymbols parseIdentifiers(List<File> files) throws IOException {

    ProgramSymbols symbols = new ProgramSymbols();
    for (File file : files) {
      Tokenizer tokenizer = new Tokenizer(file);
      ProgramSymbols fileSymbols = FileSpec.parse(tokenizer);

      symbols.add(fileSymbols);
    }

    return symbols;
  }
}
