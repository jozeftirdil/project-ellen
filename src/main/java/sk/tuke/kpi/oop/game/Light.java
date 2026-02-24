package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{

    private Animation offAnimation;

    private Animation onAnimation;

    private boolean is_on;

    private boolean electricity;

    public Light() {
        is_on = false;
        electricity = false;
        offAnimation = new Animation("sprites/light_off.png");
        onAnimation = new Animation("sprites/light_on.png");
        setAnimation(offAnimation);
    }

    /*public void setElectricityFlow(boolean electricityFlow){
        electricity = electricityFlow;
        updateAnimation();
    }*/

    private void updateAnimation(){
        if(is_on && electricity)
            setAnimation(onAnimation);
        else
            setAnimation(offAnimation);
    }

    public void toggle(){
        is_on = !is_on;
        updateAnimation();
    }


    @Override
    public void turnOn() {
        is_on = true;
        updateAnimation();
    }

    @Override
    public void turnOff() {
        is_on = false;
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return is_on;
    }

    @Override
    public void setPowered(boolean electricityFlow) {
        electricity = electricityFlow;
        updateAnimation();
    }
}
