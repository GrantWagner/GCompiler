package net.grantwagner.gcompiler.tokenizer;

import lombok.Data;

@Data
public class TokenizerState {
  long readerIndex;
  String line;
  int lineIndex;
}
