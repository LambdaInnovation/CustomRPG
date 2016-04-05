package cn.nolifem.api;

import java.util.function.Function;

import cn.nolifem.attributes.BaseAttributeCR;
import cn.nolifem.core.ModProps;

public interface IAttributeCR{
		
	public void addCalc(IAttributeCalculator calculator);
	
	public default String getLang(){
		return ModProps.ATTRIBUTE_LANG + this.getClass().getSimpleName();
	}

	public int getPreference();
	
	public IAttributeCR getClone();
}
