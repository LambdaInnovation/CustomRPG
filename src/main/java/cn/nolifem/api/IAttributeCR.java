package cn.nolifem.api;

import cn.lambdalib.s11n.CopyHelper;
import cn.nolifem.core.ModProps;

public interface IAttributeCR{

	public <T> void addToDealer(T dealer);

	public int getDisplayPreference();
	public int getApplyPreference();

	public boolean isTag();

	public default IAttributeCR copy(){
		return CopyHelper.instance.copy(this);
	}

	public default String getLang(){
		return ModProps.ATTRIBUTE_LANG + this.getClass().getSimpleName();
	}
}
