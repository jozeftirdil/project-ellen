package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {

    private List<Collectible> items;

    private String name;

    private int capacity;

    public Backpack(String name, int capacity) {
        items = new ArrayList<>(capacity);
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return new ArrayList<>(items);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(getSize() < capacity) {
            items.add(actor);
        }else{
            throw new IllegalStateException(String.format("%s is full", name));
        }
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        items.remove(actor);
    }

    @Override
    public @Nullable Collectible peek() {
        if(items.isEmpty())
            return null;

        return items.get(items.size() - 1);
    }

    @Override
    public void shift() {
        if (items.size() > 1) {
            Collections.rotate(items, 1);
        }
    }

    @Override
    public @NotNull Iterator<Collectible> iterator() {
        return items.iterator();
    }
}
