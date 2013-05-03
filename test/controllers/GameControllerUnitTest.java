package controllers;

import models.Game;
import models.Game.GameInvalidModelException;
import models.Player;

import org.junit.Test;

import play.test.UnitTest;

public class GameControllerUnitTest extends UnitTest {

	@Test 
	public void resetAllCleanGamesStats() throws GameInvalidModelException{
		Player localPlayer = new Player("local", "localmail");
		localPlayer.save();
		Player visitorPlayer = new Player("visitor", "visitormail");
		visitorPlayer.save();
		
		Game game = new Game(localPlayer, visitorPlayer, 2, 3);
		game.save();
		
		GameController.resetAllUtil();
		
		Game theGame = Game.find("").first();
		assertEquals(theGame.getLocalPointsChange(), 0, 1e-18);
	}

}
