package cn.nolifem.attributes.player;

import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.attributes.BaseAttributeCR;

public abstract class PlayerAttribute extends BaseAttributeCR implements Cloneable{

	private double value;
	
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {	
		this.value = value;
	}
}
