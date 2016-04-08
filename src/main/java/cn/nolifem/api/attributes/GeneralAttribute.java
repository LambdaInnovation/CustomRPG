package cn.nolifem.api.attributes;

import cn.lambdalib.s11n.SerializeIncluded;
import cn.nolifem.api.IAttributeCR;

/**Simple Attribute with one value
 * @author Nolife_M
 * @param <T> Attribute's value
 */
public abstract class GeneralAttribute extends BaseAttributeCR implements Cloneable{

	@SerializeIncluded
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
