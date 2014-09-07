package me.Stijn.Kits.api;

public class KitException extends Exception
{
  private static final long serialVersionUID = 4634639924183356239L;
  private final Throwable cause;

  public KitException(Throwable throwable)
  {
    this.cause = throwable;
  }

  public KitException() {
    this.cause = null;
  }

  public KitException(Throwable cause, String message) {
    super(message);
    this.cause = cause;
  }

  public KitException(String message) {
    super(message);
    this.cause = null;
  }

  public Throwable getCause()
  {
    return this.cause;
  }
}