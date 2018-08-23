package pl.bpol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import pl.bpol.model.Player;
import pl.bpol.service.GameService;
import pl.bpol.service.PlayerService;

@Controller
@SessionAttributes("name")
public class StartController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "statki",method = RequestMethod.GET)
    public String hello(ModelMap modelMap){
        modelMap.addAttribute("hello","Witaj w grze w statki");
        return "hello";
    }

    @RequestMapping(value = "statki", method = RequestMethod.POST)
    public String sendName(String name, ModelMap modelMap){
        Player player = playerService.createPlayer(name);
        modelMap.addAttribute("name",player.getName());
        return "redirect:gameslist";
    }

    @RequestMapping(value = "gameslist")
    public String showGames(ModelMap modelMap){
        modelMap.put("games",gameService.getGames());
        return "gameslist";
    }

    @RequestMapping(value = "newgame")
    public String newGame(ModelMap modelMap){
        gameService.addGameByPlayerName(modelMap.get("name").toString());
        return "redirect:gameslist";
    }


}