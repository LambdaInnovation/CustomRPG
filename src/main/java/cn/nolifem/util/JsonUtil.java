package cn.nolifem.util;

import java.io.IOException;

import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;

import cn.lambdalib.util.generic.RegistryUtils;

public class JsonUtil {
	public static String getJson(ResourceLocation json){
		try {
			return IOUtils.toString(RegistryUtils.getResourceStream(json));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
