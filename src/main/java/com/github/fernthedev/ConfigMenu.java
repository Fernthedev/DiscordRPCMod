package com.github.fernthedev;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigMenu extends GuiConfig {

/*
    private GuiButton closebutton;

    private GuiButton autoignore;
    private GuiButton showpresence;
    private GuiTextField text;
    private String textmessage;*/

    //public ConfigMenu(GuiScreen parentScreen, List<IConfigElement> configElements, String modID, String configID, boolean allRequireWorldRestart, boolean allRequireMcRestart, String title) {
      //  super(parentScreen, configElements, modID, configID, allRequireWorldRestart, allRequireMcRestart, title);
    //}

    public ConfigMenu(GuiScreen parentScreen) {
        super(parentScreen,
                new ConfigElement(
                        ConfigHandler.config.getCategory("discord")).getChildElements(),DiscordMod.MODID,
                        false,
                        true,
                        "Play with discord's settings :O");
        titleLine2 = DiscordMod.configfile.getAbsolutePath();

    }

    @Override
    public void initGui()
    {
        // You can add buttons and initialize fields here
        super.initGui();
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        // You can do things like create animations, draw additional elements, etc. here
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        // You can process any additional buttons you may have added here
        super.actionPerformed(button);
    }

/*    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        ConfigHandler.syncConfig();
        this.text = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, this.height /2 + 100,40,40);
        text.setMaxStringLength(40);
        text.setText(ConfigHandler.message);
        text.setVisible(true);
        this.text.setFocused(true);
        autoignore = new GuiButton(0, this.width / 2 - 50, this.height /2 + 100, "Autoignore: " + ConfigHandler.autoignore);
        buttonList.add(autoignore);

        showpresence = new GuiButton(2, this.width / 2 - 100, this.height - (this.height / 4) + 30, "Show presence:" + ConfigHandler.showpresence);
        buttonList.add(showpresence);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button == autoignore) {
            ConfigHandler.updateConfig(!ConfigHandler.autoignore,ConfigHandler.Settings.autoignore);
        }

        if(button == showpresence) {
            ConfigHandler.updateConfig(!ConfigHandler.showpresence,ConfigHandler.Settings.showpresence);
        }


        ConfigHandler.syncConfig();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.text.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void updateScreen()
    {
        super.updateScreen();
        this.text.updateCursorCounter();
        //ConfigHandler.updateConfig(text.getText(),ConfigHandler.Settings.message);
        textmessage = text.getText();
        ConfigHandler.syncConfig();
    }

    protected void keyTyped(char par1, int par2) {
        try {
            super.keyTyped(par1, par2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.text.textboxKeyTyped(par1, par2);
    }

    protected void mouseClicked(int x, int y, int btn) {
        try {
            super.mouseClicked(x, y, btn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.text.mouseClicked(x, y, btn);
    }*/
}
