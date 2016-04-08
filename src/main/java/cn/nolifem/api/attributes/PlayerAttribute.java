package cn.nolifem.api.attributes;

import cn.lambdalib.s11n.SerializeIncluded;

public abstract class PlayerAttribute extends BaseAttributeCR implements Cloneable{

	@SerializeIncluded
	private double value;
	
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {	
		this.value = value;
	}
}
