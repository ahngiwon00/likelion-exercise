package com.parser;

public interface Parser<T> {
    T parse(String str);
}