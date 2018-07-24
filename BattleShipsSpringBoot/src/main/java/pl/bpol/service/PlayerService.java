package pl.bpol.service;

import pl.bpol.model.Player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PlayerService {

    private List<Player> players;

    public PlayerService(){
        this.players = new ArrayList<>();
    }

    public Player createPlayer(String name){
        Player player = new Player(name);
        this.players.add(player);
        return player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getPlayerByName(String name){
        return this.players.stream().filter(p -> p.getName().equals(name)).findFirst().orElseGet(null);
    }
}
