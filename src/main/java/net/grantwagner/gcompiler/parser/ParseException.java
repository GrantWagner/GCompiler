package net.grantwagner.gcompiler.parser;

public class ParseException extends Exception {
  private static final long serialVersionUID = -5865912169428647250L;

  public ParseException() {
    super();
  }

  public ParseException(
    String message,
    Throwable cause,
    boolean enableSuppression,
    boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ParseException(String message, Throwable cause) {
    super(message, cause);
  }

  public ParseException(String message) {
    super(message);
  }

  public ParseException(Throwable cause) {
    super(cause);
  }

}
