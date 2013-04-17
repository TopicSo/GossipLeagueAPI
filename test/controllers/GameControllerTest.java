package controllers;

import models.Game;
import models.Player;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class GameControllerTest extends BaseControllerTest {
    
	@Test
	public void gamesForANewPlayerIsEmpty(){
		Player player = new Player("NewUser", "email");
		response = GET("/games?playerId=" + player.getId());

    	JsonParser parser = new JsonParser();
        JsonArray games = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("games");
        
        assertTrue(games.size() == 0);
	}
	
	@Test
	public void OneGameReturnsOneGame(){
		Player localPlayer = new Player("local", "localmail");
		localPlayer.save();
		Player visitorPlayer = new Player("visitor", "visitormail");
		visitorPlayer.save();
		
		Game game = new Game(localPlayer, visitorPlayer, 2, 3);
		game.save();
		
		response = GET("/games");

    	JsonParser parser = new JsonParser();
        JsonArray games = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("games");
        
        assertTrue(games.size() == 1);
	}
}