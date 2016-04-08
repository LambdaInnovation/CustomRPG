package cn.nolifem.api;

import cn.lambdalib.s11n.CopyHelper;
import cn.nolifem.core.ModProps;

public interface IAttributeCR{

	public default void addToDealer(IAttributeDealer dealer){
		dealer.getAttrListForCalc().add(this);
		this.addFunction(dealer);
	};

	public void addFunction(IAttributeDealer calculator);

	public default IAttributeCR copy(){
		return CopyHelper.instance.copy(this);
	}

	public default String getLang(){
		return ModProps.ATTRIBUTE_LANG + this.getClass().getSimpleName();
	}

	public int getPreference();

	public boolean isTag();
}
