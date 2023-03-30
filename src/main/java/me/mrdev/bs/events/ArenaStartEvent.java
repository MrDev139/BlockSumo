package me.mrdev.bs.events;

import me.mrdev.bs.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaStartEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private Arena arena;

    public ArenaStartEvent(Arena arena) {
        this.arena = arena;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Arena getArena() {
        return arena;
    }

}

