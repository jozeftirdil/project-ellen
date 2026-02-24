package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop <A extends Keeper> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {

        Keeper keeper = getActor();

        if(keeper == null) {
            setDone(true);
            return;
        }

        Collectible item = keeper.getBackpack().peek();

        if(item == null) {
            setDone(true);
            return;
        }

        Scene scene = keeper.getScene();

        keeper.getBackpack().remove(item);

        int itemX = (int)keeper.getPosX() + (keeper.getWidth() / 2) - (item.getWidth() / 2);
        int itemY = (int)keeper.getPosY() + (keeper.getHeight() / 2) - (item.getHeight() / 2);

        scene.addActor(item, itemX, itemY);

        setDone(true);
    }
}
