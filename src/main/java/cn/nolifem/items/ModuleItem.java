package cn.nolifem.items;

import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.mc.RegInitCallback;
import cn.nolifem.CustomRPG;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.core.ModProps;
import cn.nolifem.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Registrant
public class ModuleItem {

	public static List<Item> items = new ArrayList<>();
	//public static Item[] items;
	public static Item grindTool;
	public static Gson gson = new Gson();
	public static ResourceLocation wjson = new ResourceLocation(ModProps.RESOURCE + "weapons.json");
	public static ResourceLocation mjson = new ResourceLocation(ModProps.RESOURCE + "misc.json");
		
	@RegInitCallback
	public static void init(){
		//Items
		JsonArray wjarray = gson.fromJson(JsonUtil.getJson(wjson), JsonArray.class);
		JsonArray mjarray = gson.fromJson(JsonUtil.getJson(mjson), JsonArray.class);
		//items = new Item[wjarray.size() + mjarray.size()];
		
		//for(int i = 0; i < items.length; i++){
		for(int i = 0; i < wjarray.size() + mjarray.size(); i++){
			//weapons
			JsonObject object = new JsonObject();
			if(i < wjarray.size())
				object = wjarray.get(i).getAsJsonObject();
			else if(i < wjarray.size() + mjarray.size())
				object = mjarray.get(i - wjarray.size()).getAsJsonObject();
			
			try {
				//item
				Optional<Item> opt = Optional.of((Item) (CustomRPG.construct(
						ModProps.itemFullName(object.get("type").getAsString()))));
				
				if(opt.isPresent()){
					Item item = opt.get();
					setItemName(item, object.get("name").getAsString()); 
					
					//put attributes
					if(item instanceof IAttributeContainer){
						JsonArray attArray = object.get("attributes").getAsJsonArray();
						
						if(attArray != null){
							for(int j = 0; j < attArray.size(); j++){
								
								JsonObject attObj = attArray.get(j).getAsJsonObject();
								//System.out.println("Attribute check" + attObj);
								if(attObj != null){
									Class<?> type = Class.forName(
											ModProps.attributeFullName(
													attObj.get("type").getAsString()));

									//System.out.println((IAttributeCR)gson.fromJson(attObj.get("data").getAsJsonObject(), type));
									((IAttributeContainer)item).putAttr(
											(IAttributeCR)gson.fromJson(
											attObj.get("data").getAsJsonObject(), type));
									
									((IAttributeContainer)item).initAttr();
								}
							}
							((IAttributeContainer) item).initAttr();
						}else
							System.out.println("[WARN]A IAttributeContainer didn't got it's attribues, check your Json!");
					}
					//put in
					//items[i] = item;
					items.add(item);
				}
				
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		//
		items.add(new ItemGrindTool("grind_tool"));
		//
		register();
	}
	
	private static void register(){
		for(int i=0; i<items.size(); i++){
				GameRegistry.registerItem(items.get(i),items.get(i).getUnlocalizedName());
		}
	}
	
	public static void setItemName(Item item, String name){ 
		item.setUnlocalizedName(ModProps.PREFIX + name);
		item.setTextureName(ModProps.RESOURCE+ name);
	}
}
