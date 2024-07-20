package app.ccls.yml;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class YamlApiMain implements ModInitializer {
    @Override
    public void onInitialize() {
        String minecraftVersion = FabricLoader.getInstance().getModContainer("minecraft").get().getMetadata().getVersion().getFriendlyString();
        System.out.println("YAML API initialized for Minecraft " + minecraftVersion);
    }
}
