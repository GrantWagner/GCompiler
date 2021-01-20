package net.grantwagner.gcompiler.tokenizer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.grantwagner.gcompiler.parser.ParseException;
import net.grantwagner.gcompiler.tokenizer.model.KeyWords;
import net.grantwagner.gcompiler.tokenizer.model.Token;
import net.grantwagner.gcompiler.tokenizer.model.TokenType;

//TODO make this properly data driven
public class Tokenizer {
  
  Collection<Character> identifierStart = Set.of(
    'a',
    'b',
    'c',
    'd',
    'e',
    'f',
    'g',
    'h',
    'i',
    'j',
    'k',
    'l',
    'm',
    'n',
    'o',
    'p',
    'q',
    'r',
    's',
    't',
    'u',
    'v',
    'w',
    'x',
    'y',
    'z',
    'A',
    'B',
    'C',
    'D',
    'E',
    'F',
    'G',
    'H',
    'I',
    'J',
    'K',
    'L',
    'M',
    'N',
    'O',
    'P',
    'Q',
    'R',
    'S',
    'T',
    'U',
    'V',
    'W',
    'X',
    'Y',
    'Z'
    );

  Collection<Character> identifierCont = Set.of(
    '0',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9'
    );
  
  Collection<Character> otherChars = Set.of(
    ';',
    '.'
    );
  
  Collection<Character> knownChars = new HashSet<>();
  
  Collection<String> keywords = Set.of(
    KeyWords.PACKAGE,
    KeyWords.IMPORT
  );

  RandomAccessFile br;
  String line; 
  int lineIndex;

  public Tokenizer(File file) throws IOException {
    br = new RandomAccessFile(file, "r"); 
    line = br.readLine();
    lineIndex = 0;
    
    knownChars.addAll(identifierStart);
    knownChars.addAll(identifierCont);
    knownChars.addAll(otherChars);
  }
  
  public void close() throws IOException {
    br.close();
  }

  public Token nextToken() throws ParseException {
    //eat unknown chars
    char nextChar;
    do {
      nextChar = readChar();
      advance();
    } while (!knownChars.contains(nextChar));

    //general cases
    if (nextChar == '.') {
      return new Token()
        .setType(TokenType.PackageSeperator)
        .setValue("" + nextChar);
    } else if (nextChar == ';') {
      return new Token()
        .setType(TokenType.CommandSeperator)
        .setValue("" + nextChar);
    //TODO other cases here
    } 
      
    //special handling for identifiers
    String value = "";
    while (true) {
      value += nextChar;
      nextChar = readChar();
      
      if (identifierStart.contains(nextChar) 
        || identifierCont.contains(nextChar)) {
        advance();
      } else {
        break;
      }
    } 
    
    return new Token()
      .setValue(value)
      .setType(keywords.contains(value) ? 
        TokenType.KEYWORD : 
        TokenType.IDENTIFIER);
  }

  private char readChar() throws ParseException {
    while (lineIndex >= line.length()) {
      try {
        line = br.readLine();
      } catch (IOException e) {
        throw new ParseException();
      }
      lineIndex = 0;
    }
    
    char response = line.charAt(lineIndex);

    return response;
  }
  
  private void advance() {
    lineIndex++;
  }

  public TokenizerState getState() {
    try {
      TokenizerState state = new TokenizerState()
        .setReaderIndex(br.getFilePointer())
        .setLine(line)
        .setLineIndex(lineIndex);
      return state;
    } catch (IOException e) {
      throw new RuntimeException("Failed getting state", e);
    }
  }

  public void setState(TokenizerState tokenizerState) {
    try {
      br.seek(tokenizerState.getReaderIndex());
      line = tokenizerState.getLine();
      lineIndex = tokenizerState.getLineIndex();
    } catch (IOException e) {
      throw new RuntimeException("Failed getting state", e);
    }
  }
}
