package me.glaremasters.glarecore.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;


/**
 * Created by Glare
 * Date: 4/26/2019
 * Time: 10:20 AM
 */
public class SkullUtils {

    /**
     * Get the encoded skin url
     *
     * @param skinUrl the url of the skin
     * @return encoded
     */
    public static String getEncoded(@NotNull final String skinUrl) {
        return new String(Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", skinUrl).getBytes()));
    }

    /**
     * Create a game profile object
     *
     * @param url the url to use
     * @return game profile
     */
    public static GameProfile getGameProfile(@NotNull final String url) {
        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        return profile;
    }

    /**
     * Get the skull from a url
     *
     * @param skinUrl url to use
     * @return skull
     */
    public static ItemStack getSkull(@NotNull final String skinUrl) {
        final ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (skinUrl.isEmpty()) return head;

        final SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        final GameProfile profile = getGameProfile(skinUrl);
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

}