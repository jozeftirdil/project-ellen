package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

public class Fire <A extends Armed> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {

        Armed armed = getActor();

        if(armed == null) {
            setDone(true);
            return;
        }

        Fireable fireable = armed.getFirearm().fire();

        if(fireable == null) {
            setDone(true);
            return;
        }

        Scene scene = armed.getScene();

        int fireableX = (int) armed.getPosX() + (armed.getWidth() / 2) - (fireable.getWidth() / 2);
        int fireableY = (int) armed.getPosY() + (armed.getHeight() / 2) - (fireable.getHeight() / 2);

        scene.addActor(fireable, fireableX, fireableY);

        Move<Movable> move = new Move<>(Direction.fromAngle(armed.getAnimation().getRotation()), Float.MAX_VALUE);
        scene.scheduleAction(move, fireable);

        setDone(true);
    }
}
