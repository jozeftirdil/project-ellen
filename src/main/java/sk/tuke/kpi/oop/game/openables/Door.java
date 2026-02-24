package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Usable<Actor>, Openable {

    private boolean is_open;

    private boolean vertical;
    private boolean horizontal;

    private Animation animation;

    public enum Orientation { VERTICAL, HORIZONTAL };



    public Door(Orientation orientation) {
        is_open = false;
        if(orientation == Orientation.VERTICAL) {
            animation = new Animation("sprites/vdoor.png", 16, 32, 0.1f);
            vertical = true;
            horizontal = false;
        }else if(orientation == Orientation.HORIZONTAL) {
            animation = new Animation("sprites/hdoor.png", 32, 16, 0.1f);
            vertical = false;
            horizontal = true;
        }
        setAnimation(animation);
        animation.stop();
    }

    public Door(String name, Orientation orientation) {
        super(name);

        is_open = false;
        if(orientation == Orientation.VERTICAL) {
            animation = new Animation("sprites/vdoor.png", 16, 32, 0.1f);
            vertical = true;
            horizontal = false;
        }else if(orientation == Orientation.HORIZONTAL) {
            animation = new Animation("sprites/hdoor.png", 32, 16, 0.1f);
            vertical = false;
            horizontal = true;
        }
        setAnimation(animation);
        animation.stop();
    }

    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);

    @Override
    public void useWith(Actor actor) {
        if (actor == null)
            return;

        if(is_open) {
            close();
        }else {
            open();
        }
    }

    @Override
    public void open() {
        is_open = true;
        animation.setPlayMode(Animation.PlayMode.ONCE);
        animation.resetToFirstFrame();
        animation.play();
        makeWall();
        super.getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        is_open = false;
        animation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        animation.resetToFirstFrame();
        animation.play();
        makeWall();
        super.getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return is_open;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        makeWall();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    private void makeWall(){

        if(vertical && !horizontal) {
            verticalWall();
        }else if(!vertical && horizontal) {
            horizontalWall();
        }

    }

    private void verticalWall() {

        int doorTileX = (int)(super.getPosX() / 16);
        int doorTileY = (int)(super.getPosY() / 16);


        MapTile bottomTile = getScene().getMap().getTile(doorTileX, doorTileY);
        MapTile topTile = getScene().getMap().getTile(doorTileX, doorTileY + 1);

        if(bottomTile != null && topTile != null){
            if(is_open){
                if(bottomTile.isWall())
                    bottomTile.setType(MapTile.Type.CLEAR);

                if(topTile.isWall())
                    topTile.setType(MapTile.Type.CLEAR);
            }else {
                if(!bottomTile.isWall())
                    bottomTile.setType(MapTile.Type.WALL);

                if(!topTile.isWall())
                    topTile.setType(MapTile.Type.WALL);
            }
        }
    }

    private void horizontalWall() {

        int doorTileX = (int)(super.getPosX() / 16);
        int doorTileY = (int)(super.getPosY() / 16);


        MapTile leftTile = getScene().getMap().getTile(doorTileX, doorTileY);
        MapTile rightTile = getScene().getMap().getTile(doorTileX + 1, doorTileY);

        if(leftTile != null && rightTile != null){
            if(is_open){
                if(leftTile.isWall())
                    leftTile.setType(MapTile.Type.CLEAR);

                if(rightTile.isWall())
                    rightTile.setType(MapTile.Type.CLEAR);
            }else {
                if(!leftTile.isWall())
                    leftTile.setType(MapTile.Type.WALL);

                if(!rightTile.isWall())
                    rightTile.setType(MapTile.Type.WALL);
            }
        }
    }
}
