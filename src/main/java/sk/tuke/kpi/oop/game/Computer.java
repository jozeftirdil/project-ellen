package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {

    private boolean electricity;

    public Computer(){
        electricity = false;
        Animation normalAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f);
        setAnimation(normalAnimation);
        getAnimation().stop();
    }

    public int add(int x, int y){
        if(!electricity)
            return 0;

        return x + y;
    }

    public int sub(int x, int y){
        if(!electricity)
            return 0;

        return x - y;
    }

    public float add(float x, float y){
        if(!electricity)
            return 0;

        return x + y;
    }

    public float sub(float x, float y){
        if(!electricity)
            return 0;

        return x - y;
    }

    @Override
    public void setPowered(boolean electricityFlow) {
        electricity = electricityFlow;

        if(electricity == true) {
            getAnimation().play();
        } else {
            getAnimation().stop();
        }
    }
}
