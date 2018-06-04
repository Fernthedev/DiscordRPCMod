package com.github.fernthedev;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.JoinRequestCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class RequestJoinEvent implements JoinRequestCallback {
    @Override
    public void apply(DiscordUser discordUser) {
        Minecraft mc = Minecraft.getMinecraft();
        ServerData server = mc.getCurrentServerData();
        boolean isNotNull = false;
        if(server == null) {
            DiscordRPC.discordRespond(discordUser.userId,DiscordRPC.DiscordReply.NO);
        }else{
            if(server.serverIP == null) {
                DiscordRPC.discordRespond(discordUser.userId,DiscordRPC.DiscordReply.NO);
            }else{
                isNotNull = true;
            }
        }
        if(isNotNull) {
            DiscordMod.sendPlayerMessage(Minecraft.getMinecraft().thePlayer, "The user " + discordUser + " has requested to join you");
            DiscordMod.sendPlayerMessage(Minecraft.getMinecraft().thePlayer, "The userid is " + discordUser.userId);
            DiscordMod.sendPlayerMessage(Minecraft.getMinecraft().thePlayer, "The secret is " + RPC.secret);
            if (RPC.secret.equals(server.serverIP)) {
                mc.displayGuiScreen(new OptionMenu(true,discordUser));
            }
                //DiscordRPC.discordRespond(discordUser.userId, DiscordReply.YES);
        }
    }
}
