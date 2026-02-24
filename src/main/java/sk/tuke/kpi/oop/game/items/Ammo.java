package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;

public class Ammo extends AbstractActor implements Usable<Armed> {

    public Ammo(){
        Animation animation = new Animation("sprites/ammo.png");
        setAnimation(animation);
    }

    @Override
    public void useWith(Armed actor) {
        if(actor == null) {
            return;
        }

        int ammo = actor.getFirearm().getAmmo();

        if(ammo < 500) {
            actor.getFirearm().reload(50);

            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }
}
