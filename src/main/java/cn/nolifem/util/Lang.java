package cn.nolifem.util;

import net.minecraft.util.StatCollector;

public class Lang {
	public static String translate(String str){
		return StatCollector.translateToLocal(str);
	}
	
	public static String translateFormatted(String str, Object ... obj){
		return StatCollector.translateToLocalFormatted(str, obj);
	}
}
