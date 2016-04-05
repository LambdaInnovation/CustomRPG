package cn.nolifem.attributes.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.attributes.BaseAttributeCR;
import cn.nolifem.attributes.general.PhysicalDamage;
import cn.nolifem.util.Lang;

public class Sharpness extends BaseAttributeCR implements Cloneable{
	
	public double sharpness = 100;
	public double sharpnessDecrease = 2;
	
	private int MAXSHARPNESS = 100;
	private double GRINDDAMAGE = 1;
	
	public Sharpness(){
		setTag();
	}
	
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
	public void addCalc(IAttributeCalculator calculator) {
		calculator.<Double>addCalculationPAI(PhysicalDamage.class.getSimpleName(), (input) -> input * (1.0 + sharpness/250.0 - 0.2 ));
	}

	public int getPreference(){ return 0;}
}
