package cn.nolifem.api.util;

import cn.nolifem.api.IAttributeCR;

import java.util.Comparator;

/**
 * Created by Nolife_M on 2016/4/9.
 */
public class SortAttribute implements Comparator<IAttributeCR> {

    public final static  SortAttribute INSTANCE = new SortAttribute();

    @Override
    public int compare(IAttributeCR o1, IAttributeCR o2) {
        return  o1.getPreference() > o2.getPreference() ? 1 : -1;
    }
}
