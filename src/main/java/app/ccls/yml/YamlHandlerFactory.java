package app.ccls.yml;

import net.fabricmc.loader.api.FabricLoader;

public class YamlHandlerFactory {

    public static YamlHandler getHandler(String format) {
        String minecraftVersion = FabricLoader.getInstance().getModContainer("minecraft").get().getMetadata().getVersion().getFriendlyString();

        switch (format) {
            case "basic":
                return getBasicHandler(minecraftVersion);
            case "nested":
                return getNestedHandler(minecraftVersion);
            default:
                throw new IllegalArgumentException("Unknown YAML format: " + format);
        }
    }

    private static YamlHandler getBasicHandler(String version) {
        if (version.startsWith("1.20.6")) {
            return new app.ccls.yml.v1_20_6.BasicYamlHandler();
        } else if (version.startsWith("1.21")) {
            return new app.ccls.yml.v1_21.BasicYamlHandler();
        } else if (version.startsWith("1.20.1")) {
            return new app.ccls.yml.v1_20_1.BasicYamlHandler();
        } else if (version.startsWith("1.19.4")) {
            return new app.ccls.yml.v1_19_4.BasicYamlHandler();
        } else {
            throw new IllegalArgumentException("Unsupported Minecraft version: " + version);
        }
    }

    private static YamlHandler getNestedHandler(String version) {
        if (version.startsWith("1.20.6")) {
            return new app.ccls.yml.v1_20_6.NestedYamlHandler();
        } else if (version.startsWith("1.21")) {
            return new app.ccls.yml.v1_21.NestedYamlHandler();
        } else if (version.startsWith("1.20.1")) {
            return new app.ccls.yml.v1_20_1.NestedYamlHandler();
        } else if (version.startsWith("1.19.4")) {
            return new app.ccls.yml.v1_19_4.NestedYamlHandler();
        } else {
            throw new IllegalArgumentException("Unsupported Minecraft version: " + version);
        }
    }
}
