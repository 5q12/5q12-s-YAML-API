package main.java.app.ccls.yml;

import java.io.IOException;
import java.util.Map;

public interface YamlHandler {
    Map<String, Object> readYaml(String path) throws IOException;
    void writeYaml(String path, Map<String, Object> data) throws IOException;
}
