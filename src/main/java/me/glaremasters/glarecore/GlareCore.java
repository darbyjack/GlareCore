package me.glaremasters.glarecore;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.configurationdata.ConfigurationData;
import co.aikar.commands.PaperCommandManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class GlareCore {
    private final BukkitAudiences bukkitAudiences;
    private final MiniMessage miniMessage;
    private final PaperCommandManager commandManager;
    private final SettingsManager settingsManager;

    public BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }

    public MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public PaperCommandManager getCommandManager() {
        return commandManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    private GlareCore(@NotNull final GlareCoreBuilder builder) {
        this.bukkitAudiences = builder.bukkitAudiences;
        this.miniMessage = builder.miniMessage;
        this.commandManager = builder.commandManager;
        this.settingsManager = builder.settingsManager;
    }

    public static class GlareCoreBuilder {
        private BukkitAudiences bukkitAudiences;
        private MiniMessage miniMessage;
        private final PaperCommandManager commandManager;
        private SettingsManager settingsManager;

        public GlareCoreBuilder(@NotNull final PaperCommandManager commandManager) {
            this.commandManager = commandManager;
        }

        public GlareCoreBuilder useAdventure(@NotNull final JavaPlugin plugin) {
            this.bukkitAudiences = BukkitAudiences.create(plugin);
            return this;
        }

        public GlareCoreBuilder useMiniMessage() {
            this.miniMessage = MiniMessage.get();
            return this;
        }

        public GlareCoreBuilder useConfigMe(@NotNull final File file, @NotNull final ConfigurationData configurationData) {
            this.settingsManager = SettingsManagerBuilder.withYamlFile(file).useDefaultMigrationService().configurationData(configurationData).create();
            return this;
        }

        public GlareCore build() {
            return new GlareCore(this);
        }
    }
}
