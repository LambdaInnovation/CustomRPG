package cn.nolifem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.core.RegistrationManager;
import cn.lambdalib.annoreg.core.RegistrationMod;
import cn.lambdalib.annoreg.mc.RegItem;
import cn.lambdalib.annoreg.mc.RegMessageHandler;
import cn.nolifem.core.ModProps;
import cn.nolifem.items.ModuleItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = ModProps.MODID, name = ModProps.MODNAME, version = ModProps.VERSION,dependencies = ModProps.ModDependencies)
@RegistrationMod(pkg = "cn.nolife", res = "customrpg", prefix = "cr_")
@Registrant
public class CustomRPG
{

    public static boolean debug = true;
    
    public static CustomRPG instance = new CustomRPG();
    
    @RegMessageHandler.WrapperInstance
	public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(ModProps.ModChannel);
    
    public CustomRPG(){}
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent event){
    	RegistrationManager.INSTANCE.registerAll(this, "PreInit");
	}
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	RegistrationManager.INSTANCE.registerAll(this, "Init");
    }
    
    @EventHandler
	public void postInitMod(FMLPostInitializationEvent event){
    	RegistrationManager.INSTANCE.registerAll(this, "PostInit");
	}
    
	public static Object construct(String name) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return Class.forName(name).newInstance();
	}    
	//CreativeTab
    
    @RegItem
    @RegItem.UTName("logo_weapon")
    public static Item logoWeapon;
    
    @RegItem
    @RegItem.UTName("logo_tool")
    public static Item logoTool;
    
    @RegItem
    @RegItem.UTName("logo_misc")
    public static Item logoMisc;
    
    public static CreativeTabs wcct = new CreativeTabs("CustomRPG_Weapon") {
        @Override
        public Item getTabIconItem() {
            return logoWeapon;
        }
    };

    public static CreativeTabs tcct = new CreativeTabs("CustomRPG_Tool") {
        @Override
        public Item getTabIconItem() {
            return logoTool;
        }
    };
    
    public static CreativeTabs mcct = new CreativeTabs("CustomRPG_Misc") {
        @Override
        public Item getTabIconItem() {
            return logoMisc;
        }
    };
}
