package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Actor> {

    private boolean used;

    public Locker() {
        used = false;
        Animation animation = new Animation("sprites/locker.png");
        setAnimation(animation);
    }

    @Override
    public void useWith(Actor actor) {
        if(actor == null || used)
            return;

        used = true;

        Scene scene = actor.getScene();

        int hammerX = (int)this.getPosX() + (this.getWidth() / 2) - (this.getWidth() / 2);
        int hammerY = (int)this.getPosY() + (this.getHeight() / 2) - (this.getHeight() / 2);

        scene.addActor(new Hammer(), hammerX, hammerY);

    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
