package cn.nolifem.attributes;

import java.lang.reflect.Type;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.mc.RegPreInitCallback;
import cn.nolifem.core.ModProps;
import cn.nolifem.util.JsonUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

@Registrant
public class ModuleAttribute {
	
	public static Gson gson = new Gson();
	public static ResourceLocation mjson = new ResourceLocation(ModProps.RESOURCE + "meterial.json");	
	
	@RegPreInitCallback
	public static void init(){
		//Meterial
		JsonArray jsonm = gson.fromJson(JsonUtil.getJson(mjson), JsonArray.class);
		Type typem = new TypeToken<List<Material>>() { }.getType();
		List<Material> materials = gson.fromJson(jsonm, typem);
		
		for(Material m : materials){
			Material.put(m);
		}
	}
}
