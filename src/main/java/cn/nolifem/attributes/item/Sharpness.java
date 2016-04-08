package cn.nolifem.attributes.item;

import java.util.List;

import cn.lambdalib.s11n.SerializeIncluded;
import cn.nolifem.api.ICalculator;
import cn.nolifem.api.attributes.ItemAttribute;
import cn.nolifem.core.ModProps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.attributes.general.PhysicalDamage;
import cn.nolifem.util.Lang;

public class Sharpness extends ItemAttribute implements Cloneable{
	
	public double sharpness = 100;
	public double sharpnessDecrease = 2;

	@SerializeIncluded
	private int MAXSHARPNESS = 100;
	@SerializeIncluded
	private double GRINDDAMAGE = 1;
	
	public Sharpness(){super();}
	
	@Override
	public void addInfo(List arraylist){
		EnumChatFormatting color = sharpness < 33 ? EnumChatFormatting.RED 
				: sharpness > 66 ? EnumChatFormatting.GREEN 
				: EnumChatFormatting.YELLOW;
		StringBuffer info = new StringBuffer(color + Lang.translate(super.getLang()) + " ");
		arraylist.add(1, info.append(this.sharpness).append("/").append((int)MAXSHARPNESS).toString());
	}

	/**Decrease sharpness when attacking
	 * @param player
	 * @return true if sharpness > 0
	 */
	public boolean dealAttack(EntityPlayer player){
		if(!player.capabilities.isCreativeMode){
			sharpness -= sharpnessDecrease;
			if(sharpness < 0){
				sharpness = 0;
				return false;
			}	
		}
		return true;
	}
	
	public void dealGrind(double add){
		sharpness += add;
		if(sharpness > 100)
			sharpness = 100;
	}

	@Override
	public void addFunction(ICalculator calculator) {
		calculator.<Double>addFunction(PAI, PhysicalDamage.class.getSimpleName(), (input) -> input * (1.0 + sharpness/250.0 - 0.2 ));
	}

	public int getDisplayPreference(){ return 0;}
}
