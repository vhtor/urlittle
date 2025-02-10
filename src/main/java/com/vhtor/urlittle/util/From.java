package com.vhtor.urlittle.util;

/**
 * Interface implemented at a class (usually records)
 * that converts itself into another class.
 *
 * @param <T> the class to convert to
 */
public interface From<T> {
  T from();
}
