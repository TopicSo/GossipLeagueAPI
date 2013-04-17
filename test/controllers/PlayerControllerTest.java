package controllers;

import java.util.List;

import models.Player;

import org.junit.Before;
import org.junit.Test;

import play.Logger;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class PlayerControllerTest extends FunctionalTest {
    
	private Response response;
	
	@Before
    public void setupDatabaseAndResponse() {

        Fixtures.deleteDatabase();
        response = null;
    }
	

	@Test
    public void playersRankingIsInJSON() {
        response = GET("/players/ranking");
        
        assertIsOk(response);
        assertContentType("application/json", response);
    }
	
	@Test
    public void playersRankingWithoutPlayersIsAnEmptyArray() {
        response = GET("/players/ranking");
        
        assertIsOk(response);
        assertContentType("application/json", response);

    	JsonParser parser = new JsonParser();
        JsonArray players = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("players");
        
        assertTrue(players.size() == 0);
    }
	
	@Test
    public void playersRankingWithOnePlayerHasOnePlayer() {
		Player p1 = new Player("user1", "email1");
		p1.save();
		
		List<Player> ps = Player.findAll();
		
        response = GET("/players/ranking");
        
        assertIsOk(response);
        assertContentType("application/json", response);

    	JsonParser parser = new JsonParser();
        JsonArray players = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("players");
        Logger.info("asdadada players-size:" + ps.size() + "\n" + response.out.toString());
        assertTrue(players.size() == 1);
    }
}