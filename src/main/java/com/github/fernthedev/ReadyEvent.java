package com.github.fernthedev;

import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class ReadyEvent implements ReadyCallback {
    @Override
    public void apply(DiscordUser discordUser) {
        DiscordMod.print(this,"Discord's ready!");
        System.out.println("Discord's ready!");
    }
}
