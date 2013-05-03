package models;
import models.Game.GameInvalidModelException;

import org.junit.Test;

import controllers.GameController;

import play.test.UnitTest;

public class GameTest extends UnitTest {

    @Test(expected=Game.GameInvalidModelException.class)
    public void gameAgaingSamePlayer() throws GameInvalidModelException {
        Player player = new Player("player", "player@mail.com");
        player.save();
        
        new Game(player, player, 0, 0);
    }
    
    @Test
    public void reset() throws GameInvalidModelException {
    	Player localPlayer = new Player("local", "localmail");
		localPlayer.save();
		Player visitorPlayer = new Player("visitor", "visitormail");
		visitorPlayer.save();
		
		Game theGame = GameController.addGameUtil(localPlayer, visitorPlayer, 2, 3);
		
		theGame.reset();
		
		assertEquals(0, theGame.getLocalPointsChange(), 1e-18);
		assertEquals(0, theGame.getVisitorPointsChange(), 1e-18);
    }
}
