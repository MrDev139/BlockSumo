package me.mrdev.bs.game;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.material.Dye;

public class GamePlayer {

    private Player player;
    private Player lastDamager;
    private ChatColor team;
    private int lives = 3; //default value


    public GamePlayer(Player player, ChatColor team, int lives) {
        this.player = player;
        this.team = team;
        this.lives = lives;
    }

    public GamePlayer(Player player, ChatColor team) {
        this.player = player;
        this.team = team;

    }

    public GamePlayer(Player player, int lives) {
        this.player = player;
        this.lives = lives;

    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void addLives(int lives) {
        this.lives += lives;
    }

    public void removeLives(int lives) {
        this.lives -= lives;
    }

    public Player getPlayer() {
        return player;
    }

    public ChatColor getTeam() {
        return team;
    }

    public void setTeam(ChatColor team) {
        this.team = team;
    }

    public Player getLastDamager() {
        return lastDamager;
    }

    public void setLastDamager(Player lastDamager) {
        this.lastDamager = lastDamager;
    }


}
