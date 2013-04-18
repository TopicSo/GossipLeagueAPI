package controllers;

import java.util.List;

import managers.LeagueScoreEngine;
import models.Game;
import models.Player;
import play.mvc.Controller;

public class GameController extends Controller {

	public static void list(String player1Id, String player2Id, int page, int recsPerPage){
		Player player1 = null;
		Player player2 = null;
		List<Game> games = null;
		
		if(player1Id != null){
			player1 = Player.findById(player1Id);
		}
		
		if(player2Id != null){
			player2 = Player.findById(player2Id);
		}

        if(recsPerPage == 0)
        	recsPerPage = Game.DEFAULT_RECS_PER_PAGE;

		games = Game.findGamesBetween(player1, player2, page, recsPerPage);
		
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