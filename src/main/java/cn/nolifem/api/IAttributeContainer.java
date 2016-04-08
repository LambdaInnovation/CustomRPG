package cn.nolifem.api;

import cn.nolifem.api.util.SortApply;
import cn.nolifem.api.util.SortDisplay;

import java.util.*;
import java.util.Map.Entry;


/**Interface use for store attributes, and place calc;
 */
public interface IAttributeContainer {

	public static final Comparator displayComp = SortDisplay.INSTANCE;
	public static final Comparator applyComp = SortApply.INSTANCE;

	public void initAttr();

	public Map<String, IAttributeCR> getAttrMap();

	public default List<IAttributeCR> getAttrMapAsList(){
		List<IAttributeCR> attlist = new ArrayList<>();

		Map map = this.getAttrMap();
		Iterator<Entry<String, IAttributeCR>> iter = getAttrMap().entrySet().iterator();
		while (iter.hasNext()) {
			attlist.add((IAttributeCR)
					((Map.Entry<String, IAttributeCR>)iter.next()).getValue());
		}
		attlist.sort(displayComp);
		//System.out.println(attlist);
		return attlist;
	}

	public default Map<String, IAttributeCR> copyAttrMap(){
		Map<String, IAttributeCR> result = new HashMap();
		IAttributeCR attr;
		for(IAttributeCR _attr : this.getAttrMapAsList()){
			attr = _attr.copy();
			result.put(attr.getClass().getSimpleName(), attr);
		}
		return result;
	}
	
	public default IAttributeContainer putAttr(IAttributeCR attr){
		if(this.getAttrMap().get(attr.getClass().getSimpleName()) == null){
			this.getAttrMap().put(attr.getClass().getSimpleName(), attr);
		}	
		return this;
	}

	public default IAttributeCR getAttr(Class<? extends IAttributeCR> clazz){
		return this.getAttrMap().get(clazz.getSimpleName());
	}

}
