package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm{

    public Gun(int actualAmmo, int maxAmmo) {
        super(actualAmmo, maxAmmo);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
