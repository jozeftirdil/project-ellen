package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {

    private Movable movable;

    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );

    private Move<Movable> move;

    private Set<Direction> activeDirections;

    public MovableController(Movable movable) {
        this.movable = movable;
        this.activeDirections = new HashSet<>();
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if(!isUsableKey(key))
            return;

        Direction direction = keyDirectionMap.get(key);
        activeDirections.add(direction);

        movementUpdate();
    }

    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if(!isUsableKey(key))
            return;

        Direction direction = keyDirectionMap.get(key);
        activeDirections.remove(direction);

        movementUpdate();
    }

    private boolean isUsableKey(Input.Key key) {
        return movable != null && keyDirectionMap.containsKey(key);
    }

    private void movementUpdate(){
        if(move != null && !move.isDone())
            move.stop();

        Direction combinedDirection = Direction.NONE;

        for(Direction direction : activeDirections){
            combinedDirection = combinedDirection.combine(direction);
        }

        if(combinedDirection == Direction.NONE){
            return;
        }

        move = new Move<>(combinedDirection, Float.MAX_VALUE);
        movable.getScene().scheduleAction(move, movable);
    }

}
