package com.bigdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        LineReader<Hospital> hospitalLineReader = new LineReader<>(new HospitalParser()); //타입이 Hospital인 LineReader객체 생성
        String filename="C:\\Users\\SoMin\\Downloads\\서울시 병의원 위치 정보.csv"; //파일 위치
        List<Hospital> hospitals =hospitalLineReader.readLine(filename); //readLine(filename) 반환값 Hospital 리스트를 가리킴

        List<String> hospitalList = new ArrayList<>(); //sql형식으로 만들 String리스트 만듬
        for(Hospital hospital : hospitals){
            hospitalList.add(hospital.toSqlString()); //toSqlString메소드를 실행해 리스트에 sql형식으로 넣어줌
        }

        createFile createFile = new createFile(); //파일 생성
        createFile.createAFile("sqlFile");

        fileWrite fileWrite = new fileWrite();//파일에 sql형식으로 된 String 리스트를 하나씩 넣음
        fileWrite.write(hospitalList,"sqlFile");

    }
}
