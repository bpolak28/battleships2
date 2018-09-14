package pl.bpol.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String gameHostName;

    private Player host;

    private List<Field> hostShips = new ArrayList<>();

    private Player guest;

    private List<Field> guestShips = new ArrayList<>();

    public Game(){

    }

    public Game(Player host, List<Field> hostShips, Player guest, List<Field> guestShips) {
        this.host = host;
        this.hostShips = hostShips;
        this.guest = guest;
        this.guestShips = guestShips;
    }

    public String getGameHostName() {
        return gameHostName;
    }

    public void setGameHostName(String gameHostName) {
        this.gameHostName = gameHostName;
    }

    public List<Field> getHostShips() {
        return hostShips;
    }

    public void setHostShips(List<Field> hostShips) {

        this.hostShips = createShips(hostShips);
    }

    public List<Field> getGuestShips() {
        return guestShips;
    }

    public void setGuestShips(List<Field> guestShips) {
        this.guestShips = createShips(guestShips);
    }

    public Game(Player host) {
        this.host = host;
        setHostShips(hostShips);
        setGuestShips(guestShips);
    }

    public Player getHost() {
        return host;
    }

    public void setHost(Player host) {
        this.host = host;
    }

    public Player getGuest() {
        return guest;
    }

    public void setGuest(Player guest) {
        this.guest = guest;
    }

    @Override
    public String toString() {
        return "Game{" +
                "host=" + host +
                ", guest=" + guest +
                '}';
    }

    private List<Field> createShips(List<Field> ships){
        ships.add(new Field("oneFieldShip"));
        ships.add(new Field("oneFieldShip"));
        ships.add(new Field("oneFieldShip"));
        ships.add(new Field("oneFieldShip"));
        ships.add(new Field("firstTwoFieldShip"));
        ships.add(new Field("firstTwoFieldShip"));
        ships.add(new Field("secondTwoFieldShip"));
        ships.add(new Field("secondTwoFieldShip"));
        ships.add(new Field("thirdTwoFieldShip"));
        ships.add(new Field("thirdTwoFieldShip"));
        ships.add(new Field("firstThreeFieldShip"));
        ships.add(new Field("firstThreeFieldShip"));
        ships.add(new Field("firstThreeFieldShip"));
        ships.add(new Field("secondThreeFieldShip"));
        ships.add(new Field("secondThreeFieldShip"));
        ships.add(new Field("secondThreeFieldShip"));
        ships.add(new Field("fourFieldShip"));
        ships.add(new Field("fourFieldShip"));
        ships.add(new Field("fourFieldShip"));
        ships.add(new Field("fourFieldShip"));
        return ships;
    }
}