package cn.nolifem.api.attributes;

import cn.nolifem.api.ICalculator;
import cn.nolifem.event.PlayerAttackEvent;

/**
 * Created by Nolife_M on 2016/3/28.
 */
public abstract class Effect extends BaseAttributeCR {

    public abstract void init(PlayerAttackEvent e);
    public abstract void tick(PlayerAttackEvent e);
    public abstract void post(PlayerAttackEvent e);
    public abstract int getDisplayPreference();

    public abstract int getApplyPreference();

    @Override
    public void addFunction(ICalculator calculator) {}
}

