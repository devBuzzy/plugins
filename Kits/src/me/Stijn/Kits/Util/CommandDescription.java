package me.Stijn.Kits.Util;

public class CommandDescription
{
  private String title;
  private String command;
  private String[] args;

  public CommandDescription(String title, String command, String[] args)
  {
    setTitle(title);
    setCommand(command);
    setArgs(args);
  }

  public String[] getArgs() {
    return this.args;
  }

  public void setArgs(String[] args) {
    this.args = args;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCommand() {
    return this.command;
  }

  public void setCommand(String command) {
    this.command = command;
  }
}