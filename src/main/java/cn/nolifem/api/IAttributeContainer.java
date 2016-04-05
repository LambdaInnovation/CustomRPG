package cn.nolifem.api;

import java.util.*;
import java.util.Map.Entry;


/**Interface use for store attributes, and apply calc;
 */
public interface IAttributeContainer {
	
	public Map<String, IAttributeCR> getAttrMap();

    public static final Comparator comp = new SortAttribute();
	
	public default Map<String, IAttributeCR> copyAttrMap(){
		Map<String, IAttributeCR> result = new HashMap();
		IAttributeCR attr;
		for(IAttributeCR _attr : this.getAttrMapAsList()){
			attr = _attr.getClone();
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
	
	public void initAttr();
	
	public default IAttributeCR getAttr(Class<? extends IAttributeCR> clazz){
		return this.getAttrMap().get(clazz.getSimpleName());
	}

	public default List<IAttributeCR> getAttrMapAsList(){
		List<IAttributeCR> attlist = new ArrayList<>();
		
		Map map = this.getAttrMap();
		Iterator<Entry<String, IAttributeCR>> iter = getAttrMap().entrySet().iterator();
		while (iter.hasNext()) {
			attlist.add((IAttributeCR)
					((Map.Entry<String, IAttributeCR>)iter.next()).getValue());
		}
        attlist.sort(comp);
        //System.out.println(attlist);
		return attlist;
	}

	class SortAttribute implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            //System.out.println("Sorting" + (((IAttributeCR) o1).getPreference() > ((IAttributeCR) o2).getPreference()));
            return ((IAttributeCR) o1).getPreference() > ((IAttributeCR) o2).getPreference() ? 1 : -1;
        }
    }
}
