package cn.nolifem.core.loadrules;

import cn.nolifem.core.ModProps;

public abstract class LoadRule{
	public String type;
	
	public LoadRule(){}

	public abstract String getType();
	
	public class ItemRule extends LoadRule{
		
		public String name;
		public AttributeRule[] attributes;

		public String getType() {
			return ModProps.ITEM_PATH + this.type;
		}
	}
	
	public class AttributeRule extends LoadRule{
		public Object[] values;

		public String getType() {
			return ModProps.ATTRIBUTE_PATH + this.type;
		}
	}
}
