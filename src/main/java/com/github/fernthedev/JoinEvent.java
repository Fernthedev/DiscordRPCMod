package com.github.fernthedev;

import cpw.mods.fml.client.FMLClientHandler;
import net.arikia.dev.drpc.callbacks.JoinGameCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class JoinEvent implements JoinGameCallback {
    @Override
    public void apply(String secret) {
        Minecraft mc = Minecraft.getMinecraft();
        ServerData server = new ServerData("",secret);
        FMLClientHandler.instance().connectToServer(mc.currentScreen,server);
    }
}
