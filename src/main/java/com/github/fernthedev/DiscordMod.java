package com.github.fernthedev;

import com.github.fernthedev.fernapi.server.forge.FernForgeAPI;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
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
@Mod(modid = DiscordMod.MODID, name = DiscordMod.NAME, version = DiscordMod.VERSION, clientSideOnly = true,acceptedMinecraftVersions = "1.12,")
public class DiscordMod extends FernForgeAPI {
    public static final String MODID = "discord";
    public static final String NAME = "Discord";
    public static final String VERSION = "1.3.2";

    public static String client;
    public static EntityPlayer player;
    public static File configfile;

    private RPC rpc;
    //private static IPCClient client;

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        try {
            //logger = event.getModLog();
            configfile = event.getSuggestedConfigurationFile();
            rpc = new RPC();
            MinecraftForge.EVENT_BUS.register(new ConfigHandler(rpc));

            ConfigHandler.init(configfile);
            //FMLCommonHandler.instance().bus().register(new ConfigHandler());

        } catch (Exception e) {
            DiscordMod.print(this,"an exception");
            e.printStackTrace();
        }
    }



    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("unused")
    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            DiscordEventHandlers handler = new DiscordEventHandlers();

            new RPCEvents(rpc);
            ConfigManager.sync(MODID, Config.Type.INSTANCE);

            handler.ready = new ReadyEvent();
            handler.joinGame = new JoinEvent();
            handler.joinRequest = new RequestJoinEvent();
            client = "448100612987551744L";
            DiscordRPC.discordInitialize(client, handler, true);

            //IThreadListener mainThread = Minecraft.getMinecraft();
            //mainThread.addScheduledTask(DiscordRPC::discordRunCallbacks);
            player = Minecraft.getMinecraft().player;
            //do your stuff
            Runtime.getRuntime().addShutdownHook(new Thread(DiscordRPC::discordShutdown));
        } catch (Exception e) {
            DiscordMod.print(this,"an exception");
            e.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
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


    public static void sendPlayerMessage(EntityPlayer player, String message) {
     //player.sendMessage(new TextComponentString(message));
     player.sendMessage(new TextComponentString(message));
    }

    public static void print(Object someclass, Object text) {
        System.out.println("[DiscordMod] ["+someclass +"] " + text);
    }

    @SuppressWarnings("unused")
    public static void sendPlayerMessage(EntityPlayer player,ITextComponent iTextComponents) {
        player.sendMessage(iTextComponents);
    }
}


