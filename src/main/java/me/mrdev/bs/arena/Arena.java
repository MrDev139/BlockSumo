package me.mrdev.bs.arena;

import me.mrdev.bs.game.GamePlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Arena implements Cloneable {

    private String name; //save
    private UUID ID; //save
    private int min; //save
    private int max; //save
    private ArrayList<GamePlayer> players = new ArrayList<>();
    private ArrayList<GamePlayer> spectators = new ArrayList<>();
    private ArrayList<Location> spawns; //save
    private Location lobby; //save
    private Location spectatorloc; //save
    private int lives;

    private ArenaState state = ArenaState.WAITING;

    public Arena(String name, UUID ID, int min, int max, ArrayList<Location> spawns, Location lobby, Location spectatorloc , int lives) {
        this.name = name;
        this.ID = ID;
        this.min = min;
        this.max = max;
        this.spawns = spawns;
        this.lobby = lobby;
        this.spectatorloc = spectatorloc;
        this.lives = lives;
    }

    public Arena(String name , UUID ID) {
        this.name = name;
        this.ID = ID;
    }

    public Arena(Arena arena) {
        this.name = arena.name;
        this.ID = arena.ID;
        this.min = arena.min;
        this.max = arena.max;
        this.spawns = arena.spawns;
        this.lobby = arena.lobby;
        this.spectatorloc = arena.spectatorloc;
        this.players = arena.players;
        this.spectators = arena.spectators;
        this.state = arena.state;
        this.lives = arena.lives;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getID() {
        return ID;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public ArrayList<GamePlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<GamePlayer> players) {
        this.players = players;
    }

    public ArrayList<GamePlayer> getSpectators() {
        return spectators;
    }

    public void setSpectators(ArrayList<GamePlayer> spectators) {
        this.spectators = spectators;
    }

    public ArrayList<Location> getSpawns() {
        return spawns;
    }

    public void setSpawns(ArrayList<Location> spawns) {
        this.spawns = spawns;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public Location getSpectatorloc() {
        return spectatorloc;
    }

    public void setSpectatorloc(Location spectatorloc) {
        this.spectatorloc = spectatorloc;
    }

    public ArenaState getState() {
        return state;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public boolean isFull() {
        return players.size() >= max;
    }

    public GamePlayer getGamePlayer(Player player) {
        return players.stream().filter(p -> p.getPlayer().getUniqueId() == player.getUniqueId()).findAny().orElse(null);
    }

    public GamePlayer getGamePlayer(UUID id) {
        return players.stream().filter(p -> p.getPlayer().getUniqueId() == id).findAny().orElse(null);
    }

    @Override
    public Arena clone() throws CloneNotSupportedException {
        return (Arena) super.clone();
    }

    @Override
    public String toString() {
        return "Arena{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                ", min=" + min +
                ", max=" + max +
                ", players=" + players.size() +
                ", spectators=" + spectators.size() +
                ", spawns=" + spawns.size() +
                ", lobby=" + lobby +
                ", spectatorloc=" + spectatorloc +
                ", state=" + state.toString().toLowerCase() +
                ", lives=" + lives +
                '}';
    }
}
