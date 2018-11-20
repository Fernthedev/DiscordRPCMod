package com.github.fernthedev;

import net.arikia.dev.drpc.DiscordRPC;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

import static com.github.fernthedev.DiscordMod.MODID;

@Config(modid = MODID, category = "discord",type = Config.Type.INSTANCE)
@SuppressWarnings("WeakerAccess")
public class ConfigHandler {

    protected RPC rpc;

    @Config.Comment("Changes message shown while playing")
    public static String message = "Playing some minecraft, nothing to see here";
    @Config.Comment("Auto ignores join requests")
    public static boolean autoignore = false;
    @Config.Comment("Shows presence in discord")
    public static boolean showpresence = true;
    

    public ConfigHandler(RPC rpc) {
        this.rpc = rpc;
        new EventManager(rpc);
    }


    public static void init(File file) {

        /*
        try {
            config = new Configuration(file);
            try {
                config.load();
            } catch (Exception e) {
                System.out.println("No config");
            } finally {
                config.save();
            }*/

        //syncConfig();
    }

    public static void syncConfig() {
        ConfigManager.sync(MODID, Config.Type.INSTANCE);
        //config.save();
        //String category;
        //config.load();
        //EXAMPLE
        /*category = "World";
        config.addCustomCategoryComment(category, "World settings");
        enableWorldGeneration = config.getBoolean("enableWorldGeneration", category, true, "Allows Gaianite Ore to generate in the world.");*/

        //category = "discord";
        //config.addCustomCategoryComment(category, "Rich Presence Mode Settings");
        //message = config.getString("message", category, "none", "Changes message shown while playing");
        //autoignore = config.getBoolean("autoignore", category, false, "Auto ignores join requests");
        //showpresence = config.getBoolean("showpresence", category, true, "Shows presence in discord");

        //showpresence = config.get(category,"showpresence",true).getBoolean();



    }


    /*
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


    public static void updateConfig(String value, Settings setting) {
        String category = "discord";
        config.load();
        if (setting == Settings.message) {
            config.getCategory(category).get("message").set(value);
        }
        config.save();
    }*/

    @Mod.EventBusSubscriber(modid = MODID)
    private static class EventManager {

        private static RPC rpc;

        public EventManager(RPC rpc) {
            try {
                EventManager.rpc = rpc;
            }catch (Exception e){
                DiscordMod.print(this,"an exception");
                e.printStackTrace();
            }
        }

        @SubscribeEvent
        public static void onConfigChange(final ConfigChangedEvent.OnConfigChangedEvent e) {
            try {
                if (e.getModID().equals(MODID)) {
                    DiscordMod.print(EventManager.class, "ShowPresence: " + showpresence + " ignore: " + autoignore + " message: " + message);
                    ConfigManager.sync(MODID, Config.Type.INSTANCE);


                    //syncConfig();
                    DiscordMod.print(EventManager.class, "After ShowPresence: " + showpresence + " ignore: " + autoignore + " message: " + message);
                    //syncConfig();


                    if (!ConfigHandler.showpresence) {
                        //DiscordMod.getLogger().info("CLEAR STATUS");
                        DiscordRPC.discordClearPresence();
                    }
                    if (ConfigHandler.showpresence) {
                        rpc.updateStatus();
                    }
                }
            } catch (Exception ee) {
                DiscordMod.print(EventManager.class,"an exception");
                ee.printStackTrace();
            }
        }
    }
}

