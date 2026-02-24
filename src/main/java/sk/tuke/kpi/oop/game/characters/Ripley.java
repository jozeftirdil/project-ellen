package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {

    private int ammo;
    private Backpack backpack;
    private Animation dead;
    private Health health;
    private Firearm firearm;

    public Ripley() {
        super("Ellen");
        health = new Health(100);
        Animation animation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        dead = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(animation);
        animation.stop(); // Extra
        backpack = new Backpack("Ripley's backpack", 10);
        firearm = new Gun(100, 100);
        ammo = 0;

        health.onFatigued(() -> {
            setAnimation(dead);
            getAnimation().play();

            getScene().getMessageBus().publish(RIPLEY_DIED, this);
            getScene().cancelActions(this);
        });
    }

    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("Ripley died", Ripley.class);

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    @Override
    public int getSpeed() {
        return 2;
    }


    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
    }

    @Override
    public void stoppedMoving() {
        getAnimation().stop();
    }

    @Override
    public Backpack getBackpack() {
        return backpack;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    public void showRipleyState(Scene scene, Ripley ripley) {
        if(scene != null) {
            Overlay overlay = scene.getGame().getOverlay();

            int windowHeight = scene.getGame().getWindowSetup().getHeight();
            int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;

            overlay.drawText(String.format("Energy: %d  |  Ammo: %d", ripley.getHealth().getValue(), ripley.getAmmo()), 150, yTextPos);
        }
    }

    @Override
    public Firearm getFirearm() {
        return firearm;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        firearm = weapon;
    }
}
