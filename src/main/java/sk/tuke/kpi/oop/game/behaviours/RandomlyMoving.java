package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable>{

    public RandomlyMoving() {
    }


    @Override
    public void setUp(Movable actor) {
        if(actor == null)
            return;

            new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        Move<Movable> move = new Move<>(getRandomDirection(), 2);
                        actor.getScene().scheduleAction(move, actor);
                    }),
                    new Wait<>(2)
                )
            ).scheduleFor(actor);
    }

    private Direction getRandomDirection(){
        Direction[] directions = Direction.values();
        int random;
        do {
            random = (int) (Math.random() * directions.length);
        } while (directions[random] == Direction.NONE);

        return directions[random];
    }
}
