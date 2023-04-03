package me.mrdev.bs.events;

import me.mrdev.bs.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaChatEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private Arena arena;
    private boolean isCancelled;

    private Player player;
    private String message;

    public ArenaChatEvent(Arena arena , Player player, String message) {
        this.arena = arena;
        this.player = player;
        this.message = message;
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

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
