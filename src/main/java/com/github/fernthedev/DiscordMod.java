package com.github.fernthedev;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLModDisabledEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

/**
 * DISCORD RPC LIBRARIES PROVIDED BY VATUU (NOT DIRECTLY)
 * Github repository found at <a href="https://github.com/Vatuu/discord-rpc">https://github.com/Vatuu/discord-rpc</a>
 */
@SuppressWarnings("WeakerAccess")
@Mod(modid = DiscordMod.MODID, name = DiscordMod.NAME, version = DiscordMod.VERSION,canBeDeactivated=true,acceptedMinecraftVersions = "1.7.10",guiFactory = "com.github.fernthedev.GUIFactory")
public class DiscordMod {
    public static final String MODID = "discordmod";
    public static final String NAME = "Discord";
    public static final String VERSION = "1.3.2";

    private RPC rpc;
    public static String client;
    public static EntityPlayer player;
    public static File configfile;


    //private static IPCClient client;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //Configuration config = new Configuration(new File("config/DiscordRPC.cfg"));
        //File configDir = new File(event.getModConfigurationDirectory() + "/" + event.getSuggestedConfigurationFile());
        //configfile = new File("config/" + DiscordMod.MODID + ".cfg");
        //logger = event.getModLog();
        configfile = event.getSuggestedConfigurationFile();
        rpc = new RPC();
        ConfigHandler.init(configfile);
        FMLCommonHandler.instance().bus().register(new ConfigHandler(rpc));
    }



    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
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
    @Mod.EventHandler
    public void loaded(FMLPostInitializationEvent e) {
        rpc.menu();
        MinecraftForge.EVENT_BUS.register(new RPCEvents(rpc));
        FMLCommonHandler.instance().bus().register(new RPCEvents(rpc));
        MinecraftForge.EVENT_BUS.register(new OptionMenu(false,null));
    }


    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
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
}


