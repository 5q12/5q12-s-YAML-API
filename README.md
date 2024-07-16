# 5q12's YAML API

## Overview
5q12's YAML API is a simple and efficient API for reading and writing YAML files within the Minecraft Fabric modding environment. This API facilitates seamless integration of YAML configuration files, allowing mod developers to easily manage configuration data.

## Features
- **Read YAML Files**: Parse YAML files into Java objects with ease.
- **Write YAML Files**: Serialize Java objects into YAML format.
- **Integration**: Designed specifically for use with Fabric, ensuring compatibility and performance.

## Usage


### Reading YAML Files
```java
import main.java.app.ccls.yml.YamlReader;
import java.util.Map;

Map<String, Object> data = YamlReader.readYaml("path/to/file.yml");
```

### Writing YAML Files
```java
import main.java.app.ccls.yml.YamlWriter;
import java.util.HashMap;
import java.util.Map;

Map<String, Object> data = new HashMap<>();
data.put("key", "value");
YamlWriter.writeYaml("path/to/file.yml", data);
```

### Add to dependencies
```
dependencies {
    modImplementation 'app.ccls.yml:yamlapi:1.0.0'
}
```
