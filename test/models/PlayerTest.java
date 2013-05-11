package models;

import models.Game.GameInvalidModelException;

import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class PlayerTest extends UnitTest {
	
	private static final double DELTA = 1e-15;
    
	@Test
    public void createAnPlayerNotNull() {

    	String username = "Test player";
    	String email = "test@mail.com";
    	
    	Player player = new Player(username, email);
    	
    	assertNotNull(player);
    	assertEquals(username, player.getUsername());
    	assertEquals(email, player.getEmail());
    	assertEquals(player.gratavarGenerate(), player.getAvatar());
    	assertNotSame(0, player.getCreationDateSeconds());
    }
    
    @Test
    public void scoreForNewUserIsDefaultScore() {

    	String username = "Test player";
    	String email = "test@mail.com";
    	
    	Player p = new Player(username, email);
    	
    	assertEquals(p.getScore(), Player.DEFAULT_SCORE, DELTA);
    }
    
    @Test
    public void reset() throws GameInvalidModelException {
    	Player localPlayer = new Player("local", "localmail");
    	localPlayer.setCountConcededGoals(123);
    	localPlayer.setCountDraws(123);
    	localPlayer.setCountGames(123);
    	localPlayer.setCountLosts(123);
    	localPlayer.setCountScoredGoals(123);
    	localPlayer.setCountWins(123);
    	localPlayer.setScore(123);
		
		
		localPlayer.resetParams();
		
		assertTrue(localPlayer.hasDefaultParams());
    }

    @Test
    public void breakEvenPoint(){
    	Player player = new Player(1001);
    	player.setCountGames(600);
    	player.save();
    	
    	assertEquals(200, Player.findBreakEvenPoint());
    }
    
    @Test
    public void topPlayers(){
    	Fixtures.deleteDatabase();
    	
    	Player player1 = new Player(1001);
    	player1.setCountGames(60);
    	player1.save();

    	Player player2 = new Player(1001);
    	player2.setCountGames(2);
    	player2.save();
    	
    	long breakEvenPoint = Player.findBreakEvenPoint();
    	assertEquals(20, breakEvenPoint);
    	assertEquals(1, Player.findTopPlayersSortedByScore(breakEvenPoint).size());
    	assertEquals(1, Player.findBottomPlayersSortedByScore(breakEvenPoint).size());
    }
}
