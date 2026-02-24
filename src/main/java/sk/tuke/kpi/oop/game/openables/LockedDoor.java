package sk.tuke.kpi.oop.game.openables;

public class LockedDoor extends Door{

    private boolean is_locked;

    public LockedDoor() {
        super(Orientation.HORIZONTAL);
        is_locked = true;
    }

    public void lock() {
        is_locked = true;
        close();
    }

    public void unlock() {
        is_locked = false;
        open();
    }

    public boolean isLocked() {
        return is_locked;
    }

    @Override
    public void open() {
        if(!is_locked)
            super.open();
    }
}
