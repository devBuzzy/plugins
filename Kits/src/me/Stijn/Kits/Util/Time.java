package me.Stijn.Kits.Util;

import java.util.ArrayList;
import java.util.List;

public class Time
{
  private long milliseconds;

  public Time(String time)
    throws IllegalArgumentException
  {
    String[] numbers = trim(time.split("[^0-9]"));
    String[] types = trim(time.split("[0-9]"));

    this.milliseconds = 0L;
    try
    {
      for (int i = 0; i < types.length; i++)
        this.milliseconds += getTime(Integer.parseInt(numbers[i]), types[i]);
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex);
    }
  }

  public long getMilliseconds() {
    return this.milliseconds;
  }

  public double getSeconds() {
    return getMilliseconds() / 1000.0D;
  }

  public double getMinutes() {
    return getSeconds() / 60.0D;
  }

  public double getHours() {
    return getMinutes() / 60.0D;
  }

  public double getDays() {
    return getHours() / 24.0D;
  }

  public double getMonths() {
    return getDays() / 30.0D;
  }

  public double getYears() {
    return getMonths() / 12.0D;
  }

  private int getTime(int number, String type)
  {
    int time = number;
    switch (type) {
    case "y":
      time = getTime(time * 12, "mo");
      break;
    case "mo":
      time = getTime(time * 30, "d");
      break;
    case "d":
      time = getTime(time * 24, "h");
      break;
    case "h":
      time = getTime(time * 60, "m");
      break;
    case "m":
      time = getTime(time * 60, "s");
      break;
    case "s":
      time = getTime(time * 1000, "ms");
    case "ms":
    }

    return time;
  }

  private String[] trim(String[] args)
  {
    List list = new ArrayList();
    for (String arg : args) {
      if ((arg != null) && (arg.length() > 0)) {
        list.add(arg);
      }
    }
    return (String[])list.toArray(new String[list.size()]);
  }
}