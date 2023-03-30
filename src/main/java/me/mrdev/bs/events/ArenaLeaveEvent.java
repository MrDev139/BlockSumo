package me.mrdev.bs.events;

import me.mrdev.bs.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaLeaveEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private Player player;
    private Arena arena;

    private boolean disconnected;
    private boolean cancelled;

    public ArenaLeaveEvent(Player player , Arena arena , boolean disconnected) {
        this.player = player;
        this.arena = arena;
        this.disconnected = disconnected;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public Arena getArena() {
        return arena;
    }

    public boolean isDisconnected() {
        return disconnected;
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

