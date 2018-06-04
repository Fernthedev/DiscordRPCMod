package com.github.fernthedev;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

@SuppressWarnings("WeakerAccess")
public class RPC {

    private long unixTime = System.currentTimeMillis() / 1000L;


    public static String secret;

    public void menu() {
        /*client.setListener(new IPCListener(){
            @Override
            public void onReady(IPCClient client)
            {
                RichPresence.Builder builder = new RichPresence.Builder();
                builder.setState("In Menu")
                        .setDetails("Just doing stuff. Waiting. You Know. ")
                        .setStartTimestamp(OffsetDateTime.now())
                        .setLargeImage("minecraft-large", "Minecraft")
                        .setSmallImage("minecraft-small", "Minecraft");
                        //.setParty("party1234", 1, 6)
                        //.setMatchSecret("xyzzy")
                        //.setJoinSecret("join")
                        //.setSpectateSecret("look");
                client.sendRichPresence(builder.build());
            }
        });
        try {
            client.connect();
        } catch (NoDiscordClientException e1) {
            e1.printStackTrace();
        }*/



        DiscordRichPresence rich = new DiscordRichPresence();
        rich.details = "Just doing stuff. Waiting. You Know. ";
        rich.state = "In Menu";
        rich.largeImageKey = "minecraft-large";
        rich.largeImageText = "Minecraft";
        rich.startTimestamp = unixTime;

        DiscordRPC.discordUpdatePresence(rich);
    }

    public void server(String address, ServerData serverData) {

        DiscordRichPresence rich = new DiscordRichPresence();
        rich.details = "Playing on a server";
        rich.state = "Ip: " + address;
        rich.largeImageKey = "minecraft-large";
        rich.largeImageText = "Minecraft";
        if(!Minecraft.getMinecraft().isIntegratedServerRunning()) {
            String ip = serverData.serverIP;
            secret = ip;

            //ip = ip.replace(".","\\");
            //rich.partyId = ip;
            rich.joinSecret = ip;

            if(ip.contains("hypixel")) {
                rich.smallImageKey = "hypixel";
                rich.smallImageText = "Hypixel";
            }else if(ip.contains("mineplex.com")){
                rich.smallImageKey = "mineplex";
                rich.smallImageText = "Mineplex";
            }else if(ip.contains("cubecraft.net")) {
                rich.smallImageKey = "cubecraft";
                rich.smallImageText = "Cubecraft";
            }else if(ip.contains("phanaticmc") || ip.contains("mcskyblock.com")) {
                rich.smallImageKey = "phanaticmc";
                rich.smallImageText = "PhanaticMC";
            }else if(ip.contains("hivemc.com")) {
                rich.smallImageKey = "hivemc";
                rich.smallImageText = "HiveMC";
            }else if(ip.contains("ubermc.net")) {
                rich.smallImageKey = "ubermc";
                rich.smallImageText = "UberMC";
            }
            /*MinecraftServer server = Minecraft.getMinecraft().getIntegratedServer();
            assert server != null;
            rich.partyMax = server.getMaxPlayers();
            rich.partySize = server.getCurrentPlayerCount();*/
        }
        rich.startTimestamp = unixTime;

        DiscordRPC.discordUpdatePresence(rich);

        /*client.setListener(new IPCListener(){
            @Override
            public void onReady(IPCClient client)
            {
                RichPresence.Builder builder = new RichPresence.Builder();
                builder.setState("In Server ")
                        .setDetails("I'm in the server  " + Players)
                        .setStartTimestamp(OffsetDateTime.now())
                        .setLargeImage("minecraft-large", "Minecraft")
                        .setSmallImage("minecraft-small", "Minecraft")
                .setParty(address, Players, Players);
                //.setMatchSecret("xyzzy")
                //.setJoinSecret("join")
                //.setSpectateSecret("look");
                client.sendRichPresence(builder.build());
            }
        });
        try {
            client.connect();
        } catch (NoDiscordClientException e1) {
            e1.printStackTrace();
        }*/
    }

    public void single() {
        DiscordRichPresence rich = new DiscordRichPresence();
        rich.state = "Playing Survival";
        rich.details = "Playing Mc like a normal person";
        rich.largeImageKey = "minecraft-large";
        rich.largeImageText = "Minecraft";
        rich.startTimestamp = unixTime;

        DiscordRPC.discordUpdatePresence(rich);
        /*client.setListener(new IPCListener(){
            @Override
            public void onReady(IPCClient client)
            {
                RichPresence.Builder builder = new RichPresence.Builder();
                builder.setState("Playing SinglePlayer")
                        .setDetails("Playing mc like a normal person ")
                        .setStartTimestamp(OffsetDateTime.now())
                        .setLargeImage("minecraft-large", "Minecraft")
                        .setSmallImage("minecraft-small", "Minecraft");
                //.setParty("party1234", 1, 6)
                //.setMatchSecret("xyzzy")
                //.setJoinSecret("join")
                //.setSpectateSecret("look");
                client.sendRichPresence(builder.build());
            }
        });
        try {
            client.connect();
        } catch (NoDiscordClientException e1) {
            e1.printStackTrace();
        }
    }*/
    }
}
