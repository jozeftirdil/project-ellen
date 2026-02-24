package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible {

        public FireExtinguisher(){
            super(1);
            Animation animation = new Animation("sprites/extinguisher.png");
            setAnimation(animation);
        }

        @Override
        public void useWith(Reactor actor) {
            if (actor == null)
                return;

            boolean repair = actor.extinguish();

            if(repair)
                use();
        }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }
}
