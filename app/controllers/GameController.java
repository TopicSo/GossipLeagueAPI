package controllers;

import java.util.List;

import managers.LeagueScoreEngine;
import models.Game;
import models.Game.GameInvalidModelException;
import models.Player;
import play.data.validation.Required;
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
	
	public static void listRaw(){
		
		List<Game> games = Game.findAll();
		
		String output = "";
		for(Game game: games){
			output = output + "echo \"Adding a game between " + game.getLocal().getUsername() + " and " + game.getVisitor().getUsername() + " with: " + game.getGolsLocal() + "-" + game.getGolsVisitor() + "\"\n";
			output = output + "sh addGame.sh " + game.getLocal().getUsername() + " " + game.getVisitor().getUsername() + " " + game.getGolsLocal() + " " + game.getGolsVisitor() + "\n\n";
		}
		
		renderText(output);
	}
	
	
    public static void addGame(@Required String localPlayer, @Required String visitorPlayer, @Required int localGoals, @Required int visitorGoals) throws Exception{
    	boolean added = false;
    	Game game = null;
    	
    	if(localPlayer == null || visitorPlayer == null){
    		render(added, game);
    	}
    
    	Player local = Player.findById(localPlayer);
    	if(local == null){
    		local = Player.findByUsername(localPlayer);
    	}
    	
    	Player visitor = Player.findById(visitorPlayer);
    	if(visitor == null){
    		visitor = Player.findByUsername(visitorPlayer);
    	}
    	if(local == null || visitor == null){
    		throw new Exception("Unexpected Players");
    	}
   
    	try{    		
    		game = new Game(local, visitor, localGoals, visitorGoals);
    		added = true;
    		game.save();
    	}
    	catch(GameInvalidModelException e){
    		render(added, game);
    	}
    
    	LeagueScoreEngine scoreEngine = new LeagueScoreEngine();
    	scoreEngine.evaluateFriendshipGame(game);
    	
    	game.save();
    	
    	// update stats
    	local.save();
    	visitor.save();
    	
        render(added, game);
    }
    
    public static void deleteAll(){
    
    	Game.deleteAll();
    	render();
    }
}