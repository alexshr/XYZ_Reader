package com.alexshr.xyz.util;

//simple functional interface without exception
public interface SimpleConsumer<T> {
    void accept(T t);
}
