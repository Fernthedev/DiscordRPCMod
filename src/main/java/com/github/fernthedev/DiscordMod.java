package com.github.fernthedev;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * DISCORD RPC LIBRARIES PROVIDED BY VATUU (NOT DIRECTLY)
 * Github repository found at <a href="https://github.com/Vatuu/discord-rpc">https://github.com/Vatuu/discord-rpc</a>
 */
@SuppressWarnings("WeakerAccess")
@Mod(modid = DiscordMod.MODID, name = DiscordMod.NAME, version = DiscordMod.VERSION,canBeDeactivated=true,clientSideOnly = true,acceptedMinecraftVersions = "[1.8,)",guiFactory = "com.github.fernthedev.GUIFactory")
public class DiscordMod {
    public static final String MODID = "discordmod";
    public static final String NAME = "Discord";
    public static final String VERSION = "1.3";

    private RPC rpc;
    public static String client;
    public static EntityPlayer player;
    public static File configfile;
    private static Logger logger;

    @SuppressWarnings("unused")
    public static double ver() {
        return Double.parseDouble(Minecraft.getMinecraft().getVersion());
    }
    //private static IPCClient client;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //Configuration config = new Configuration(new File("config/DiscordRPC.cfg"));
        //File configDir = new File(event.getModConfigurationDirectory() + "/" + event.getSuggestedConfigurationFile());
        //configfile = new File("config/" + DiscordMod.MODID + ".cfg");
        logger = event.getModLog();
        configfile = event.getSuggestedConfigurationFile();
        rpc = new RPC();
        ConfigHandler.init(configfile);
        FMLCommonHandler.instance().bus().register(new ConfigHandler(rpc));
    }



    @SideOnly(Side.CLIENT)
    @EventHandler
    public void init(FMLInitializationEvent event) {
        DiscordEventHandlers handler = new DiscordEventHandlers();

        new RPCEvents(rpc);

        handler.ready = new ReadyEvent();
        handler.joinRequest = new RequestJoinEvent();
        handler.joinGame = new JoinEvent();
        client = "448100612987551744L";
        DiscordRPC.discordInitialize(client, handler,true);


        //IThreadListener mainThread = Minecraft.getMinecraft();
        //mainThread.addScheduledTask(DiscordRPC::discordRunCallbacks);
        player = Minecraft.getMinecraft().thePlayer;
    }

    @SideOnly(Side.CLIENT)
    @EventHandler
    public void loaded(FMLPostInitializationEvent e) {
        rpc.menu();
        MinecraftForge.EVENT_BUS.register(new RPCEvents(rpc));
        MinecraftForge.EVENT_BUS.register(new OptionMenu(false,null));
    }


    @SideOnly(Side.CLIENT)
    @EventHandler
    public void shutdown(FMLModDisabledEvent e) {
        DiscordRPC.discordShutdown();
    }


    public static void sendPlayerMessage(EntityPlayer player,String message) {
        player.addChatMessage(new ChatComponentText(message));
    }

    @SuppressWarnings("unused")
    public static void sendPlayerMessage(EntityPlayer player, IChatComponent component) {
        player.addChatMessage(component);
    }

    public static Logger getLogger() {
        return logger;
    }
}


