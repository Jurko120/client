package bspkrs.armorstatushud.fml;

import bspkrs.util.config.Configuration;

import java.util.logging.Logger;

public class Reference {
   public static final String MODID = "ArmorStatusHUD";
   public static final String NAME = "ArmorStatusHUD";
   public static final String PROXY_COMMON = "bspkrs.armorstatushud.fml.CommonProxy";
   public static final String PROXY_CLIENT = "bspkrs.armorstatushud.fml.ClientProxy";
   public static final String GUI_FACTORY = "bspkrs.armorstatushud.fml.gui.ModGuiFactoryHandler";
   public static final Logger LOGGER = Logger.getLogger("ArmorStatusHUD");
   public static Configuration config = null;
}
