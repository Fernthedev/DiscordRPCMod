package com.github.fernthedev;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

/**
 * DISCORD RPC LIBRARIES PROVIDED BY VATUU (NOT DIRECTLY)
 * Github repository found at <a href="https://github.com/Vatuu/discord-rpc">https://github.com/Vatuu/discord-rpc</a>
 */
@SuppressWarnings("WeakerAccess")
@Mod(modid = DiscordMod.MODID, name = DiscordMod.NAME, version = DiscordMod.VERSION,canBeDeactivated=true,clientSideOnly = true,acceptedMinecraftVersions = "1.12,",guiFactory = "com.github.fernthedev.GUIFactory")
public class DiscordMod {
    public static final String MODID = "discord";
    public static final String NAME = "Discord";
    public static final String VERSION = "1.3";

    public static String client;
    public static EntityPlayer player;
    public static File configfile;
    //private static IPCClient client;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
                configfile = event.getSuggestedConfigurationFile();
                ConfigHandler.init(configfile);
                //FMLCommonHandler.instance().bus().register(new ConfigHandler());
                MinecraftForge.EVENT_BUS.register(new ConfigHandler());
    }



    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unused")
    @EventHandler
    public void init(FMLInitializationEvent event) {
        DiscordEventHandlers handler = new DiscordEventHandlers();
        RPC rpc = new RPC();
        new RPCEvents();

        handler.ready = new ReadyEvent();
        handler.joinGame = new JoinEvent();
        handler.joinRequest = new RequestJoinEvent();
        client = "448100612987551744L";
        DiscordRPC.discordInitialize(client, handler,true);

        IThreadListener mainThread = Minecraft.getMinecraft();
        mainThread.addScheduledTask(DiscordRPC::discordRunCallbacks);
        player = Minecraft.getMinecraft().player;
    }

    @SideOnly(Side.CLIENT)
    @EventHandler
    public void loaded(FMLPostInitializationEvent e) {
        RPC.menu();
        MinecraftForge.EVENT_BUS.register(new RPCEvents());
        MinecraftForge.EVENT_BUS.register(new OptionMenu(false,null));
    }


    @SideOnly(Side.CLIENT)
    @EventHandler
    public void shutdown(FMLModDisabledEvent e) {
        DiscordRPC.discordShutdown();
    }

    public static void sendPlayerMessage(EntityPlayer player, String message) {
     //player.sendMessage(new TextComponentString(message));
     player.sendMessage(new TextComponentString(message));
    }

    @SuppressWarnings("unused")
    public static void sendPlayerMessage(EntityPlayer player,ITextComponent iTextComponents) {
        player.sendMessage(iTextComponents);
    }
}


