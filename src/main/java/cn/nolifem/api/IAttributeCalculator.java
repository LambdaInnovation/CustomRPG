package cn.nolifem.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;

import cn.nolifem.attributes.general.PhysicalDamage;


/**Interface use for store attributes, and apply calc;
 */
public interface IAttributeCalculator {
	
	public List<IAttributeCR> getAttrListForCalc();
	
	public Map<String, List<Function>> getCalcMapSIGMA();
	public Map<String, List<Function>> getCalcMapPAI();
	
	public default void addAttrForCalc(IAttributeCR attr){
		this.getAttrListForCalc().add(attr);
		attr.addCalc(this);
	}

	public default boolean hasCalcList(String key){
		return this.getCalcMapSIGMA().get(key) != null;
	}
	
	public default <S> void addCalculationSIGMA(String key, Function<S, S> calc){
		List<Function> list = this.getCalcMapSIGMA().get(key);
		if(list == null){
			list = new ArrayList<>();
			this.getCalcMapSIGMA().put(key, list);
		}
		list.add(calc);
	}
	
	public default <S> void addCalculationPAI(String key, Function<S, S> calc){
		List<Function> list = this.getCalcMapPAI().get(key);
		if(list == null){
			list = new ArrayList<>();
			this.getCalcMapPAI().put(key, list);
		}
		list.add(calc);
	}
	
	public default <T> T applyCalc(String key, T value){
		//SIGMA
		String str = "CriticalRate";
		if(key.equals(str))
			System.out.println("now appling" + key);
		Optional<List<Function>> listSIGMA = Optional.ofNullable(this.getCalcMapSIGMA().get(key));
		if(listSIGMA.isPresent()){
			for(Function _f : listSIGMA.get()){
				Function<T, T>f = (Function<T, T>) _f;
				value = f.apply(value);
				if(key.equals(str))
					System.out.println("function" + f);
			}
		}
		if(key.equals(str))
			System.out.println("SIGEMA" + value);
		//PAI
		Optional<List<Function>> listPAI = Optional.ofNullable(this.getCalcMapPAI().get(key));
		if(listPAI.isPresent()){
			for(Function _f : listPAI.get()){
				Function<T, T>f = (Function<T, T>) _f;
				value = f.apply(value);
			}
		}
		if(key.equals(str))
			System.out.println("PAI" + value);
		return value;
	}
}


