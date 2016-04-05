package cn.nolifem.attributes;

import java.util.HashMap;

public class Material {
	
	public static HashMap<String, Material> map = new HashMap<String, Material>();
	public static int GRIND_DAMAGE = 10;
	
	public String name;
	public int maxDamage;
	public double sharpnessDecrease = 2;
		
	public Material(){}
	
	public static void put(Material m){
		map.put(m.name, m);
	}
	
	public static Material get(String str){
		return map.get(str);
	}
}
