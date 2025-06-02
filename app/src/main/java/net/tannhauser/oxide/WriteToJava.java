package net.tannhauser.oxide;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToJava {

    public void writeToJava(String[] lines, FileWriter writer) throws IOException {
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            System.out.println(line);
            writer.write(line);
        }
    }
}
