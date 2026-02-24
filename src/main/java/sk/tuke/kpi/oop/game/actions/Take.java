package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Take<A extends Keeper> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {

        Keeper keeper = getActor();

        if(keeper == null) {
            setDone(true);
            return;
        }

        for(Actor actor : keeper.getScene().getActors()){
            if(actor instanceof Collectible && keeper.intersects(actor)){

                try {
                    keeper.getBackpack().add((Collectible) actor);
                    keeper.getScene().removeActor(actor);
                }catch (Exception ex){
                    Overlay overlay = keeper.getScene().getOverlay();
                    overlay.drawText(ex.getMessage(), 0, 0).showFor(2);
                }

                break;
            }
        }

        setDone(true);
    }
}
