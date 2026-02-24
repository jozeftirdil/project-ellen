package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {

    private Reactor reactor;

    private boolean is_on;

    public Cooler(Reactor reactor) {
        is_on = false;
        this.reactor = reactor;
        Animation animation = new Animation("sprites/fan.png", 32, 32, 0.2f);
        setAnimation(animation);
        getAnimation().stop();
    }

    @Override
    public void turnOn(){
        is_on = true;
        getAnimation().play();
    }

    @Override
    public void turnOff(){
        is_on = false;
        getAnimation().stop();
    }

    @Override
    public boolean isOn() {
        return is_on;
    }

    private void coolReactor(){
        if(reactor == null)
            return;

        if(is_on)
            reactor.decreaseTemperature(1);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
