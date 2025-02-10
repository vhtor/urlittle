package com.vhtor.urlittle.util;

import java.math.BigDecimal;
import java.util.Collection;

public class ValidatorUtils {
  public static boolean isNotEmpty(Object o) {
    return !isEmpty(o);
  }

  public static boolean isEmpty(String s) {
    return s == null || s.trim().isEmpty();
  }

  public static boolean isEmpty(Object o) {
    switch (o) {
      case null -> {
        return true;
      }
      case String s -> {
        return isEmpty(s);
      }
      case final Number i -> {
        if (!(i instanceof Double) && !(i instanceof BigDecimal)) {
          return i.intValue() == 0;
        } else {
          return i.doubleValue() == 0.0;
        }
      }
      case Object[] objects -> {
        return objects.length == 0;
      }
      case int[] ints -> {
        return ints.length == 0;
      }
      case Collection<?> collection -> {
        return collection.isEmpty();
      }
      default -> {
        return false;
      }
    }
  }
}
