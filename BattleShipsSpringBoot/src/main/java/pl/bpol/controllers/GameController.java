package pl.bpol.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import pl.bpol.form.FieldsForm;
import pl.bpol.model.Game;
import pl.bpol.model.OutputMessage;
import pl.bpol.model.WebSocketMessage;
import pl.bpol.service.GameService;
import pl.bpol.service.PlayerService;

@Controller
@SessionAttributes({ "name", "gameName" })
public class GameController {

	private GameService gameService;

	@Autowired
	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	private PlayerService playerService;

	@Autowired
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	@ModelAttribute("fieldsForm")
	public FieldsForm construct() {
		return new FieldsForm();
	}

	@RequestMapping(value = "game/{gameName}")
	public String enterGame(ModelMap modelMap, @PathVariable("gameName") String gameName,
			@ModelAttribute("name") String name) {
		modelMap.addAttribute("gameName", gameName);
		Game game = gameService.getGameByHostName(gameName);
		if (!game.getGameHostName().equals(name)) {
			if (game.getGuest() == null) {
				game.setGuest(playerService.getPlayerByName(name));
			}
		}
		return "game";
	}

	@RequestMapping(value = "battle/{gameName}", method = RequestMethod.POST)
	public String sendForm(ModelMap modelMap, @ModelAttribute FieldsForm fieldsForm) {
		List<String> positionsList = fieldsForm.getPositions();
		String[] positions = positionsList.toArray(new String[0]);
		// if(positions.length>20){
		// modelMap.addAttribute("wrongNumberOfChecks","Zaznaczono zbyt wiele pól");
		// } else if (positions.length<20){
		// modelMap.addAttribute("wrongNumberOfChecks","Zaznaczono zbyt mało pól");
		// } else {
		// if(gameService.checkShipsLocations(positions)) {
		// if
		// (modelMap.get("name").toString().equals(modelMap.get("gameName").toString()))
		// {
		// System.out.println("git");
		// gameService.setShipsLocationForHost(positions,gameService.getGameByHostName(modelMap.get("name").toString()));
		// modelMap.addAttribute("hostShips",gameService.getGameByHostName(modelMap.get("name").toString()).getHostShips());
		// return "battle";
		// } else {
		// gameService.setShipsLocationForGuest(positions,gameService.getGameByHostName(modelMap.get("gameName").toString()));
		// modelMap.addAttribute("guestShips",gameService.getGameByHostName(modelMap.get("gameName").toString()).getHostShips());
		// return "battle";
		// }
		// }
		// else {
		// modelMap.addAttribute("wrongPlacement", "Błędne rozmieszczenie statków.
		// Pomiędzy każdym statkiem powinno znajdować się jedno pole odstępu.");
		// }
		// }

		System.out.println(positionsList);
		// return "game";
		return "battle";
	}

	@RequestMapping(value = "game/battle", method = RequestMethod.GET)
	public String hitAction(ModelMap modelMap, @RequestParam("target") String target) {
		System.out.println(target);
		return "battle";
	}

	@MessageMapping("/chat")
	@SendTo("/topic")
	public OutputMessage myMessageHendler(WebSocketMessage message) {

		System.out.println(message.getMessage());
		return new OutputMessage(message.getMessage(), message.getFromPlayer());
	}

}
