package main.java.app.ccls.yml;

public class YamlHandlerFactory {

    public static YamlHandler getHandler(String format) {
        switch (format) {
            case "basic":
                return new BasicYamlHandler();
            case "nested":
                return new NestedYamlHandler();
            default:
                throw new IllegalArgumentException("Unknown YAML format: " + format);
        }
    }
}
