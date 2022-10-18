package com.bigdata;

public class HospitalParser implements Parser<Hospital>{
    @Override

    public Hospital parse(String str){
        str=str.replace("\"","");
        String[] splitted = str.split(",");

        return new Hospital(splitted[0],splitted[1],splitted[2],splitted[6],splitted[10]);
    }
}
