package controllers;

import models.Game;
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
	
//	@Test
//	public void recalculateGames() throws GameInvalidModelException {
//		Player localPlayer = new Player("local", "localmail");
//		localPlayer.save();
//		Player visitorPlayer = new Player("visitor", "visitormail");
//		visitorPlayer.save();
//		
//		Game game = new Game(localPlayer, visitorPlayer, 2, 3);
//		game.save();
//		
//		GameController.resetAllUtil();
//		
//		Game theGame = Game.findById(game.getId());
//		assertTrue(theGame.hasDefaultParams());	
//	}

}
