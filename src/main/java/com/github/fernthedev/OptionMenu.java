package com.github.fernthedev;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordUser;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class OptionMenu extends GuiScreen {

    private GuiButton closebutton;

    private GuiButton acceptbutton;

    private GuiButton denybutton;
    private boolean reply;
    private DiscordUser discordUser;

    @SuppressWarnings("WeakerAccess")
    public OptionMenu(boolean reply, DiscordUser discordUser) {
        this.reply = reply;
        this.discordUser = discordUser;
    }

    /*@SubscribeEvent
    public void openMenu(GuiScreenEvent.InitGuiEvent e) {
        if(e.gui instanceof GuiOptions) {
            GuiButton optionMenu = new GuiButton(89,this.width / 2 - 20, this.height - (this.height / 4) - 40,"Discord");
            e.buttonList.add(optionMenu);
        }
    }

    @SubscribeEvent
    public void clickedFern(GuiScreenEvent.ActionPerformedEvent e) {
        if(e.gui instanceof GuiOptions) {
            if(e.button.id == 89) {
                Minecraft minecraft = Minecraft.getMinecraft();
                minecraft.displayGuiScreen(this);
            }
        }
    }*/

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        closebutton = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) + 10, "Close");
        buttonList.add(closebutton);

        acceptbutton = new GuiButton(1, this.width / 2 - 100, 0, "Accept");
        denybutton = new GuiButton(1, this.width / 2 - 100, -10, "Deny");
        buttonList.add(acceptbutton);
        buttonList.add(denybutton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == closebutton) {
            //System.out.println("Closed");
            //mc.thePlayer.closeScreen();
            if(reply)
            DiscordRPC.discordRespond(discordUser.userId, DiscordRPC.DiscordReply.IGNORE);
        }
        if(button == acceptbutton) {
            if(reply)
            DiscordRPC.discordRespond(discordUser.userId, DiscordRPC.DiscordReply.YES);
            //System.out.println("Yes");
            //mc.thePlayer.closeScreen();
        }

        if(button == denybutton) {
            if(reply)
            DiscordRPC.discordRespond(discordUser.userId, DiscordRPC.DiscordReply.NO);
            //System.out.println("No");
            //mc.thePlayer.closeScreen();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
