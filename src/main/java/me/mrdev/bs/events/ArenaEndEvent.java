package me.mrdev.bs.events;

import me.mrdev.bs.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaEndEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private Arena arena;

    private Player winner;

    public ArenaEndEvent(Arena arena , Player winner) {
        this.arena = arena;
        this.winner = winner;
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

    public Player getWinner() {
        return winner;
    }

}
