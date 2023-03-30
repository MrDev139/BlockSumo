package me.mrdev.bs.events;

import me.mrdev.bs.arena.Arena;
import me.mrdev.bs.game.GameArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class GamePlayerDeath extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private GameArena arena;

    private Player spectator;

    public GamePlayerDeath(GameArena arena , Player spectator) {
        this.arena = arena;
        this.spectator = spectator;
    }

    public Player getSpectator() {
        return spectator;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public GameArena getArena() {
        return arena;
    }

}
