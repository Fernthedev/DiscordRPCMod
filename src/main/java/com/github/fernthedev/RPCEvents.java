package com.github.fernthedev;

import net.arikia.dev.drpc.DiscordRPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RPCEvents {

    private RPC rpc;
    private Gui lastgui;
    private String oldadress;

    @SuppressWarnings("WeakerAccess")
    public RPCEvents(RPC rpc) {
        this.rpc = rpc;
        oldadress = "none";
        lastgui = null;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void menuOpened(GuiScreenEvent e) {
        if(lastgui == null) {
            lastgui = e.getGui();
        }
        if ((e.getGui() instanceof GuiMainMenu || e.getGui() instanceof GuiScreenServerList || e.getGui() instanceof GuiWorldSelection)) {
            if (lastgui instanceof GuiMainMenu || lastgui instanceof GuiScreenServerList || lastgui instanceof GuiWorldSelection) {
                lastgui = e.getGui();
                DiscordMod.player = Minecraft.getMinecraft().player;
                oldadress = "none";
                RPC.menu();
            }
        }
    }

    @SubscribeEvent
    public void multiplayer(EntityJoinWorldEvent e) {
        //EntityPlayer player = Minecraft.getMinecraft().player;
        ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
        Minecraft mc = Minecraft.getMinecraft();
        String address = "unknown";
        if(!(serverData == null)) {
            if(e.getEntity() == mc.player) {
                if (!mc.isIntegratedServerRunning()) {
                    if (oldadress == null) {
                        oldadress = "none";
                    }
                    if (!oldadress.equals(address)) {
                        address = serverData.serverIP;
                        RPC.server(address, serverData);
                    }
                }
            }
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
            oldadress = "none";
            RPC.single();
        }
    }

    private int tick;

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if(tick >= 40) {
            DiscordRPC.discordRunCallbacks();
            tick = 0;
        }
        tick++;
    }
}
