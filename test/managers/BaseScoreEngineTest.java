package managers;

import models.Game;
import models.Game.GameInvalidModelException;
import models.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class BaseScoreEngineTest extends UnitTest{
	public static final double PLAYER_A_BASIC_SCORE = 630.0;
	public static final double PLAYER_B_BASIC_SCORE = 500.0;
	public static final double PLAYER_C_BASIC_SCORE = 480.0;

	public static final String PLAYER_A_USERNAME = "playerA";
	public static final String PLAYER_B_USERNAME = "playerB";
	public static final String PLAYER_C_USERNAME = "playerC";

	protected Game game;
	
	@Before
	public void setUp() throws Exception {
		game = createBasicGameAB(0, 0);
	}
	
	@After
	public void deleteDatabase(){
		Fixtures.deleteDatabase();
	}
	
    @Test
    public void aVeryImportantThingToTest() {
        assertTrue(true);
    }
	
	protected Game createBasicGameAB(int golsLocal, int golsVisitor) {
		Player playerA = new Player(PLAYER_A_USERNAME, "", PLAYER_A_BASIC_SCORE);
		playerA.save();
		Player playerB = new Player(PLAYER_B_USERNAME, "", PLAYER_B_BASIC_SCORE);
		playerB.save();
		
		try {
			game = new Game(playerA, playerB, golsLocal, golsVisitor);
		} catch (GameInvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}
	
	protected Game createBasicGameBC(int golsLocal, int golsVisitor) {
		Player playerB = new Player(PLAYER_B_USERNAME, "", PLAYER_B_BASIC_SCORE);
		playerB.save();
		Player playerC = new Player(PLAYER_C_USERNAME, "", PLAYER_C_BASIC_SCORE);
		playerC.save();
		
		try {
			game = new Game(playerB, playerC, golsLocal, golsVisitor);
		} catch (GameInvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}
	
	protected Game createBasicGameCB(int golsLocal, int golsVisitor) {
		Player playerC = new Player(PLAYER_C_USERNAME, "", PLAYER_C_BASIC_SCORE);
		playerC.save();
		Player playerB = new Player(PLAYER_B_USERNAME, "", PLAYER_B_BASIC_SCORE);
		playerB.save();

		try {
			game = new Game(playerC, playerB, golsLocal, golsVisitor);
		} catch (GameInvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}
	
	protected static void assertDouble(double d1, double d2){
		assertEquals(d1, d2, 1e-18);
	}
}
