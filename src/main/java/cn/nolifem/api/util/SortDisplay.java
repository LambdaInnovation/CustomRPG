package cn.nolifem.api.util;

import cn.nolifem.api.IAttributeCR;

import java.util.Comparator;

/**
 * Created by Nolife_M on 2016/4/9.
 */
public class SortDisplay implements Comparator<IAttributeCR> {

    public final static SortDisplay INSTANCE = new SortDisplay();

    @Override
    public int compare(IAttributeCR o1, IAttributeCR o2) {
        return  o1.getDisplayPreference() > o2.getDisplayPreference() ? 1 : -1;
    }
}
