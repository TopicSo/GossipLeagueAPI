package models;

import org.junit.Test;

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
}
