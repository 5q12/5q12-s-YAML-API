package app.ccls.yml.v1_20_6;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import app.ccls.yml.YamlHandler;

public class NestedYamlHandler implements YamlHandler {

    private static final String INDENT = "  ";
    private static final String LOG_FILE_PATH = "config/yaml/logs/nested.log";

    static {
        try {
            Files.createDirectories(Paths.get("config/yaml/logs"));
            new PrintWriter(LOG_FILE_PATH).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> readYaml(String path) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            Deque<Map<String, Object>> stack = new ArrayDeque<>();
            stack.push(data);
            int currentIndentLevel = 0;
            while ((line = reader.readLine()) != null) {
                line = removeComment(line);
                if (line.trim().isEmpty()) {
                    continue;
                }
                int indentLevel = countLeadingSpaces(line) / INDENT.length();
                String trimmedLine = line.trim();
                log("Reading line: " + line);
                if (trimmedLine.contains(":")) {
                    String[] parts = trimmedLine.split(":", 2);
                    String key = parts[0].trim();
                    Object value = parts.length > 1 && !parts[1].trim().isEmpty() ? parseValue(parts[1].trim()) : new LinkedHashMap<>();
                    log("Parsed key: " + key + ", value: " + value);
                    while (indentLevel < currentIndentLevel) {
                        stack.pop();
                        currentIndentLevel--;
                    }
                    Map<String, Object> currentMap = stack.peek();
                    currentMap.put(key, value);
                    if (value instanceof Map) {
                        stack.push((Map<String, Object>) value);
                        currentIndentLevel = indentLevel + 1;
                    }
                }
            }
        }
        log("Final parsed data: " + data);
        return data;
    }

    @Override
    public void writeYaml(String path, Map<String, Object> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writeMap(writer, data, 0);
        }
    }

    private void writeMap(BufferedWriter writer, Map<String, Object> map, int indentLevel) throws IOException {
        String indent = getIndent(indentLevel);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            writer.write(indent + entry.getKey() + ": ");
            if (entry.getValue() instanceof Map) {
                writer.write("\n");
                writeMap(writer, (Map<String, Object>) entry.getValue(), indentLevel + 1);
            } else if (entry.getValue() instanceof List) {
                writer.write("\n");
                writeList(writer, (List<Object>) entry.getValue(), indentLevel + 1);
            } else {
                writer.write(formatValue(entry.getValue()) + "\n");
            }
        }
    }

    private void writeList(BufferedWriter writer, List<Object> list, int indentLevel) throws IOException {
        String indent = getIndent(indentLevel);
        for (Object item : list) {
            writer.write(indent + "- ");
            if (item instanceof Map) {
                writer.write("\n");
                writeMap(writer, (Map<String, Object>) item, indentLevel + 1);
            } else if (item instanceof List) {
                writer.write("\n");
                writeList(writer, (List<Object>) item, indentLevel + 1);
            } else {
                writer.write(formatValue(item) + "\n");
            }
        }
    }

    private String formatValue(Object value) {
        if (value instanceof String) {
            String str = (String) value;
            if (str.contains(": ") || str.contains("\n") || str.contains("\"") || str.contains("'") || str.contains("{") || str.contains("}") || str.contains("[") || str.contains("]")) {
                return "'" + str.replace("'", "''") + "'";
            }
            return str;
        }
        return value.toString();
    }

    private Object parseValue(String value) {
        if (value.startsWith("{") && value.endsWith("}")) {
            return parseInlineMap(value);
        } else if (value.startsWith("[") && value.endsWith("]")) {
            return parseInlineList(value);
        } else if (value.matches("^-?\\d+$")) {
            return Long.parseLong(value);
        } else if (value.matches("^-?\\d+\\.\\d+$")) {
            return Double.parseDouble(value);
        } else if (value.startsWith("'") && value.endsWith("'")) {
            return value.substring(1, value.length() - 1).replace("''", "'");
        } else {
            return value;
        }
    }

    private Map<String, Object> parseInlineMap(String value) {
        Map<String, Object> map = new LinkedHashMap<>();
        value = value.substring(1, value.length() - 1);
        String[] entries = value.split(", ");
        for (String entry : entries) {
            String[] parts = entry.split("=");
            map.put(parts[0].trim(), parseValue(parts[1].trim()));
        }
        return map;
    }

    private List<Object> parseInlineList(String value) {
        List<Object> list = new ArrayList<>();
        value = value.substring(1, value.length() - 1);
        String[] items = value.split(", ");
        for (String item : items) {
            list.add(parseValue(item.trim()));
        }
        return list;
    }

    private int countLeadingSpaces(String str) {
        int count = 0;
        while (count < str.length() && str.charAt(count) == ' ') {
            count++;
        }
        return count;
    }

    private String getIndent(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(INDENT);
        }
        return sb.toString();
    }

    private String removeComment(String line) {
        boolean inQuote = false;
        char quoteChar = '\0';
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"' || c == '\'') {
                if (inQuote && c == quoteChar) {
                    inQuote = false;
                } else if (!inQuote) {
                    inQuote = true;
                    quoteChar = c;
                }
            }
            if (c == '#' && !inQuote) {
                break;
            }
            result.append(c);
        }
        return result.toString();
    }
}
