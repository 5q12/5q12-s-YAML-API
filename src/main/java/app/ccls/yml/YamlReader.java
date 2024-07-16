package main.java.app.ccls.yml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YamlReader {
    public static Map<String, Object> readYaml(String filePath) throws IOException {
        Map<String, Object> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    data.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return data;
    }
}
