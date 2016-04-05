package cn.nolifem.core;

public class ModProps {
	
    public static final String MODID = "customrpg";
    public static final String MODNAME	= "CustomRPG";
    public static final String VERSION = "0.1";
    public static final String PREFIX = "cr_";
    public static final String TAG = ModProps.MODNAME + "_State";
    public static final String ATAG = ModProps.MODNAME + "_AttributeList";
    
    public static final String RESOURCE = "customrpg:";
    public static final String ModDependencies = "required-after:LambdaLib";
    public static final String ModChannel = "custom_rpg";
    //lang
    public static final String ATTRIBUTE_LANG = "customrpg.attribute.";
    public static final String ITEM_lORE_LANG = "customrpg.item.lore.";
    //path
    public static final String ITEM_PATH = "cn.nolifem.items.";
    public static final String ATTRIBUTE_PATH = "cn.nolifem.attributes.";
    
    public static String lore(String str){
		return ITEM_lORE_LANG + str;
	}
    
    public static String itemFullName(String str){
		return ITEM_PATH + str;
	}
	
	public static String attributeFullName(String str){
		return ATTRIBUTE_PATH + str;
	}
}
