package pl.bpol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.bpol.exception.NoSuchPlayerExeption;
import pl.bpol.model.Field;
import pl.bpol.model.Game;
import pl.bpol.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameService {

    @Autowired
    private PlayerService playerService;

    private List<Game> games;

    public GameService() {
        games = new ArrayList<>();
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game){
        games.add(game);
    }

    public void addGameByPlayerName(String name){
        Player playerByName = playerService.getPlayerByName(name);
        if(!playerByName.hasCreatedGame()){
            Game newGame = new Game(playerByName);
            newGame.setGameHostName(name);
            addGame(newGame);
            playerByName.setCreatedGame(true);
        }
    }

    public void checkHost(String name) {
        games.stream().filter(g -> g.getHost().getName().equals(name)).findFirst().orElseGet(null);
    }

    public Game getGameByHost(Player player){
        Game game = games.stream().filter(g -> g.getHost() == player).findFirst().orElseGet(null);
        if(game!=null){
            return game;
        } else {
            return null;
        }
    }

    public Game getGameByHostName(String name){
        return games.stream().filter( g -> g.getGameHostName().equals(name)).findFirst().orElseGet(null);
    }
    
    public Game getGameByGuestName(String name){
    	
    	List<Game> newGames = games.stream().filter(g -> g.getGuest()!=null).collect(Collectors.toList());
    	
    	return newGames.stream().filter(g -> g.getGuest().getName().equals(name)).findFirst().orElseGet(null);
    }

    public void setShipsLocationForHost(String[] positons, Game game){
        List<Integer> oneFieldShips = getOneFieldShips(positons);
        List<Integer> twoFieldsShips = getTwoFieldsShips(positons);
        List<Integer> threeFieldsShips = getThreeFieldsShips(positons);
        List<Integer> fourFieldsShip = getFourFieldsShip(positons);
        for(Integer oneShip:oneFieldShips){
            game.getHostShips().stream()
                    .filter(field -> field.getType().equals("oneFieldShip") && field.getLocation() == null)
                    .findFirst().ifPresent(field -> field.setLocation(positons[oneShip]));
        }
        for(Integer twoShip:twoFieldsShips){
            game.getHostShips().stream()
                    .filter(field -> (field.getType().equals("firstTwoFieldShip") || field.getType().equals("secondTwoFieldShip") || field.getType().equals("thirdTwoFieldShip")) && field.getLocation() == null)
                    .findFirst().ifPresent(field -> field.setLocation(positons[twoShip]));
        }
        for(Integer threeShip:threeFieldsShips){
            game.getHostShips().stream()
                    .filter(field -> (field.getType().equals("firstThreeFieldShip") || field.getType().equals("secondThreeFieldShip")) && field.getLocation() == null)
                    .findFirst().ifPresent(field -> field.setLocation(positons[threeShip]));
        }
        for(Integer fourShip:fourFieldsShip){
            game.getHostShips().stream()
                    .filter(field -> field.getType().equals("fourFieldShip") && field.getLocation() == null)
                    .findFirst().ifPresent(field -> field.setLocation(positons[fourShip]));
        }

    }

    public void setShipsLocationForGuest(String[] positons, Game game){
        List<Integer> oneFieldShips = getOneFieldShips(positons);
        List<Integer> twoFieldsShips = getTwoFieldsShips(positons);
        List<Integer> threeFieldsShips = getThreeFieldsShips(positons);
        List<Integer> fourFieldsShip = getFourFieldsShip(positons);
        for(Integer oneShip:oneFieldShips){
            game.getGuestShips().stream()
                    .filter(field -> field.getType().equals("oneFieldShip") && field.getLocation() == null)
                    .findFirst().ifPresent(field -> field.setLocation(positons[oneShip]));
        }
        for(Integer twoShip:twoFieldsShips){
            game.getGuestShips().stream()
            		.filter(field -> (field.getType().equals("firstTwoFieldShip") || field.getType().equals("secondTwoFieldShip") || field.getType().equals("thirdTwoFieldShip")) && field.getLocation() == null)
                    .findFirst().ifPresent(field -> field.setLocation(positons[twoShip]));
        }
        for(Integer threeShip:threeFieldsShips){
            game.getGuestShips().stream()
            		.filter(field -> (field.getType().equals("firstThreeFieldShip") || field.getType().equals("secondThreeFieldShip")) && field.getLocation() == null)
                    .findFirst().ifPresent(field -> field.setLocation(positons[threeShip]));
        }
        for(Integer fourShip:fourFieldsShip){
            game.getGuestShips().stream()
                    .filter(field -> field.getType().equals("fourFieldShip") && field.getLocation() == null)
                    .findFirst().ifPresent(field -> field.setLocation(positons[fourShip]));
        }

    }

    public char[] getColumns(String[] positions){
        char[] columns = new char[20];
        int i = 0;
        for(String position:positions){
            columns[i] = position.charAt(0);
            i++;
        }
        return columns;
    }

    public int[] getRows(String[] positions){
        int[] rows = new int[20];
        int i = 0;
        for(String position:positions){
            String substring = position.substring(1);
            rows[i] = Integer.valueOf(substring);
            i++;
        }
        return rows;
    }

    public boolean checkShipsLocations(String[] positions) {

        List<Integer> result = getOneFieldShips(positions);
        List<Integer> result2 = getTwoFieldsShips(positions);
        List<Integer> result3 = getThreeFieldsShips(positions);
        List<Integer> result4 = getFourFieldsShip(positions);

        if(result.size()==4&&result2.size()==6&&result3.size()==6&&result4.size()==4){
            return true;
        } else {
            return false;
        }
    }

    public List<Integer> getOneFieldShips(String[] positions){

        char[] columns = getColumns(positions);
        int[] rows = getRows(positions);

        List<Integer> indexesOfShips = new ArrayList<>();

        for(int i=0;i<20;i++){
            char checkCol = columns[i];
            int checkRow = rows[i];
            boolean result=false;
            for(int j=0;j<20;j++){
                if(i==j){
                    continue;
                }
                if((checkCol==columns[j]||checkCol==columns[j]-1||checkCol==columns[j]+1)
                    &&(checkRow==rows[j]||checkRow==rows[j]-1||checkRow==rows[j]+1)){
                    result=false;
                    break;
                } else {
                    result=true;
                }
            }
            if(result) {
                indexesOfShips.add(i);
            }
        }
        return indexesOfShips;
    }

    private List<Integer> getTwoFieldsShips(String[] positions){

        char[] columns = getColumns(positions);
        int[] rows = getRows(positions);

        List<Integer> indexesOfShips = new ArrayList<>();

        for(int i=0;i<20;i++){
            boolean fieldAlreadyChecked = false;
            for(int index:indexesOfShips){
                if(i==index){
                    fieldAlreadyChecked = true;
                }
            }
            if(fieldAlreadyChecked){
                continue;
            }
            char checkCol = columns[i];
            int checkRow = rows[i];
            boolean result=false;
            for(int j=0;j<20;j++){
                if(i==j){
                    continue;
                }
                if(((checkCol==columns[j]-1||checkCol==columns[j]+1)&&checkRow==rows[j])||
                        ((checkRow==rows[j]-1||checkRow==rows[j]+1)&&checkCol==columns[j])){
                    char newCheckCol = columns[j];
                    int newCheckRow = rows[j];

                    for(int k=0;k<20;k++){
                        if(k==i||k==j){
                            continue;
                        }
                        if((checkCol==columns[k]||checkCol==columns[k]-1||checkCol==columns[k]+1)
                                &&(checkRow==rows[k]||checkRow==rows[k]-1||checkRow==rows[k]+1)){
                            result = false;
                            break;
                        } else if((newCheckCol==columns[k]||newCheckCol==columns[k]-1||newCheckCol==columns[k]+1)
                                &&(newCheckRow==rows[k]||newCheckRow==rows[k]-1||newCheckRow==rows[k]+1)){
                            result = false;
                            break;
                        } else {
                            result = true;
                        }
                    }
                    if(result){
                        indexesOfShips.add(i);
                        indexesOfShips.add(j);
                    }

                }

            }
        }
        return indexesOfShips;
    }

    private List<Integer> getThreeFieldsShips(String[] positions){
        char[] columns = getColumns(positions);
        int[] rows = getRows(positions);
        List<Integer> indexesOfShips = new ArrayList<>();
        for(int i=0;i<20;i++){
            char baseCol = columns[i];
            int baseRow = rows[i];
            int indexOfFirstFoundField=100;
            int indexOfSecondFoundField=100;
            boolean result = false;
            for(int j=0;j<20;j++){
                if(j==i){
                    continue;
                }
                if(((baseCol==columns[j]-1||baseCol==columns[j]+1)&&baseRow==rows[j])||
                        ((baseRow==rows[j]-1||baseRow==rows[j]+1)&&baseCol==columns[j])){
                    if(indexOfFirstFoundField==100){
                        indexOfFirstFoundField=j;
                    } else if(indexOfSecondFoundField==100) {
                        indexOfSecondFoundField=j;
                    } else {
                        indexOfFirstFoundField=100;
                        indexOfSecondFoundField=100;
                        break;
                    }
                }
            }
            if(indexOfFirstFoundField!=100&&indexOfSecondFoundField!=100){
                for(int k=0;k<20;k++){
                    if(k==i||k==indexOfFirstFoundField||k==indexOfSecondFoundField){
                        continue;
                    }
                    if((baseCol==columns[k]||baseCol==columns[k]-1||baseCol==columns[k]+1)
                            &&(baseRow==rows[k]||baseRow==rows[k]-1||baseRow==rows[k]+1)){
                        result = false;
                        break;
                    } else if((columns[indexOfFirstFoundField]==columns[k]||columns[indexOfFirstFoundField]==columns[k]-1||columns[indexOfFirstFoundField]==columns[k]+1)
                            &&(rows[indexOfFirstFoundField]==rows[k]||rows[indexOfFirstFoundField]==rows[k]-1||rows[indexOfFirstFoundField]==rows[k]+1)){
                        result = false;
                        break;
                    } else if((columns[indexOfSecondFoundField]==columns[k]||columns[indexOfSecondFoundField]==columns[k]-1||columns[indexOfSecondFoundField]==columns[k]+1)
                            &&(rows[indexOfSecondFoundField]==rows[k]||rows[indexOfSecondFoundField]==rows[k]-1||rows[indexOfSecondFoundField]==rows[k]+1)){
                        result = false;
                        break;
                    } else {
                        result = true;
                    }
                }
            }
            if(result){
                indexesOfShips.add(i);
                indexesOfShips.add(indexOfFirstFoundField);
                indexesOfShips.add(indexOfSecondFoundField);
            }
        }
        return indexesOfShips;
    }

    private List<Integer> getFourFieldsShip(String[] positions){
        char[] columns = getColumns(positions);
        int[] rows = getRows(positions);
        List<Integer> indexesOfFourFieldShip = new ArrayList<>();
        boolean result = false;
        for (int i=0;i<20;i++){

            char baseCol = columns[i];
            int baseRow = rows[i];
            for(int j=0;j<20;j++){
                if(i==j){
                    continue;
                }
                if(((baseCol==columns[j]-1||baseCol==columns[j]+1)&&baseRow==rows[j])||
                        ((baseRow==rows[j]-1||baseRow==rows[j]+1)&&baseCol==columns[j])){
                    baseCol=columns[j];
                    baseRow=rows[j];
                    for(int k=0;k<20;k++){
                        if(k==j||k==i){
                            continue;
                        }
                        if(((baseCol==columns[k]-1||baseCol==columns[k]+1)&&baseRow==rows[k])||
                                ((baseRow==rows[k]-1||baseRow==rows[k]+1)&&baseCol==columns[k])){
                            baseCol=columns[k];
                            baseRow=rows[k];
                            for(int l=0;l<20;l++){
                                if(l==i||l==j||l==k){
                                    continue;
                                }
                                if(((baseCol==columns[l]-1||baseCol==columns[l]+1)&&baseRow==rows[l])||
                                        ((baseRow==rows[l]-1||baseRow==rows[l]+1)&&baseCol==columns[l])){
                                    for(int m=0;m<20;m++){
                                        if(i==m||j==m||k==m||l==m){
                                            continue;
                                        }
                                        if((columns[i]==columns[m]||columns[i]==columns[m]-1||columns[i]==columns[m]+1)
                                                    &&(rows[i]==rows[m]||rows[i]==rows[m]-1||rows[i]==rows[m]+1)){
                                                result = false;
                                                break;
                                        }
                                        if((columns[j]==columns[m]||columns[j]==columns[m]-1||columns[j]==columns[m]+1)
                                                &&(rows[j]==rows[m]||rows[j]==rows[m]-1||rows[j]==rows[m]+1)){
                                            result = false;
                                            break;
                                        }
                                        if((columns[k]==columns[m]||columns[k]==columns[m]-1||columns[k]==columns[m]+1)
                                                &&(rows[k]==rows[m]||rows[k]==rows[m]-1||rows[k]==rows[m]+1)){
                                            result = false;
                                            break;
                                        }
                                        if((columns[l]==columns[m]||columns[l]==columns[m]-1||columns[l]==columns[m]+1)
                                                &&(rows[l]==rows[m]||rows[l]==rows[m]-1||rows[l]==rows[m]+1)){
                                            result = false;
                                            break;
                                        } else {
                                            result = true;
                                        }

                                    }
                                    if(result){
                                        indexesOfFourFieldShip.add(i);
                                        indexesOfFourFieldShip.add(j);
                                        indexesOfFourFieldShip.add(k);
                                        indexesOfFourFieldShip.add(l);
                                        break;
                                    }
                                }
                            }
                        }
                        if(result){
                            break;
                        }
                    }
                }
                if(result){
                    break;
                }
            }
            if(result){
                break;
            }
        }
        return indexesOfFourFieldShip;
    }

	public String executeShot(String fromPlayer, String target) throws NoSuchPlayerExeption {
		
		Game game = this.games.stream().filter(g -> g.getHost().getName().equals(fromPlayer)).findFirst().orElse(null);
		if(game!=null) {
			String reply = findGuestField(game,target);
			if(reply.equals("mishit")) {
				game.changeTurn();	
			} else if(reply.endsWith("victory")) {
				games.remove(game);
			}
			return reply;
		} else {
			game = this.games.stream().filter(g -> g.getGuest().getName().equals(fromPlayer)).findFirst().orElse(null);
		}
		if(game==null) {
			System.err.println("There is no game with that host/guest.");
			throw new NoSuchPlayerExeption();
		} else {
			String reply = findHostField(game,target);
			if(reply.equals("mishit")) {
				game.changeTurn();	
			}else if(reply.endsWith("victory")) {
				games.remove(game);
			}
			return reply;
		}
	}

	private String findGuestField(Game game, String target) {
		List<Field> ships = game.getGuestShips();
		return checkFields(ships, target);
	}

	private String findHostField(Game game, String target) {
		List<Field> ships = game.getHostShips();
		return checkFields(ships, target);
		
	}
	
	private String checkFields(List<Field> ships, String target){
		for (Field field : ships) {
			if(target.equals(field.getLocation())){
				if(!field.isHit()){
					field.setHit(true);
					
					if(isShipIsSunk(field, ships)) {
						String type = field.getType();
						if(type.equals("oneFieldShip")) {
							
							if(isItEnd(ships)) {
								return "hit "+field.getLocation()+" victory";
							}
							return "hit "+field.getLocation();
							
						} else {
							
							String sunkFields = "";
							for(Field ship : ships) {
								if(ship.getType().equals(type)) {
									sunkFields += ship.getLocation()+",";
								}
							}
							sunkFields = sunkFields.substring(0, sunkFields.length()-1);
							if(isItEnd(ships)) {
								return "hit "+sunkFields+" victory";
							}
							return "hit "+sunkFields;
						}
						
					} else {
						return "hit";
					}
					
				} else {
					return "error";
				}
				
			}
		}
		return "mishit";
	}

	private boolean isItEnd(List<Field> ships) {
		for(Field field2 : ships) {
			if(!field2.isHit()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isShipIsSunk(Field field, List<Field> ships) {
		if(field.getType().equals("oneFieldShip")){
			return true;
		}else {
			List<Field> ship = new ArrayList<>();
			ships.stream().filter(s -> s.getType().equals(field.getType())).forEach(ship::add);
			for (Field shipField : ship) {
				if(!shipField.isHit()) {
					return false;
				}
			}
			return true;
		}
	}

	public boolean checkBothPlayersAreAdded(String gameName) {
		Game game;
		game = games.stream().filter(g -> gameName.equals(g.getGameHostName())).findFirst().orElse(null);
		if(game!=null) {
			if(game.getHost()!=null&&game.getGuest()!=null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String whoseTurn(String gameName) {
		return games.stream().filter(g -> g.getGameHostName().equals(gameName)).findFirst().get().getPlayersTurn();
	}

	public void deleteGame(Game game) {
		
		this.games.removeIf(g -> g.equals(game));
		
	}
    
    

}
