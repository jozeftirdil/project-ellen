package sk.tuke.kpi.oop.game;

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
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class SpawnPoint extends AbstractActor {

    private int spawnAliens;
    private Disposable spawningPoint;
    private boolean isPause;

    public SpawnPoint(int spawnAliens) {
        this.spawnAliens = spawnAliens;
        isPause = false;
        Animation animation = new Animation("sprites/spawn.png", 32, 32);
        setAnimation(animation);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        if(spawnAliens > 0) {
            spawningPoint = new Loop<>(new Invoke<>(this::checkSpawning)).scheduleFor(this);
        }
    }

    /*private void enableSpawning() {

        spawningPoint =  new Loop<>(new Invoke<>(this::checkSpawning)).scheduleFor(this);
    }*/

    private void checkSpawning() {

        if(isPause) {
            return;
        }

        for (Actor actor : getScene().getActors()) {
            if (actor instanceof Ripley) {
                Ripley ripley = (Ripley) actor;

                double distance = Math.sqrt(
                    Math.pow(getPosX() - ripley.getPosX(), 2) +
                        Math.pow(getPosY() - ripley.getPosY(), 2)
                );

                if (distance <= 50) {
                    spawnAlien();
                    return;
                }
            }
        }
    }

    private void spawnAlien() {

        //spawningPoint.dispose();
        spawnAliens -= 1;
        isPause = true;

        Alien alien = new Alien(100, new RandomlyMoving());
        getScene().addActor(alien, this.getPosX(), this.getPosY());

        if(spawnAliens == 0){
            spawningPoint.dispose();
            return;
        }

        new ActionSequence<>(
            new Wait<>(3),
            new Invoke<>(() -> {
                isPause = false;
            })
        ).scheduleFor(this);
    }
}
