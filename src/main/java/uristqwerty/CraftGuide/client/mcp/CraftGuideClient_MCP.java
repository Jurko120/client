package uristqwerty.CraftGuide.client.mcp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import uristqwerty.CraftGuide.CraftGuide;
import uristqwerty.CraftGuide.CraftGuideLog;
import uristqwerty.CraftGuide.GuiCraftGuide;
import uristqwerty.CraftGuide.client.CraftGuideClient;

public class CraftGuideClient_MCP extends CraftGuideClient
{
	private KeyBinding key;

	@Override
	public void initKeybind()
	{
		key = Minecraft.getMinecraft().gameSettings.keyBindCraftGuide;
	}

	@Override
	public void checkKeybind()
	{
		try
		{
			if(key != null && CraftGuide.enableKeybind && Keyboard.isKeyDown(key.getKeyCode()))
			{
				Minecraft mc = Minecraft.getMinecraft();
				GuiScreen screen = mc.currentScreen;

				if(screen == null)
				{
					CraftGuide.side.openGUI(mc.thePlayer);
				}
				else if(screen instanceof GuiContainer)
				{
					try
					{
						int x = Mouse.getX() * screen.width / mc.displayWidth;
						int y = screen.height - (Mouse.getY() * screen.height / mc.displayHeight) - 1;
						int left = ((GuiContainer)screen).field_147003_i;
						int top = ((GuiContainer)screen).field_147009_r;
						openRecipe((GuiContainer)screen, x - left, y - top);
					}
					catch(IllegalArgumentException e)
					{
						CraftGuideLog.log(e, "Exception while trying to identify if there is an item at the current cursor position.", true);
					}
					catch(SecurityException e)
					{
						CraftGuideLog.log(e, "Exception while trying to identify if there is an item at the current cursor position.", true);
					}
				}
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			CraftGuideLog.log(e, "Exception while checking keybind.", true);
		}
	}

	private void openRecipe(GuiContainer screen, int x, int y)
	{
		Container container = screen.inventorySlots;

		for(int i = 0; i < container.inventorySlots.size(); i++)
		{
			Slot slot = (Slot)container.inventorySlots.get(i);
	        if(x > slot.xDisplayPosition - 2 && x < slot.xDisplayPosition + 17 && y > slot.yDisplayPosition - 2 && y < slot.yDisplayPosition + 17)
	        {
	        	ItemStack item = slot.getStack();

	        	if(item != null)
	        	{
	    			Minecraft mc = Minecraft.getMinecraft();
	        		GuiCraftGuide.getInstance().setFilterItem(item);
					CraftGuide.side.openGUI(mc.thePlayer);
	        	}

	        	break;
	        }
		}
	}

	@Override
	public void openGUI(EntityPlayer player)
	{
		//FMLClientHandler.instance().displayGuiScreen(player, GuiCraftGuide.getInstance());
		Minecraft.getMinecraft().displayGuiScreen(GuiCraftGuide.getInstance());
	}

	boolean failed = false;

	@Override
	public void stopTessellating()
	{
		if(failed)
			return;

		try
		{
			if(Tessellator.instance.isDrawing)
			{
				Tessellator.instance.draw();
			}
		}
		catch(SecurityException e)
		{
			CraftGuideLog.log(e, "", true);
			failed = true;
		}
		catch(IllegalArgumentException e)
		{
			CraftGuideLog.log(e, "", true);
			failed = true;
		}
	}
}
