package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable {

    private boolean isFlickering;

    private Disposable light;

    public DefectiveLight() {
        super();
        isFlickering = true;
    }

    private void flicker() {
        int randomNumber = (int) (Math.random() * 21);

        if (randomNumber == 1) {
            if (isOn()) {
                turnOff();
            } else {
                turnOn();
            }
        }
    }

    private void lightFlickering(){
        isFlickering = true;
        light = new Loop<>(new Invoke<>(this::flicker)).scheduleFor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        light = new Loop<>(new Invoke<>(this::flicker)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if(!isFlickering)
            return false;

        isFlickering = false;
        light.dispose();

        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(this::lightFlickering)
        ).scheduleFor(this);

        return true;
    }
}
