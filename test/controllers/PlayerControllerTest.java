package controllers;

import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class PlayerControllerTest extends FunctionalTest {
    
	@Test
    public void playersRankingIsInJSON() {
        Response response = GET("/players/ranking");
        
        assertIsOk(response);
        assertContentType("application/json", response);
    }
	
	@Test
    public void playersRankingWithoutPlayersIsAnEmptyArray() {
        Response response = GET("/players/ranking");
        
        assertIsOk(response);
        assertContentType("application/json", response);

    	JsonParser parser = new JsonParser();
        JsonArray players = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("players");
        
        assertTrue(players.size() == 0);
    }
}