package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{

    private int temperature;

    private int damage;

    private boolean is_on;

    private Animation normalAnimation;

    private Animation offAnimation;

    private Animation hotAnimation;

    private Animation brokenAnimation;

    private Animation extinguishedAnimation;

    private Set<EnergyConsumer> devices;

    public Reactor(){
        is_on = false;
        temperature = 0;
        damage = 0;
        devices = new HashSet<>();

        // create animation object
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        offAnimation = new Animation("sprites/reactor.png");
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f);
        extinguishedAnimation = new Animation("sprites/reactor_extinguished.png");
        // set actor's animation to just created Animation object
        //setAnimation(offAnimation);
        updateAnimation();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

    public void addDevice(EnergyConsumer device){
        if (device == null)
            return;

        devices.add(device);
        device.setPowered(is_on);
    }

    public void removeDevice(EnergyConsumer device) {
        if (device == null)
            return;

        devices.remove(device);
        device.setPowered(false);
    }

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public int getTemperature(){
        return temperature;
    }

    @Override
    public boolean repair() {
        if(damage < 1 || damage > 99)
            return false;

        damage -= 50;
        int x = damage;

        if(damage < 0)
            damage = 0;

        int y = (4000*x)/100 + 2000;

        if( y < temperature)
            temperature = y;

        updateAnimation();

        return true;
    }


    public boolean extinguish(){

        if(damage != 100)
            return false;

        temperature = 4000;

        updateAnimation();

        return true;
    }

    @Override
    public void turnOn(){
        if(damage == 100){
            return;
        }
        is_on = true;
        updateAnimation();

        if (devices != null) {
            for (EnergyConsumer device : devices) {
                device.setPowered(is_on);
            }
        }
    }

    @Override
    public void turnOff(){
        is_on = false;
        updateAnimation();

        if (devices != null) {
            for (EnergyConsumer device : devices) {
                device.setPowered(is_on);
            }
        }
    }

    @Override
    public boolean isOn() {
        return is_on;
    }

    public void increaseTemperature(int increment){
        if(damage == 100)
            return;
        if(increment < 0)
            return;
        if(!is_on)
            return;

        if(damage < 33){
            temperature += increment;
        } else if (damage <= 66) {
            temperature += (int)Math.ceil(1.5*increment);
        } else {
            temperature += 2*increment;
        }

        if(temperature >= 6000) {
            is_on = false;
        }

        setDamage();
        updateAnimation();
    }

    public void decreaseTemperature(int decrement){
        if(damage == 100)
            return;
        if(decrement < 0)
            return;
        if(!is_on)
            return;

        if(damage < 50){
            temperature -= decrement;
        } else {
            temperature -= (int)Math.ceil(0.5*decrement);
        }

        if(temperature < 0)
            temperature = 0;

        updateAnimation();
    }

    private void updateAnimation(){
        frameDuration();

        if(is_on){
            if (temperature <= 4000) {
                setAnimation(normalAnimation);
            } else if (temperature < 6000) {
                setAnimation(hotAnimation);
            }
        } else {
            if(damage == 100 && temperature >= 6000){
                setAnimation(brokenAnimation);
            } else if (damage == 100 && temperature == 4000){
                setAnimation(extinguishedAnimation);
            } else {
                setAnimation(offAnimation);
            }
        }
    }

    private void setDamage(){
        if(temperature > 2000 && temperature < 6000) {
            damage = ((temperature - 2000) * 100) / 4000;
        } else if (temperature >= 6000){
            damage = 100;

            if (devices != null) {
                for (EnergyConsumer device : devices) {
                    device.setPowered(is_on);
                }
            }
        }
    }

    private void frameDuration(){
        if(damage == 0){
            normalAnimation.setFrameDuration(0.1f);
        } else if (damage < 50) {
            normalAnimation.setFrameDuration(0.075f);
        } else {
            normalAnimation.setFrameDuration(0.05f);
        }
    }

}
