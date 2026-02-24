package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl2.Lwjgl2Backend;
//import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
//import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;

public class Main {

    public static void main(String[] args) {

        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania `Game` triedou `GameApplication`
        Game game = new GameApplication(windowSetup, new Lwjgl2Backend());  // v pripade Mac OS bude druhy parameter "new Lwjgl2Backend()"

        //Scene scene = new World("world");
        //Scene scene = new World("world", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        Scene scene = new World("world", "maps/escape-room.tmx", new EscapeRoom.Factory());

        //scene.addListener(new FirstSteps());
        //scene.addListener(new MissionImpossible());
        scene.addListener(new EscapeRoom());

        game.addScene(scene);

        game.getInput().onKeyPressed(Input.Key.ESCAPE, () -> {
            game.stop();
        });

        game.start();
    }
}
