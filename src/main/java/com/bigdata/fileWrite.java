package com.bigdata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class fileWrite {
    public void write(List<String> strs, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for(String str : strs){
            writer.write(str);
        }
        writer.close();
    }
}
