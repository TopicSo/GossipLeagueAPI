package controllers;

import models.Player;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
		
        response = GET("/players/ranking");
        
        assertIsOk(response);
        assertContentType("application/json", response);

    	JsonParser parser = new JsonParser();
        JsonArray players = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("players");
        assertTrue(players.size() == 1);
    }

	@Test
    public void playersRankingFirstPlayerHasMorePointsThanTheSecondOne() {
		Player p1 = new Player("user1", "email1");
		p1.setScore(100);
		p1.save();
		
		Player p2 = new Player("user2", "email2");
		p2.setScore(200);
		p2.save();
		
        response = GET("/players/ranking");
        
        assertIsOk(response);
        assertContentType("application/json", response);

    	JsonParser parser = new JsonParser();
        JsonArray players = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("players");
        assertTrue(players.size() == 2);

        JsonObject first = players.get(0).getAsJsonObject();        
        JsonObject second = players.get(1).getAsJsonObject();
        
        assertTrue(first.getAsJsonPrimitive("score").getAsDouble() > second.getAsJsonPrimitive("score").getAsDouble());
    }
}