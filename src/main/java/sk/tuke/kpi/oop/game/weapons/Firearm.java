package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {

    private int actualAmmo;
    private int maxAmmo;

    public Firearm(int actualAmmo, int maxAmmo ) {
        this.actualAmmo = actualAmmo;
        this.maxAmmo = maxAmmo;
    }

    public Firearm(int actualMaxAmmo) {
        this.actualAmmo = actualMaxAmmo;
        this.maxAmmo = actualMaxAmmo;
    }

    public int getAmmo() {
        return actualAmmo;
    }

    public void reload(int newAmmo) {
        actualAmmo += newAmmo;

        if(actualAmmo > maxAmmo) {
            actualAmmo = maxAmmo;
        }
    }

    public Fireable fire() {
        if(actualAmmo == 0){
            return null;
        }else {
            actualAmmo -= 1;
            return createBullet();
        }
    }

    protected abstract Fireable createBullet();

}
