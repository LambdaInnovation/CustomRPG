package cn.nolifem.api.attributes;

import cn.nolifem.api.ICalculator;
import cn.nolifem.event.PlayerAttackEvent;

/**
 * Created by Nolife_M on 2016/3/28.
 */
public abstract class BuffPlacer extends BaseAttributeCR {

    public abstract void place(PlayerAttackEvent e);

    public abstract int getApplyPreference();

    @Override
    public void addFunction(ICalculator calculator) {}
}

