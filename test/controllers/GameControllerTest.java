package controllers;

import java.util.HashMap;
import java.util.Map;

import models.Game;
import models.Game.GameInvalidModelException;
import models.Player;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GameControllerTest extends BaseControllerTest {
    
	@Test
	public void gamesForANewPlayerIsEmpty(){
		Player player = new Player("NewUser", "email");
		response = GET("/games?player1Id=" + player.getId());

    	JsonParser parser = new JsonParser();
        JsonArray games = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("games");

        assertEquals(0, games.size());
	}
	
	@Test
	public void OneGameReturnsOneGame() throws GameInvalidModelException{
		Player localPlayer = new Player("local", "localmail");
		localPlayer.save();
		Player visitorPlayer = new Player("visitor", "visitormail");
		visitorPlayer.save();
		
		Game game = new Game(localPlayer, visitorPlayer, 2, 3);
		game.save();
		
		response = GET("/games");

    	JsonParser parser = new JsonParser();
        JsonArray games = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("games");
        
        assertEquals(1, games.size());
	}
	
	@Test
	public void gamesBetweenTwoPlayers() throws GameInvalidModelException{
		Player localPlayer = new Player("local", "localmail");
		localPlayer.save();
		Player visitorPlayer = new Player("visitor", "visitormail");
		visitorPlayer.save();
		Player otherPlayer = new Player("other", "othermail");
		otherPlayer.save();
		
		Game game = new Game(localPlayer, visitorPlayer, 2, 3);
		game.save();
		sleep(1);
		Game game2 = new Game(visitorPlayer, localPlayer, 2, 0);
		game2.save();
		sleep(1);
		Game otherGame = new Game(otherPlayer, visitorPlayer, 2, 3);
		otherGame.save();
		
		response = GET("/games?player1Id=" + localPlayer.getId() + "&player2Id=" + visitorPlayer.getId());
    	JsonParser parser = new JsonParser();
        JsonArray games = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("games");

        assertEquals(2, games.size());
        
        // Ordered correctly ?
        JsonObject firstGame = games.get(0).getAsJsonObject();        
        JsonObject secondGame = games.get(1).getAsJsonObject();
        
        long firstGamePlayedOn = firstGame.getAsJsonPrimitive("playedOn").getAsLong();
        long secondGamePlayedOn = secondGame.getAsJsonPrimitive("playedOn").getAsLong();
        assertTrue(firstGamePlayedOn > secondGamePlayedOn);
	}

	@Test
	public void pagingGames() throws GameInvalidModelException{
		Player localPlayer = new Player("local", "localmail");
		localPlayer.save();
		Player visitorPlayer = new Player("visitor", "visitormail");
		visitorPlayer.save();
		Player otherPlayer = new Player("other", "othermail");
		otherPlayer.save();
		
		Game game = new Game(localPlayer, visitorPlayer, 2, 3);
		game.save();
		Game game2 = new Game(visitorPlayer, localPlayer, 2, 0);
		game2.save();
		Game otherGame = new Game(otherPlayer, visitorPlayer, 2, 3);
		otherGame.save();
		Game anotherGame = new Game(visitorPlayer, otherPlayer, 2, 3);
		anotherGame.save();

		response = GET("/games?player1Id=" + visitorPlayer.getId() + "&page=1&recsPerPage=1");				
		JsonParser parser = new JsonParser();
        JsonArray games = parser.parse(response.out.toString()).getAsJsonObject().getAsJsonArray("games");
        
        assertEquals(1, games.size());
	}

	@Test
	public void postAGameWithoutParametersKO(){
		Map parameters = new HashMap<String, String>();
        
		response = POST("/games", parameters);
		
    	JsonParser parser = new JsonParser();
        JsonObject addedGame = parser.parse(response.out.toString()).getAsJsonObject();
        
		assertEquals(false, addedGame.getAsJsonPrimitive("added").getAsBoolean());
	}
	
	@Test
	public void postAGameWithSamePlayerKO(){
		Player player = new Player("user", "email");
		player.save();
		
		Map parameters = new HashMap<String, String>();
        parameters.put("localPlayerId", player.getId());
        parameters.put("visitorPlayerId", player.getId());
        
		response = POST("/games", parameters);
		
    	JsonParser parser = new JsonParser();
        JsonObject addedGame = parser.parse(response.out.toString()).getAsJsonObject();
        
		assertEquals(false, addedGame.getAsJsonPrimitive("added").getAsBoolean());
	}
	
	@Test
	public void postAGameReturnAGame(){
		Player local = new Player("local", "localemail");
		local.save();

		Player visitor = new Player("visitor", "visitoremail");
		visitor.save();

		Map parameters = new HashMap<String, String>();
        parameters.put("localPlayerId", local.getId());
        parameters.put("visitorPlayerId", visitor.getId());
        parameters.put("localGoals", "0");
        parameters.put("visitorGoals", "5");
        
		response = POST("/games", parameters);
		
		
    	JsonParser parser = new JsonParser();
        JsonObject addedGame = parser.parse(response.out.toString()).getAsJsonObject();
        
        assertNotNull(addedGame.getAsJsonObject("game"));
	}
	
	@Test 
	public void postAGameChangingPlayerStats(){
		Player local = new Player("local", "localemail");
		local.save();

		Player visitor = new Player("visitor", "visitoremail");
		visitor.save();

		Map parameters = new HashMap<String, String>();
        parameters.put("localPlayerId", local.getId());
        parameters.put("visitorPlayerId", visitor.getId());
        parameters.put("localGoals", "0");
        parameters.put("visitorGoals", "5");
        
		response = POST("/games", parameters);	
		
    	JsonParser parser = new JsonParser();
        JsonObject addedGame = parser.parse(response.out.toString()).getAsJsonObject();
        
        JsonObject parsedLocal = addedGame.getAsJsonObject("game").getAsJsonObject("local");
        JsonObject parsedVisitor = addedGame.getAsJsonObject("game").getAsJsonObject("visitor");
		assertEquals(false, addedGame.getAsJsonPrimitive("added").getAsBoolean());
		assertEquals(1, parsedLocal.getAsJsonPrimitive("countGames").getAsInt());
		assertEquals(0, parsedLocal.getAsJsonPrimitive("countWins").getAsInt());
		assertEquals(1, parsedLocal.getAsJsonPrimitive("countLosts").getAsInt());
		assertEquals(0, parsedLocal.getAsJsonPrimitive("countDraws").getAsInt());
		assertEquals(0, parsedLocal.getAsJsonPrimitive("countScoredGoals").getAsInt());
		assertEquals(5, parsedLocal.getAsJsonPrimitive("countConcededGoals").getAsInt());
		assertNotSame(Player.DEFAULT_SCORE, parsedLocal.getAsJsonPrimitive("score").getAsDouble());
		
		assertEquals(1, parsedVisitor.getAsJsonPrimitive("countGames").getAsInt());
		assertEquals(1, parsedVisitor.getAsJsonPrimitive("countWins").getAsInt());
		assertEquals(0, parsedVisitor.getAsJsonPrimitive("countLosts").getAsInt());
		assertEquals(0, parsedVisitor.getAsJsonPrimitive("countDraws").getAsInt());
		assertEquals(5, parsedVisitor.getAsJsonPrimitive("countScoredGoals").getAsInt());
		assertEquals(0, parsedVisitor.getAsJsonPrimitive("countConcededGoals").getAsInt());
		assertNotSame(Player.DEFAULT_SCORE, parsedVisitor.getAsJsonPrimitive("score").getAsDouble());
	}
}