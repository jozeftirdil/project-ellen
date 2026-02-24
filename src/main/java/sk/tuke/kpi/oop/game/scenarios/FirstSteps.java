package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Wrench;

public class FirstSteps implements SceneListener {

    private Ripley ripley;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = new Ripley();
        scene.addActor(ripley, 0, 0);

        Energy energy = new Energy();
        scene.addActor(energy, 100,100);

        scene.getInput().registerListener(new MovableController(ripley));
        scene.getInput().registerListener(new KeeperController(ripley));

        scene.scheduleAction(new Use<>(energy), ripley);

        scene.getGame().pushActorContainer(ripley.getBackpack());

        ripley.getBackpack().add(new Wrench());
        ripley.getBackpack().add(new FireExtinguisher());
        ripley.getBackpack().add(new Hammer());

        scene.addActor(new Hammer(), 200, 200);
        scene.addActor(new Hammer(), 250, 220);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        if(ripley != null) {
            Overlay overlay = scene.getGame().getOverlay();

            int windowHeight = scene.getGame().getWindowSetup().getHeight();
            int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;

            overlay.drawText(String.format("Energy: %d  |  Ammo: %d", ripley.getHealth().getValue(), ripley.getAmmo()), 150, yTextPos);
        }
    }
}
