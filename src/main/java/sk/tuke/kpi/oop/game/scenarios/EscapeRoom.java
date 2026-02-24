package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.SpawnPoint;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.MotherAlien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;


public class EscapeRoom implements SceneListener {

    //private Disposable randomlyMoving;

    public static class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {

            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "ammo":
                    return new Ammo();
                case "alien":
                    if(Objects.equals(type, "running")) {
                        return new Alien(100, new RandomlyMoving());
                    }else if(Objects.equals(type, "waiting1")) {
                        return new Alien(100, new Observing<>(
                            Door.DOOR_OPENED,
                            door -> door != null && Objects.equals(door.getName(), "front door"),
                            new RandomlyMoving()
                        ));
                    }else if(Objects.equals(type, "waiting2")) {
                        return new Alien(100, new Observing<>(
                            Door.DOOR_OPENED,
                            door -> door != null && Objects.equals(door.getName(), "back door"),
                            new RandomlyMoving()
                        ));
                    }else {
                        return new Alien(100, null);
                    }
                case "alien mother":
                    return new MotherAlien(new RandomlyMoving());
                case "front door":
                    return new Door("Front door", Door.Orientation.VERTICAL);
                case "back door":
                    return new Door("Back door", Door.Orientation.HORIZONTAL);
                case "exit door":
                    return new Door("Exit door", Door.Orientation.VERTICAL);
                case "spawn":
                    return new SpawnPoint(3);
                default:
                    return null;
            }
        }
    }

    @Override
    public void sceneCreated(@NotNull Scene scene) {

        scene.getMessageBus().subscribe(World.ACTOR_ADDED_TOPIC, actor -> {
            if (actor instanceof Alien) {
                Alien alien = (Alien) actor;
                //randomMovement(alien);
            }
        });
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {

        Ripley ripley = (Ripley) scene.getFirstActorByType(Ripley.class);

        Disposable movableController = scene.getInput().registerListener(new MovableController(ripley));
        Disposable keeperController = scene.getInput().registerListener(new KeeperController(ripley));
        Disposable shooterController = scene.getInput().registerListener(new ShooterController(ripley));

        scene.getGame().pushActorContainer(ripley.getBackpack());

        scene.follow(ripley);

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, ripley1 -> {
            movableController.dispose();
            keeperController.dispose();
            shooterController.dispose();
            //randomlyMoving.dispose();
        });
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {

        Ripley ripley = (Ripley) scene.getFirstActorByType(Ripley.class);

        if (ripley != null) {
            ripley.showRipleyState(scene, ripley);
        }
    }

    /*private void randomMovement(Alien alien){

        randomlyMoving = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> {
                    Move<Movable> move = new Move<>(getRandomDirection(), 2);
                    alien.getScene().scheduleAction(move, alien);
                }),
                new Wait<>(2)
            )
        ).scheduleFor(alien);

    }

    private Direction getRandomDirection(){
        Direction[] directions = Direction.values();
        int random;
        do {
            random = (int) (Math.random() * directions.length);
        } while (directions[random] == Direction.NONE);

        return directions[random];
    }*/
}
