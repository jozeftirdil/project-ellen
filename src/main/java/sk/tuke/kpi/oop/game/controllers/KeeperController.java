package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;


public class KeeperController implements KeyboardListener {

    private Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {

        if(key == Input.Key.ENTER){
            new Take<>().scheduleFor(keeper);

        }else if(key == Input.Key.S){
            new Shift<>().scheduleFor(keeper);

        }else if(key == Input.Key.BACKSPACE){
            new Drop<>().scheduleFor(keeper);

        }else if(key == Input.Key.U){
            keeper.getScene().getActors().stream()
                .filter(actor -> actor instanceof Usable)
                .filter(actor -> actor.intersects(keeper))
                .findFirst()
                .ifPresent(actor -> {
                    new Use<>((Usable<?>) actor).scheduleForIntersectingWith(keeper);
                });

        }else if(key == Input.Key.B){
            Collectible item = keeper.getBackpack().peek();

            if (item instanceof Usable<?>) {
                Usable<?> usable = (Usable<?>) item;
                new Use<>(usable).scheduleForIntersectingWith(keeper);
            }
        }
    }
}
