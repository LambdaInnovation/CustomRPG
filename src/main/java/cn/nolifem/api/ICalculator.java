package cn.nolifem.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import cn.nolifem.api.attributes.BuffPlacer;
import cn.nolifem.api.attributes.Effect;
import cn.nolifem.core.ModProps;

/**Interface use for store attributes, and place calc;
 */
public interface ICalculator {

    /** Add a Function to FucntionList
     * @param caltype calcType , default SIGMA = 0, PAI = 1
     * @param key attr type to calc
     * @param function calc function
     * @param <S> Number Type use for calc
     */
	public <S> void addFunction(int caltype, String key, Function<S, S> function);

	public <T> T calc(int caltype, String key, T value);
}


