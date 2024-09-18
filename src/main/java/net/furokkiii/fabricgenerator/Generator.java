package net.furokkiii.fabricgenerator;

import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class Generator extends JavaPlugin {
    public static final Logger LOGGER = LogManager.getLogger("Fabric Generator");
    public static Set<Plugin> PLUGIN_INSTANCE = new HashSet<>();

    public Generator(Plugin plugin) {
        super(plugin);

        LOGGER.info("Fabric generator loading...");

        addListener(PreGeneratorsLoadingEvent.class, e -> ModElementTypes.load());

        PLUGIN_INSTANCE.add(plugin);
    }
}
