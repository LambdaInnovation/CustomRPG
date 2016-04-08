package cn.nolifem.attributes.player;

import java.util.List;

import cn.nolifem.api.IAttributeDealer;
import cn.nolifem.api.attributes.PlayerAttribute;
import cn.nolifem.attributes.general.CriticalMulti;
import cn.nolifem.attributes.general.CriticalRate;
import cn.nolifem.attributes.general.PrepareSpeed;

public class Proficient extends PlayerAttribute implements Cloneable{

	@Override
	public void addFunction(IAttributeDealer calculator) {
		calculator.<Double>addFunctionSIGMA(PrepareSpeed.class.getSimpleName(), (input) -> input + 1.0D + this.getValue() * 0.05);
		calculator.<Double>addFunctionSIGMA(CriticalRate.class.getSimpleName(), (input) -> input + (1- this.getValue() * 0.01));
		calculator.<Double>addFunctionSIGMA(CriticalMulti.class.getSimpleName(), (input) -> input + 1.5D + this.getValue() * 0.025);
	}
	
	@Override
	public void addInfo(List arraylist) {
	}

	public int getPreference(){ return 5;}
}
