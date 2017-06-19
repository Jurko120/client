package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiScreenOptionsSounds extends GuiScreen
{
    private final GuiScreen field_146505_f;
    private final GameSettings field_146506_g;
    protected String field_146507_a = "Options";
    private String field_146508_h;

    public GuiScreenOptionsSounds(GuiScreen p_i45025_1_, GameSettings p_i45025_2_)
    {
        this.field_146505_f = p_i45025_1_;
        this.field_146506_g = p_i45025_2_;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        byte var1 = 0;
        this.field_146507_a = I18n.format("options.sounds.title", new Object[0]);
        this.field_146508_h = I18n.format("options.off", new Object[0]);
        this.buttonList.add(new GuiScreenOptionsSounds.Button(SoundCategory.MASTER.getCategoryId(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), SoundCategory.MASTER, true));
        int var6 = var1 + 2;
        SoundCategory[] var2 = SoundCategory.values();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            SoundCategory var5 = var2[var4];

            if (var5 != SoundCategory.MASTER)
            {
                this.buttonList.add(new GuiScreenOptionsSounds.Button(var5.getCategoryId(), this.width / 2 - 155 + var6 % 2 * 160, this.height / 6 - 12 + 24 * (var6 >> 1), var5, false));
                ++var6;
            }
        }


        this.buttonList.add(new GuiButton(16, this.width / 2 - 155 + 160, this.height / 6 - 12 + 24 * 5, 150, 20, "Choisir la Radio"));

        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done")));
    }

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        if (p_146284_1_.enabled)
        {
            if (p_146284_1_.id == 200)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.field_146505_f);
            }
            else if (p_146284_1_.id == 16)
            {
                this.mc.gameSettings.saveOfOptions();
                this.mc.displayGuiScreen(new GuiRadio(this));
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146507_a, this.width / 2, 15, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    protected String func_146504_a(SoundCategory p_146504_1_)
    {
        float var2 = this.field_146506_g.getSoundLevel(p_146504_1_);
        return var2 == 0.0F ? this.field_146508_h : (int)(var2 * 100.0F) + "%";
    }

    class Button extends GuiButton
    {
        private final SoundCategory category;
        private final String name;
        public float value = 1.0F;
        public boolean active;

        public Button(int p_i45024_2_, int p_i45024_3_, int p_i45024_4_, SoundCategory p_i45024_5_, boolean p_i45024_6_)
        {
            super(p_i45024_2_, p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
            this.category = p_i45024_5_;
            this.name = I18n.format("soundCategory." + p_i45024_5_.getCategoryName(), new Object[0]);
            this.displayString = this.name + ": " + GuiScreenOptionsSounds.this.func_146504_a(p_i45024_5_);
            this.value = GuiScreenOptionsSounds.this.field_146506_g.getSoundLevel(p_i45024_5_);
        }

        public int getHoverState(boolean p_146114_1_)
        {
            return 0;
        }

        protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_)
        {
            if (this.visible)
            {
                if (this.active)
                {
                    this.value = (float)(p_146119_2_ - (this.xPosition + 4)) / (float)(this.width - 8);

                    if (this.value < 0.0F)
                    {
                        this.value = 0.0F;
                    }

                    if (this.value > 1.0F)
                    {
                        this.value = 1.0F;
                    }

                    p_146119_1_.gameSettings.setSoundLevel(this.category, this.value);
                    p_146119_1_.gameSettings.saveOptions();
                    this.displayString = this.name + ": " + GuiScreenOptionsSounds.this.func_146504_a(this.category);
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.drawTexturedModalRect(this.xPosition + (int)(this.value * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
                this.drawTexturedModalRect(this.xPosition + (int)(this.value * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
            }
        }

        public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
        {
            if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_))
            {
                this.value = (float)(p_146116_2_ - (this.xPosition + 4)) / (float)(this.width - 8);

                if (this.value < 0.0F)
                {
                    this.value = 0.0F;
                }

                if (this.value > 1.0F)
                {
                    this.value = 1.0F;
                }

                p_146116_1_.gameSettings.setSoundLevel(this.category, this.value);
                p_146116_1_.gameSettings.saveOptions();
                this.displayString = this.name + ": " + GuiScreenOptionsSounds.this.func_146504_a(this.category);
                this.active = true;
                return true;
            }
            else
            {
                return false;
            }
        }

        public void playPressSound(SoundHandler p_146113_1_) {}

        public void mouseReleased(int p_146118_1_, int p_146118_2_)
        {
            if (this.active)
            {
                if (this.category == SoundCategory.MASTER)
                {
                    float var10000 = 1.0F;
                }
                else
                {
                    GuiScreenOptionsSounds.this.field_146506_g.getSoundLevel(this.category);
                }

                GuiScreenOptionsSounds.this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
            }

            this.active = false;
        }
    }
}
