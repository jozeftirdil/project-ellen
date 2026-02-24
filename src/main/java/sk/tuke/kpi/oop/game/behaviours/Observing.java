package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.function.Predicate;

public class Observing <A extends Actor, T> implements Behaviour<A> {

    private Topic<T> topic;
    private Predicate<T> predicate;
    private Behaviour<A> delegate;

    public Observing(Topic<T> topic, Predicate<T> predicate, Behaviour<A> delegate) {
        this.topic = topic;
        this.predicate = predicate;
        this.delegate = delegate;
    }

    @Override
    public void setUp(A actor) {
        if(actor == null)
            return;

        actor.getScene().getMessageBus().subscribe(topic, topic1 -> {
            if(predicate.test(topic1)){
                delegate.setUp(actor);
            }
        });
    }
}
