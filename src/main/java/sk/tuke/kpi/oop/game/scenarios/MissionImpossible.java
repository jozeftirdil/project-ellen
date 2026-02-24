package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;



public class MissionImpossible implements SceneListener {

    private Disposable poisoning;

    public static class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "door":
                    return new LockedDoor();
                case "access card":
                    return new AccessCard();
                case "ventilator":
                    return new Ventilator();
                case "locker":
                    return new Locker();
                default:
                    return null;
            }
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {

        Ripley ripley = (Ripley) scene.getFirstActorByType(Ripley.class);

        Disposable movableController = scene.getInput().registerListener(new MovableController(ripley));
        Disposable keeperController = scene.getInput().registerListener(new KeeperController(ripley));

        scene.getGame().pushActorContainer(ripley.getBackpack());

        scene.follow(ripley);

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, ripley1 -> {
            movableController.dispose();
            keeperController.dispose();
        });

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door -> {
            startPoisoning(ripley);
        });

        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, ventilator ->  {
            poisoning.dispose();
        });
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {

        Ripley ripley = (Ripley) scene.getFirstActorByType(Ripley.class);

        if (ripley != null) {
            ripley.showRipleyState(scene, ripley);
        }
    }

    private void startPoisoning(Ripley ripley){

        poisoning = new Loop<>(
            new ActionSequence<>(
                new Wait<>(1),
                new Invoke<>(() -> {
                    if(ripley.getHealth().getValue() > 0) {
                        ripley.getHealth().drain(3);
                    }
                })
            )
        ).scheduleFor(ripley);
    }

}
