package net.grantwagner.gcompiler.tokenizer.model;

import lombok.Data;

@Data
public class Token {
  TokenType type;
  String value;
}
