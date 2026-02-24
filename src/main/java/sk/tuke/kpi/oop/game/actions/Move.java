package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Move<A extends Movable> implements Action<A> {

    private Direction direction;

    private float duration;

    private A movable;

    private boolean executed;

    private boolean firstTime;

    private float wholeTime;

    public Move(Direction direction, float duration) {

        this.direction = direction;
        this.duration = duration;
        executed = false;
        firstTime = true;
        wholeTime = 0;
    }

    public Move(Direction direction) {
        //executed = false;
        this(direction, 0);
    }

    @Override
    public @Nullable A getActor() {
        return movable;
    }

    @Override
    public void setActor(@Nullable A actor) {
        this.movable  = actor;
    }

    @Override
    public boolean isDone() {
        return executed;
    }

    @Override
    public void execute(float deltaTime) {
        if(isDone()) //
            return;

        if(firstTime){
            firstTime = false;
            movable.startedMoving(direction);
        }

        if(movable == null)
            return;

        int oldPosX = movable.getPosX();
        int oldPosY = movable.getPosY();

        movable.setPosition(movable.getPosX() + (movable.getSpeed() * direction.getDx()),
            movable.getPosY() + (movable.getSpeed() * direction.getDy()));

        if(movable.getScene().getMap().intersectsWithWall(movable)){
            movable.setPosition(oldPosX, oldPosY);
            movable.collidedWithWall();
            stop();
        }

        wholeTime += deltaTime;

        if((wholeTime >= duration)){
            stop();
        }
    }

    public void stop(){

        if(executed) //
            return;

        executed = true;
        if(movable != null)
            movable.stoppedMoving();
    }

    @Override
    public void reset() {
        executed = false;
        firstTime = true;
        wholeTime = 0;
    }
}
