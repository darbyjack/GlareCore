package me.glaremasters.glarecore;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.configurationdata.ConfigurationData;
import co.aikar.commands.PaperCommandManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class GlareCore {
    private final BukkitAudiences bukkitAudiences;
    private final PaperCommandManager commandManager;
    private final SettingsManager settingsManager;

    private GlareCore(@NotNull final GlareCoreBuilder builder) {
        this.bukkitAudiences = builder.bukkitAudiences;
        this.commandManager = builder.commandManager;
        this.settingsManager = builder.settingsManager;
    }

    public BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }

    public PaperCommandManager getCommandManager() {
        return commandManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public static class GlareCoreBuilder {
        private final PaperCommandManager commandManager;
        private BukkitAudiences bukkitAudiences;
        private SettingsManager settingsManager;

        public GlareCoreBuilder(@NotNull final JavaPlugin plugin) {
            this.commandManager = new PaperCommandManager(plugin);
        }

        /**
         * Adds a language file to PaperCommandManager
         *
         * @param file   the name of the language file
         * @param plugin the plugin to adapt the language file to
         * @param locale the language of the language file
         * @return builder with a loaded language file
         */
        public GlareCoreBuilder addLangFile(@NotNull final String file, @NotNull final JavaPlugin plugin, @NotNull final Locale locale) {
            final File langFile = new File(plugin.getDataFolder(), file);
            if (!langFile.exists()) {
                plugin.saveResource(file, false);
            }
            this.commandManager.addSupportedLanguage(locale);

            try {
                this.commandManager.getLocales().loadYamlLanguageFile(langFile, locale);
            } catch (IOException | InvalidConfigurationException exception) {
                exception.printStackTrace();
            }

            this.commandManager.getLocales().setDefaultLocale(locale);
            return this;
        }

        /**
         * Tells the builder to use the Adventure json library
         *
         * @param plugin the plugin that Adventure needs to adapt to
         * @return builder with Adventure support
         */
        public GlareCoreBuilder useAdventure(@NotNull final JavaPlugin plugin) {
            this.bukkitAudiences = BukkitAudiences.create(plugin);
            return this;
        }

        /**
         * Tells the builder to use the ConfigMe configuration library
         *
         * @param file              the file for the config
         * @param configurationData the config data
         * @return loaded config file
         */
        public GlareCoreBuilder useConfigMe(@NotNull final File file, @NotNull final ConfigurationData configurationData) {
            this.settingsManager = SettingsManagerBuilder.withYamlFile(file).useDefaultMigrationService().configurationData(configurationData).create();
            return this;
        }

        /**
         * Builds the core
         *
         * @return built core
         */
        public GlareCore build() {
            return new GlareCore(this);
        }
    }
}
