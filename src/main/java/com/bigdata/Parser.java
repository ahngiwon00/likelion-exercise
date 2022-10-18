package com.bigdata;

public interface Parser<T> { //다형성을 적용할려면 타입을 넣을수 있게
    T parse(String str); // T를 리턴
}
