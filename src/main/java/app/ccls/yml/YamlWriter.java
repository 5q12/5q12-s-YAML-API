package main.java.app.ccls.yml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class YamlWriter {
    public static void writeYaml(String filePath, Map<String, Object> data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                bw.write(entry.getKey() + ": " + entry.getValue());
                bw.newLine();
            }
        }
    }
}
