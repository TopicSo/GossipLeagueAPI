package controllers;

import java.util.HashMap;
import java.util.Map;

import models.Player;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PlayerControllerTest extends BaseControllerTest {

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
        
        assertEquals(0, players.size());
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
        assertEquals(1, players.size());
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
	
	@Test
	public void addAPlayerWithoutParametersKO(){
	    Map parameters = new HashMap<String, String>();
        
		response = POST("/players", parameters);
		
    	JsonParser parser = new JsonParser();
        JsonObject addedPlayer = parser.parse(response.out.toString()).getAsJsonObject();
        
		assertEquals(false, addedPlayer.getAsJsonPrimitive("added").getAsBoolean());
	}
	
	@Test
	public void addAPlayerReturnsOk(){
		String newUsername = "usuario";
        Map parameters = new HashMap<String, String>();
        parameters.put("username", newUsername);
        parameters.put("email", "email");
        
		response = POST("/players", parameters);
    	JsonParser parser = new JsonParser();
        JsonObject addedPlayer = parser.parse(response.out.toString()).getAsJsonObject();

		assertEquals(true, addedPlayer.getAsJsonPrimitive("added").getAsBoolean());        
		assertEquals(newUsername, addedPlayer.getAsJsonObject("player").getAsJsonPrimitive("username").getAsString());
	}
	
	@Test
	public void addAPlayerWithAEmailAlreadyUserdKO(){
		String newUsername = "usuario";
		
		Player p1 = new Player(newUsername, "asdasd");
		p1.save();
		
        Map parameters = new HashMap<String, String>();
        parameters.put("username", newUsername);
        parameters.put("email", "email");
        
		response = POST("/players", parameters);
		
    	JsonParser parser = new JsonParser();
        JsonObject addedPlayer = parser.parse(response.out.toString()).getAsJsonObject();

		assertEquals(false, addedPlayer.getAsJsonPrimitive("added").getAsBoolean());        
	}
	
	@Test
	public void addAPlayerWorks(){
        Map parameters = new HashMap<String, String>();
        parameters.put("username", "usuario");
        parameters.put("email", "email");
        
		response = POST("/players", parameters);
        response = GET("/players/ranking");
        
        assertIsOk(response);
        assertContentType("application/json", response);

    	JsonParser parser = new JsonParser();
        JsonArray players = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("players");
        
        assertEquals(1, players.size());
	}
}