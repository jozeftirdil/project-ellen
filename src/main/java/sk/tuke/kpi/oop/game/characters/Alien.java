package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {

    private Health health;
    private Disposable atack;
    private Behaviour<? super Alien> behaviour;

    public Alien() {
        health = new Health(100);
        Animation animation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        getAnimation().stop();
    }

    public Alien(int actualMaxHealth, Behaviour<? super Alien> behaviour) {
        health = new Health(actualMaxHealth);
        this.behaviour = behaviour;
        Animation animation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        getAnimation().stop();

        health.onFatigued(() -> {
            getScene().cancelActions(this);
            getScene().removeActor(this);
        });
    }


    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
    }

    @Override
    public void stoppedMoving() {
        getAnimation().stop();
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(behaviour != null) {
            behaviour.setUp(this);
        }

        attacking(scene);
    }

    private void attacking(Scene scene) {
        atack = new Loop<>(
            new Invoke<>(() -> {
                for (Actor actor : scene.getActors()) {
                    if (actor instanceof Alive && !(actor instanceof Enemy) && this.intersects(actor)) {
                        ((Alive) actor).getHealth().drain(10);
                        stopAtack(scene);
                    }
                }
            })
        ).scheduleFor(this);
    }

    private void stopAtack(Scene scene) {
        atack.dispose();

        new ActionSequence<>(
            new Wait<>(1),
            new Invoke<>(() -> {
                attacking(scene);
            })
        ).scheduleFor(this);
    }

}
