package me.mrdev.bs.events;

import me.mrdev.bs.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaJoinEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private Player joiner;
    private Arena arena;
    private boolean cancelled;

    public ArenaJoinEvent(Player joiner , Arena arena) {
        this.joiner = joiner;
        this.arena = arena;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getJoiner() {
        return joiner;
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}

