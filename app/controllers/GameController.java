package controllers;

import java.util.List;

import managers.LeagueScoreEngine;
import models.Game;
import models.Player;
import play.mvc.Controller;

public class GameController extends Controller {

	public static void list(String playerId){
		List<Game> games = Game.findAll();
		
		render(games);
	}
	
    public static void addGame(String localPlayerId, String visitorPlayerId, int localGoals, int visitorGoals) throws Exception{

    	Player local = Player.findById(localPlayerId);
    	Player visitor = Player.findById(visitorPlayerId);
    	
    	if(local == null || visitor == null){
    		throw new Exception("Unexpected Players");
    	}
    	
    	Game game = new Game(local, visitor, localGoals, visitorGoals);
    	game.save();
    
    	LeagueScoreEngine scoreEngine = new LeagueScoreEngine();
    	scoreEngine.evaluateFriendshipGame(game);
    	
        render(game);
    }
}