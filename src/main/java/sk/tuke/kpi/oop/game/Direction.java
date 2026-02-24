package sk.tuke.kpi.oop.game;

public enum Direction {

    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTHWEST(-1, 1),
    NORTHEAST(1, 1),
    SOUTHWEST(-1, -1),
    SOUTHEAST(1, -1),
    NONE(0,0);


    private int dx;
    private int dy;

    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public float getAngle(){

        switch (this) {
            case NORTH: return 0;
            case EAST: return 270;
            case SOUTH: return 180;
            case WEST: return 90;
            case NORTHEAST: return 315;
            case NORTHWEST: return 45;
            case SOUTHEAST: return 225;
            case SOUTHWEST: return 135;
            default: return -1;
        }
    }

    public Direction combine(Direction other){

        int newDx = this.dx;
        int newDy = this.dy;

        if(newDx != other.dx){
            newDx += other.dx;
        }

        if(newDy != other.dy){
            newDy += other.dy;
        }

        Direction[] values = Direction.values();
        for(int i = 0; i < values.length; i++){
            Direction direction = values[i];
            if(direction.getDx() == newDx && direction.getDy() == newDy){
                return direction;
            }
        }

        return NONE;
    }

    public static Direction fromAngle(float angle) {

        switch ((int) angle) {
            case 0: return NORTH;
            case 270: return EAST;
            case 180: return SOUTH;
            case 90: return WEST;
            case 315: return NORTHEAST;
            case 45: return NORTHWEST;
            case 225: return SOUTHEAST;
            case 135: return SOUTHWEST;
            default: return NONE;
        }
    }


}
