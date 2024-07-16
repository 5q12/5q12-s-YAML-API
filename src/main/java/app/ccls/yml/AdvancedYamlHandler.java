package main.java.app.ccls.yml;

import java.io.*;
import java.util.*;

public class AdvancedYamlHandler implements YamlHandler {

    private static final String INDENT = "  ";

    @Override
    public Map<String, Object> readYaml(String path) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            Deque<Map<String, Object>> stack = new ArrayDeque<>();
            stack.push(data);
            int currentIndentLevel = 0;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;  // skip empty lines
                }
                int indentLevel = countLeadingSpaces(line) / INDENT.length();
                String trimmedLine = line.trim();
                if (trimmedLine.contains(":")) {
                    String[] parts = trimmedLine.split(":", 2);
                    String key = parts[0].trim();
                    Object value = parts.length > 1 ? parseValue(parts[1].trim()) : new LinkedHashMap<>();
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
        value = value.substring(1, value.length() - 1); // Remove the curly braces
        String[] entries = value.split(", ");
        for (String entry : entries) {
            String[] parts = entry.split("=");
            map.put(parts[0].trim(), parseValue(parts[1].trim()));
        }
        return map;
    }

    private List<Object> parseInlineList(String value) {
        List<Object> list = new ArrayList<>();
        value = value.substring(1, value.length() - 1); // Remove the square brackets
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
}
