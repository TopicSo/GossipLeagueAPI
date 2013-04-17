
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import managers.LeagueScoreEngine;
import models.Game;
import models.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

/* 
 BASIC SCORE 
---------------------
| Team 	|	Points  |
---------------------
| A		|	630		|
| B		|	500		|
| C		|	480		|
---------------------

 EXPECTED RESULTS
-------------------------------------------------------------------------
| 			| A			B 		| A			B		| A			B		|
-------------------------------------------------------------------------
| Score		| 3  		1		| 1 		3		| 2 		2		|
| K			| 20		20		| 20		20		| 20		20		|
| G			| 1.5		1.5		| 1.5		1.5		| 1			1		|
| W			| 1			0		| 0			1		| 0.5		0.5		|
| We		| 0.68		0.32 	| 0.68		0.32	| 0.68		0.32	|
| Total (P)	| +9.64		-9.64	| -20.37	+20.37	| -3.58		+3.58	|
-------------------------------------------------------------------------

*/
public class LeagueScoreEngineTest extends UnitTest{
	public static final double PLAYER_A_BASIC_SCORE = 630.0;
	public static final double PLAYER_B_BASIC_SCORE = 500.0;
	private LeagueScoreEngine scoreEngine;
	private Game game;
	
	@Before
	public void setUp() throws Exception {
		scoreEngine = new LeagueScoreEngine();
		game = createBasicGame(0, 0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEvaluateResultReturnNullWhenNoResultIsProviden() { 
		Game game = scoreEngine.evaluateFriendshipGame(null);
		
		assertEquals(null, game);
	}
	
	@Test
	public void testEvaluateResultRetunAMatchScore() {
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
		
		assertTrue(theGame instanceof Game);
	}
	
	@Test
	public void testEvaluateResultReturnSamePlayersIntoTheScore() { 
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
		
		assertTrue(theGame.local instanceof Player);
		assertTrue(theGame.visitor instanceof Player);
	}
	
	@Test
	public void testExpectedGameResultIsCorrectForLocal() { 
		createBasicGame(3, 1);
		double expectedGameResult = scoreEngine.expectedGameResult(game, Player.Type.LOCAL);
		
		assertEquals(0.68, expectedGameResult, 1e-2);
	}
	
	@Test
	public void testExpectedGameResultIsCorrectForVisitor() { 
		createBasicGame(3, 1);
		double expectedGameResult = scoreEngine.expectedGameResult(game, Player.Type.VISITOR);
		
		assertEquals(0.32, expectedGameResult, 1e-2);
	}
	
	@Test
	public void testPointsChangeForLocalIsCorrect() { 
		createBasicGame(3, 1);
		double pointsChange = scoreEngine.pointsChange(game, Player.Type.LOCAL, 20);
		
		assertEquals(9.64, pointsChange, 1e-2);
	}
	
	@Test
	public void testPointsChangeForVisitorlIsCorrect() { 
		createBasicGame(3, 1);
		double pointsChange = scoreEngine.pointsChange(game, Player.Type.VISITOR, 20);
		
		assertEquals(-9.64, pointsChange, 1e-2);
	}
	
	@Test
	public void testPointsChangeForLocalIsCorrect2() { 
		createBasicGame(1, 3);
		double pointsChange = scoreEngine.pointsChange(game, Player.Type.LOCAL, 20);
		
		assertEquals(-20.36, pointsChange, 1e-2);
	}
	
	@Test
	public void testPointsChangeForVisitorlIsCorrect2() { 
		createBasicGame(1, 3);
		double pointsChange = scoreEngine.pointsChange(game, Player.Type.VISITOR, 20);
		
		assertEquals(20.36, pointsChange, 1e-2);
	}
	
	@Test
	public void testCase1() {
		createBasicGame(3, 1);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
		
		assertDouble((PLAYER_A_BASIC_SCORE + 9.64), theGame.local.getScore());
		assertDouble((PLAYER_B_BASIC_SCORE - 9.64), theGame.visitor.getScore());
	}
	
	@Test
	public void testCase2() {
		createBasicGame(1, 3);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
			
		assertDouble((PLAYER_A_BASIC_SCORE - 20.36), theGame.local.getScore());
		assertDouble((PLAYER_B_BASIC_SCORE + 20.36), theGame.visitor.getScore());
	}
	
	@Test
	public void testCase3() {
		createBasicGame(2, 2);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
			
		assertDouble((PLAYER_A_BASIC_SCORE - 3.58), theGame.local.getScore());
		assertDouble((PLAYER_B_BASIC_SCORE + 3.58), theGame.visitor.getScore());
	}
	
	@Test
	public void testGameResultForWinner() {
		createBasicGame(3, 1);
		double gameResult = scoreEngine.gameResult(game, Player.Type.LOCAL);
			
		assertDouble(1, gameResult);
	}
	
	@Test
	public void testGameResultForLoser() {
		createBasicGame(3, 1);
		double gameResult = scoreEngine.gameResult(game, Player.Type.VISITOR);
			
		assertDouble(0, gameResult);
	}
	
	@Test
	public void testGameResultForDrawn() {
		createBasicGame(3, 3);
		double gameResult = scoreEngine.gameResult(game, Player.Type.VISITOR);
			
		assertDouble(0.5, gameResult);
	}
	
	private Game createBasicGame(int golsLocal, int golsVisitor) {
		game = new Game(new Player("", "", PLAYER_A_BASIC_SCORE), new Player("", "", PLAYER_B_BASIC_SCORE), golsLocal, golsVisitor);
		return game;
	}
	
	private static void assertDouble(double d1, double d2){

		assertEquals(d1, d2, 1e-18);
	}
}
