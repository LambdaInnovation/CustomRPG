package cn.nolifem.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;

import cn.nolifem.attributes.effect.BuffPlacer;
import cn.nolifem.attributes.effect.Effect;
import cn.nolifem.attributes.general.PhysicalDamage;


/**Interface use for store attributes, and place calc;
 */
public interface IAttributeDealer {

	//Effect part
	public List<BuffPlacer> getBuffPlacerList();
	public List<Effect> getEffectList();

	//Calculate part
	public List<IAttributeCR> getAttrListForCalc();
	
	public Map<String, List<Function>> getCalcMapSIGMA();
	public Map<String, List<Function>> getCalcMapPAI();

	public default boolean hasSIGMAkey(String key){
		return this.getCalcMapSIGMA().get(key) != null;
	}
	public default boolean hasPAIkey(String key){
		return this.getCalcMapPAI().get(key) != null;
	}
	
	public default <S> void addFunctionSIGMA(String key, Function<S, S> calc){
		List<Function> list = this.getCalcMapSIGMA().get(key);
		if(list == null){
			list = new ArrayList<>();
			this.getCalcMapSIGMA().put(key, list);
		}
		list.add(calc);
	}
	
	public default <S> void addFunctionPAI(String key, Function<S, S> calc){
		List<Function> list = this.getCalcMapPAI().get(key);
		if(list == null){
			list = new ArrayList<>();
			this.getCalcMapPAI().put(key, list);
		}
		list.add(calc);
	}
	
	public default <T> T calc(String key, T value){
		//TEST
		String str = "CriticalRate";
		if(key.equals(str))
			System.out.println("now appling" + key);
		//SIGMA
		List<Function> listSIGMA = this.getCalcMapSIGMA().get(key);
		if(listSIGMA != null){
			for(Function _f : listSIGMA){
				Function<T, T>f = (Function<T, T>) _f;
				value = f.apply(value);
				if(key.equals(str))
					System.out.println("function" + f);
			}
		}
		if(key.equals(str))
			System.out.println("SIGEMA" + value);

		//PAI
		List<Function> listPAI = this.getCalcMapPAI().get(key);
		if(listPAI != null){
			for(Function _f : listPAI){
				Function<T, T>f = (Function<T, T>) _f;
				value = f.apply(value);
			}
		}
		if(key.equals(str))
			System.out.println("PAI" + value);
		return value;
	}
}


