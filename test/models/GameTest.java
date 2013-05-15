package models;
import java.util.List;

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

    @Test
    public void givenUserFindWinLostAndDrawGames() {
    	Player localPlayer = new Player("local", "localmail");
		localPlayer.save();
		Player visitorPlayer = new Player("visitor", "visitormail");
		visitorPlayer.save();
		
		Game game; 
		game = GameController.addGameUtil(localPlayer, visitorPlayer, 2, 0);
		game.save();
		
		game = GameController.addGameUtil(localPlayer, visitorPlayer, 4, 0);
		game.save();
		
		game = GameController.addGameUtil(localPlayer, visitorPlayer, 1, 1);
		game.save();
		
		game = GameController.addGameUtil(localPlayer, visitorPlayer, 0, 2);
		game.save();
		
		game = GameController.addGameUtil(localPlayer, visitorPlayer, 0, 3);
		game.save();
		
		game = GameController.addGameUtil(localPlayer, visitorPlayer, 1, 4);
		game.save();
		
		List<Game> winGames = Game.findWinGames(localPlayer, 0, 40);
		List<Game> lostGames = Game.findLostGames(localPlayer, 0, 40);
		List<Game> drawGames = Game.findDrawGames(localPlayer, 0, 40);
		
		assertEquals(2, winGames.size());
		assertEquals(3, lostGames.size());
		assertEquals(1, drawGames.size());
    }
}
