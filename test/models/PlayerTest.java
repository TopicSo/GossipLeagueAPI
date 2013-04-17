package models;

import org.junit.Test;

import play.test.UnitTest;

public class PlayerTest extends UnitTest {

    @Test
    public void createAnPlayerNotNull() {

    	String username = "Test player";
    	String email = "test@mail.com";
    	
    	Player p = new Player(username, email);
    	
    	assertTrue(p != null);
    	assertTrue(username.equals(p.getUsername()));
    	assertTrue(email.equals(p.getEmail()));
    }
    
    @Test
    public void scoreForNewUserIsDefaultScore() {

    	String username = "Test player";
    	String email = "test@mail.com";
    	
    	Player p = new Player(username, email);
    	
    	assertTrue(p.getScore() == Player.DEFAULT_SCORE);
    }
}
