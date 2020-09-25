package me.glaremasters.glarecore.utils;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * Created by Glare
 * Date: 9/24/2020
 * Time: 9:08 PM
 */
public final class LogUtils {

    /**
     * Log any message to console with any level.
     *
     * @param level   the log level to log on.
     * @param content the message to log.
     */
    public static void log(@NotNull final Level level, @NotNull final String content) {
        JavaPlugin.getProvidingPlugin(LogUtils.class).getLogger().log(level, content);
    }

    /**
     * Log a message to console on INFO level.
     *
     * @param msg the msg you want to log.
     */
    public static void info(String msg) {
        log(Level.INFO, msg);
    }

    /**
     * Log a message to console on WARNING level.
     *
     * @param msg the msg you want to log.
     */
    public static void warn(String msg) {
        log(Level.WARNING, msg);
    }

    /**
     * Log a message to console on SEVERE level.
     *
     * @param msg the msg you want to log.
     */
    public static void severe(String msg) {
        log(Level.SEVERE, msg);
    }
}
