package com.github.fernthedev;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

import static com.github.fernthedev.DiscordMod.MODID;


public class ConfigMenu extends GuiConfig {
    /**
     * This constructor handles the {@code @Config} configuration classes
     *
     * @param parentScreen the parent GuiScreen object
     */
    public ConfigMenu(GuiScreen parentScreen) {
        super(parentScreen, MODID, "thing");
    }
    /*
    public ConfigMenu(GuiScreen parentScreen) {

        super(parentScreen,
                new ConfigElement(
                        ConfigHandler.config.getCategory("discord")).getChildElements(),DiscordMod.MODID,
                false,
                true,
                "Play with discord's settings :O");
        titleLine2 = DiscordMod.configfile.getAbsolutePath();

    }*/

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

}
