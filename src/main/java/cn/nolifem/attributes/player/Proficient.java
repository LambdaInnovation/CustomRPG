package cn.nolifem.attributes.player;

import java.util.List;

import cn.nolifem.api.ICalculator;
import cn.nolifem.api.attributes.PlayerAttribute;
import cn.nolifem.attributes.general.CriticalMulti;
import cn.nolifem.attributes.general.CriticalRate;
import cn.nolifem.attributes.general.PrepareSpeed;
import cn.nolifem.core.ModProps;

public class Proficient extends PlayerAttribute implements Cloneable{

	@Override
	public void addFunction(ICalculator calculator) {
		calculator.<Double>addFunction(SIGMA, CriticalMulti.class.getSimpleName(), (input) -> input + 1.5D + this.getValue() * 0.01);
		calculator.<Double>addFunction(SIGMA, CriticalRate.class.getSimpleName(), (input) -> input + this.getValue() * 0.01);
		calculator.<Double>addFunction(SIGMA, PrepareSpeed.class.getSimpleName(), (input) -> input + 1.0D + this.getValue() * 0.05);
	}
	
	@Override
	public void addInfo(List arraylist) {
	}

	public int getDisplayPreference(){ return 5;}
}
