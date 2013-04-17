package managers;

import models.Game;
import models.Player;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class BaseScoreEngineTest extends UnitTest{
	public static final double PLAYER_A_BASIC_SCORE = 630.0;
	public static final double PLAYER_B_BASIC_SCORE = 500.0;

	protected Game game;
	
	@Before
	public void setUp() throws Exception {
		game = createBasicGame(0, 0);
	}
	
    @Test
    public void aVeryImportantThingToTest() {
        assertTrue(true);
    }
	
	protected Game createBasicGame(int golsLocal, int golsVisitor) {
		game = new Game(new Player("", "", PLAYER_A_BASIC_SCORE), new Player("", "", PLAYER_B_BASIC_SCORE), golsLocal, golsVisitor);
		return game;
	}
	
	protected static void assertDouble(double d1, double d2){
		assertEquals(d1, d2, 1e-18);
	}
}
