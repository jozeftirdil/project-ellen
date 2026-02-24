package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {

    private boolean is_broken;

    private Animation animation;

    public Ventilator() {
        is_broken = true;
        animation = new Animation("sprites/ventilator.png", 32, 32, 0.1f);
        setAnimation(animation);
        animation.stop();
    }

    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);

    @Override
    public boolean repair() {
        if(!is_broken)
            return false;

        is_broken = false;
        animation.play();
        super.getScene().getMessageBus().publish(VENTILATOR_REPAIRED, this);
        return true;
    }
}
