package me.glaremasters.glarecore.utils;

import co.aikar.commands.CommandIssuer;
import co.aikar.locales.MessageKeyProvider;
import org.jetbrains.annotations.NotNull;

public class MessageUtils {

    /**
     * Helper method to convert a message key from lang files into a String
     *
     * @param issuer the issuer that requested the message
     * @param key    the key to translate
     * @return key translated to string
     */
    public static String asString(@NotNull final CommandIssuer issuer, @NotNull final MessageKeyProvider key) {
        return issuer.getManager().getLocales().getMessage(issuer, key);
    }
}
