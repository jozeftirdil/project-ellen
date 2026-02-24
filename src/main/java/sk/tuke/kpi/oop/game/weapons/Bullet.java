package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Bullet extends AbstractActor implements Fireable {

    public Bullet() {
        Animation animation = new Animation("sprites/bullet.png", 16, 16);
        setAnimation(animation);
    }

    @Override
    public int getSpeed() {
        return 4;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        targetCheck();
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void collidedWithWall() {
        getScene().removeActor(this);
    }

    public void targetCheck(){
        new Loop<>(
                new Invoke<>(() -> {
                    for (Actor actor : getScene().getActors()) {
                        if (actor instanceof Alive && this.intersects(actor) && !(actor instanceof Ripley)) {
                            ((Alive) actor).getHealth().drain(15);
                            getScene().removeActor(this);
                            return;
                        }
                    }
                })
        ).scheduleFor(this);
    }
}
