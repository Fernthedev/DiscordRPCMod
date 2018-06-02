package com.github.fernthedev;

import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RPCEvents {

    private RPC rpc;
    private String client;
    private String oldaddress;
    private Gui lastgui;

    public RPCEvents(RPC rpc) {
        this.rpc = rpc;
        client = DiscordMod.client;
        lastgui = null;
        oldaddress = "none";
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void menuOpened(GuiScreenEvent e) {
        if(lastgui == null) {
            lastgui = e.gui;
        }
        if ((e.gui instanceof GuiMainMenu || e.gui instanceof GuiScreenServerList || e.gui instanceof GuiSelectWorld) && !(lastgui instanceof GuiMainMenu || lastgui instanceof GuiScreenServerList || lastgui instanceof GuiSelectWorld)) {
            lastgui = e.gui;
            oldaddress = "none";
            DiscordMod.player = Minecraft.getMinecraft().thePlayer;
            rpc.menu(client);
        }
    }

    /*@SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void joinServer(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        IThreadListener mainThread = Minecraft.getMinecraft();
        mainThread.addScheduledTask(() -> {
            ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
            Minecraft mc = Minecraft.getMinecraft();
            //Logger.info("Server Connected! Local? %s - Address: %s", e.isLocal(), serverData != null ? serverData.serverIP : "<No ServerData>");
            if (serverData != null || mc.world.isRemote) {
                EntityPlayerSP player = mc.player;
                rpc.server(client);
                if (serverData == null) {
                    RPC.address = "unknown";
                    RPC.ServerName = "unknown";
                    RPC.Players = "unknown";
                } else {
                    RPC.address = serverData.serverIP;
                    RPC.ServerName = serverData.serverName;
                    RPC.Players = serverData.populationInfo;
                }
            }
        });
    }*/



    @SubscribeEvent
    public void multiplayer(EntityJoinWorldEvent e) {
        ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
        Minecraft mc = Minecraft.getMinecraft();
        String ServerName = "unknown";
        String address = "unknown";
        if(e.entity == mc.thePlayer) {
            //DiscordMod.sendPlayerMessage(mc.thePlayer, "Player found");
            if (!(serverData == null)) {
                //DiscordMod.sendPlayerMessage(mc.thePlayer, "Server not null");
                if (!mc.isIntegratedServerRunning()) {
                    //DiscordMod.sendPlayerMessage(mc.thePlayer, "not intergrated");
                    address = serverData.serverIP;
                    ServerName = serverData.serverName;
                    if (oldaddress == null) {
                        oldaddress = "none";
                    }
                    if (!oldaddress.equals(address)) {
                        oldaddress = address;
                        rpc.server(client, address, ServerName, serverData);
                        //DiscordMod.sendPlayerMessage(mc.thePlayer, "Set the rich presence");
                    }
                }
            }
        }else{
            //DiscordMod.sendPlayerMessage(mc.thePlayer, "Not player");
        }


        //assert serverData != null;
        //player.sendMessage(new TextComponentString("Ip " + address + " is local " + serverData.isOnLAN() + " and name " + ServerName));
        //player.sendMessage(new TextComponentString("Is Local " + serverData.isOnLAN()));
        //player.sendMessage(new TextComponentString("Name " + ServerName));
        //player.sendMessage(new TextComponentString("Ip " + address));
    }


    @SubscribeEvent
    public void singlePlayer(PlayerEvent.PlayerLoggedInEvent e) {
        ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
        Minecraft mc = Minecraft.getMinecraft();

        if(serverData == null || mc.isIntegratedServerRunning()) {
            oldaddress = "none";
            rpc.single(client);
        }
    }

    private int tick;

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if(tick >= 20*5) {
            DiscordRPC.discordRunCallbacks();
            tick = 0;
        }
        tick++;
    }
}
