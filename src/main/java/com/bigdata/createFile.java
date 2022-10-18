package com.bigdata;

import java.io.File;
import java.io.IOException;

public class createFile {
    public void createAFile(String filename) throws IOException {
        File file = new File(filename);
        file.createNewFile();
    }
}
