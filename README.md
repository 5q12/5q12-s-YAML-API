# 5q12's YAML API

## Overview
5q12's YAML API is a simple and efficient API for reading and writing YAML files within the Minecraft Fabric modding environment. This API facilitates seamless integration of YAML configuration files, allowing mod developers to easily manage configuration data.

## Features
- **Read YAML Files**: Parse YAML files into Java objects with ease.
- **Write YAML Files**: Serialize Java objects into YAML format.
- **Integration**: Designed specifically for use with Fabric, ensuring compatibility and performance.

## Usage


### Imports 
```java
import main.java.app.ccls.yml.YamlHandler;
import main.java.app.ccls.yml.YamlHandlerFactory;
import java.util.Map;

Map<String, Object> data = YamlReader.readYaml("path/to/file.yml");
```

### Simple Key-Value Storage
```java
import main.java.app.ccls.yml.YamlHandler;
import main.java.app.ccls.yml.YamlHandlerFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleKeyValueExample {
    public static void main(String[] args) {
        // Initialize YamlHandler with the "advanced" format
        YamlHandler yamlHandler = YamlHandlerFactory.getHandler("advanced");

        // Define a file path
        String filePath = "config/simple_data.yml";

        // Create a simple key-value map
        Map<String, Object> data = new HashMap<>();
        data.put("username", "exampleUser");
        data.put("score", 1500);

        try {
            // Write the data to the YAML file
            yamlHandler.writeYaml(filePath, data);
            System.out.println("Data saved to " + filePath);

            // Read the data back from the YAML file
            Map<String, Object> loadedData = yamlHandler.readYaml(filePath);
            System.out.println("Loaded data: " + loadedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
### Nested Structures
```java
import main.java.app.ccls.yml.YamlHandler;
import main.java.app.ccls.yml.YamlHandlerFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NestedStructureExample {
    public static void main(String[] args) {
        // Initialize YamlHandler with the "advanced" format
        YamlHandler yamlHandler = YamlHandlerFactory.getHandler("advanced");

        // Define a file path
        String filePath = "config/nested_data.yml";

        // Create a nested structure map
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("age", 25);
        userDetails.put("email", "user@example.com");
        data.put("username", "exampleUser");
        data.put("details", userDetails);

        try {
            // Write the nested data to the YAML file
            yamlHandler.writeYaml(filePath, data);
            System.out.println("Data saved to " + filePath);

            // Read the nested data back from the YAML file
            Map<String, Object> loadedData = yamlHandler.readYaml(filePath);
            System.out.println("Loaded data: " + loadedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Lists in YAML
```java
import main.java.app.ccls.yml.YamlHandler;
import main.java.app.ccls.yml.YamlHandlerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListExample {
    public static void main(String[] args) {
        // Initialize YamlHandler with the "advanced" format
        YamlHandler yamlHandler = YamlHandlerFactory.getHandler("advanced");

        // Define a file path
        String filePath = "config/list_data.yml";

        // Create a list
        List<String> favoriteFoods = new ArrayList<>();
        favoriteFoods.add("Pizza");
        favoriteFoods.add("Sushi");
        favoriteFoods.add("Tacos");

        // Create a map to hold the list
        Map<String, Object> data = new HashMap<>();
        data.put("username", "exampleUser");
        data.put("favoriteFoods", favoriteFoods);

        try {
            // Write the list data to the YAML file
            yamlHandler.writeYaml(filePath, data);
            System.out.println("Data saved to " + filePath);

            // Read the list data back from the YAML file
            Map<String, Object> loadedData = yamlHandler.readYaml(filePath);
            System.out.println("Loaded data: " + loadedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Add to dependencies
```
dependencies {
    modImplementation 'app.ccls.yml:yamlapi:1.0.0'
}
```
