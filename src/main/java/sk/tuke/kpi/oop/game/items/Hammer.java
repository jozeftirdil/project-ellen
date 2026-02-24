package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible{

    public Hammer(){
        super(1);
        Animation animation = new Animation("sprites/hammer.png");
        setAnimation(animation);
    }

    public Hammer(int remainingUses) {
        super(remainingUses);
        Animation animation = new Animation("sprites/hammer.png");
        setAnimation(animation);
    }

    @Override
    public void useWith(Repairable actor) {
        if (actor == null)
            return;

        boolean repair = actor.repair();

        if(repair)
            use();
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}

