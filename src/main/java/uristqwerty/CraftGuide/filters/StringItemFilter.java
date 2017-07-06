package uristqwerty.CraftGuide.filters;

import net.minecraft.item.ItemStack;
import uristqwerty.CraftGuide.CommonUtilities;
import uristqwerty.CraftGuide.CraftGuideLog;
import uristqwerty.CraftGuide.ForgeExtensions;
import uristqwerty.CraftGuide.api.ItemFilter;
import uristqwerty.CraftGuide.api.NamedTexture;
import uristqwerty.CraftGuide.api.Renderer;
import uristqwerty.CraftGuide.api.Util;

import java.util.ArrayList;
import java.util.List;

public class StringItemFilter implements ItemFilter
{
	String comparison;
	private NamedTexture textImage = Util.instance.getTexture("TextFilter");

	public StringItemFilter(String string)
	{
		comparison = string.toLowerCase();
	}

	@Override
	public boolean matches(Object item)
	{
		if(item instanceof ItemStack)
		{
			try
			{
				return CommonUtilities.searchExtendedItemStackText(item, comparison);
			}
			catch (Throwable e)
			{
				CraftGuideLog.log("exception trace: uristqwerty.CraftGuide.StringItemFilter.matches ItemStack branch");
				throw new RuntimeException(e);
			}
		}
		else if(item instanceof String)
		{
			try
			{
				return ((String)item).toLowerCase().contains(comparison);
			}
			catch (Throwable e)
			{
				CraftGuideLog.log("exception trace: uristqwerty.CraftGuide.StringItemFilter.matches String branch");
				throw new RuntimeException(e);
			}
		}
		else if(item instanceof List)
		{
			try
			{
				List<?> list = (List<?>)item;

				for(Object o: list)
				{
					if(matches(o))
					{
						return true;
					}
				}

				if(list.size() < 1)
				{
					List<String> lines = ForgeExtensions.emptyOreDictEntryText(list);

					if(lines != null)
					{
						for(String line: lines)
						{
							if(line != null && line.toLowerCase().contains(comparison))
							{
								return true;
							}
						}
					}
				}


				return false;
			}
			catch (Throwable e)
			{
				CraftGuideLog.log("exception trace: uristqwerty.CraftGuide.StringItemFilter.matches List branch");
				throw new RuntimeException(e);
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public void draw(Renderer renderer, int x, int y)
	{
		renderer.renderRect(x, y, 16, 16, textImage);
	}

	@Override
	public List<String> getTooltip()
	{
		List<String> text = new ArrayList<String>(1);
		text.add("\u00a77Text search: '" + comparison + "'");
		return text;
	}
}
