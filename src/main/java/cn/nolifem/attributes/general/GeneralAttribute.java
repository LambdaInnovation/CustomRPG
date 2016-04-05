package cn.nolifem.attributes.general;

import net.minecraft.entity.EntityLivingBase;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.attributes.BaseAttributeCR;
import cn.nolifem.attributes.player.PlayerAttribute;

/**Simple Attribute with one value
 * @author Nolife_M
 * @param <T> Attribute's value
 */
public abstract class GeneralAttribute extends BaseAttributeCR implements Cloneable{

	private double value;
		
	public GeneralAttribute(){}

	public double getValue() {
		return this.value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	public abstract Class<? extends IAttributeCR> getPlayerAttrClass();	
}
