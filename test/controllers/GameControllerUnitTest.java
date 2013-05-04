package controllers;

import models.Game;
import models.Game.GameInvalidModelException;
import models.Player;

import org.junit.Test;

import play.test.UnitTest;

public class GameControllerUnitTest extends UnitTest {

	@Test 
	public void resetAllCleanGamesStats() throws Exception{
		Player localPlayer = new Player("local", "localmail");
		localPlayer.save();
		Player visitorPlayer = new Player("visitor", "visitormail");
		visitorPlayer.save();
		
		Game game = GameController.addGameUtil(localPlayer, visitorPlayer, 2, 3);
		GameController.resetAllUtil();
		
		assertTrue(game.hasDefaultParams());
	}
	
	@Test
	public void recalculateGames() throws GameInvalidModelException {
		Player localPlayer = new Player("local", "localmail");
		localPlayer.save();
		Player visitorPlayer = new Player("visitor", "visitormail");
		visitorPlayer.save();
		
		Game game = new Game(localPlayer, visitorPlayer, 2, 3);
		game.save();
		
		Game game2 = new Game(localPlayer, visitorPlayer, 0, 1);
		game2.save();
		
		GameController.recalculateAllGames();
		
		Game theGame = Game.findById(game.getId());
		assertEquals(-10, theGame.getLocalPointsChange(), 1e-18);
		assertEquals(10, theGame.getVisitorPointsChange(), 1e-18);
		
		Game theGame2 = Game.findById(game2.getId());
		
		assertEquals(-9.42, theGame2.getLocalPointsChange(), 1e-18);
		assertEquals(+9.42, theGame2.getVisitorPointsChange(), 1e-18);
		
		assertEquals(Player.DEFAULT_SCORE - 19.42, localPlayer.getScore(), 1e-18);
		assertEquals(Player.DEFAULT_SCORE + 19.42, visitorPlayer.getScore(), 1e-18);
		assertEquals(2, localPlayer.getCountGames(), 1e-18);
		assertEquals(2, localPlayer.getCountLosts(), 1e-18);
		assertEquals(2, localPlayer.getCountScoredGoals(), 1e-18);
	}

}
