package app.ccls.yml.v1_19_4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.ccls.yml.YamlHandler;

public class BasicYamlHandler implements YamlHandler {

    @Override
    public Map<String, Object> readYaml(String path) throws IOException {
        Map<String, Object> data = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ", 2);
                if (parts.length == 2) {
                    data.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return data;
    }

    @Override
    public void writeYaml(String path, Map<String, Object> data) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        }
    }
}
