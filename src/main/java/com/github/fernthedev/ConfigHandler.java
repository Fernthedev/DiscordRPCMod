package com.github.fernthedev;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

@SuppressWarnings("WeakerAccess")
public class ConfigHandler {

    public static Configuration config;

    // Settings
    //public static boolean enableWorldGeneration;
    public static String message;
    public static boolean autoignore;
    public static boolean showpresence;

    private RPC rpc;

    public enum Settings {
        message,
        autoignore,
        showpresence
    }

    public ConfigHandler(RPC rpc) {
        this.rpc = rpc;
    }


    public static void init(File file) {
        config = new Configuration(file);
        try {
            config.load();
        } catch (Exception e) {
            System.out.println("No config");
        } finally {
            config.save();
        }
        syncConfig();
    }

    public static void syncConfig() {
        String category;
        config.load();
        //EXAMPLE
        /*category = "World";
        config.addCustomCategoryComment(category, "World settings");
        enableWorldGeneration = config.getBoolean("enableWorldGeneration", category, true, "Allows Gaianite Ore to generate in the world.");*/

        category = "discord";
        config.addCustomCategoryComment(category, "Rich Presence Mode Settings");
        message = config.getString("message", category, "none", "Changes message shown while playing");
        autoignore = config.getBoolean("autoignore", category, false, "Auto ignores join requests");
        //showpresence = config.getBoolean("showpresence", category, true, "Shows presence in discord");

        //showpresence = config.get(category,"showpresence",true).getBoolean();


        config.save();
    }

    @SuppressWarnings("unused")
    public static void updateConfig(Boolean value, Settings setting) {
        String category = "discord";
        config.load();
        if (setting == Settings.showpresence) {
            config.getCategory(category).get("showpresence").set(value);
        }

        if (setting == Settings.autoignore) {
            config.getCategory(category).get("autoignore").set(value);
        }
        config.save();
    }

    @SuppressWarnings("unused")
    public static void updateConfig(String value, Settings setting) {
        String category = "discord";
        config.load();
        if (setting == Settings.message) {
            config.getCategory(category).get("message").set(value);
        }
        config.save();
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent e) {
        if (e.modID.equalsIgnoreCase(DiscordMod.MODID)) {
            config.save();
            rpc.updateStatus();
        }
    }
}

