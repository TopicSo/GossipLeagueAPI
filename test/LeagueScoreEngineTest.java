
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
| We		| 0.679		0.321 	| 0.679		0.321	| 0.679		0.321	|
|Total (P)	| +9.63		-9.63	| -20.37	+20.37	| -3.58		+3.58	|
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
		double expectedGameResult = scoreEngine.expectedGameResult(game, LeagueScoreEngine.PlayerType.LOCAL);
		
		assertEquals(0.679, expectedGameResult, 1e-3);
	}
	
	@Test
	public void testExpectedGameResultIsCorrectForVisitor() { 
		createBasicGame(3, 1);
		double expectedGameResult = scoreEngine.expectedGameResult(game, LeagueScoreEngine.PlayerType.VISITOR);
		
		assertEquals(0.321, expectedGameResult, 1e-3);
	}
	
	@Test
	public void testPointsChangeForLocalIsCorrect() { 
		createBasicGame(3, 1);
		double pointsChange = scoreEngine.pointsChange(game, LeagueScoreEngine.PlayerType.LOCAL, 20);
		
		assertEquals(9.63, pointsChange, 1e-2);
	}
	

	@Test
	public void testCase1() {
		createBasicGame(3, 1);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
		
		
		assertEquals((PLAYER_A_BASIC_SCORE + 9.63), theGame.local.score, 1);
		assertEquals((PLAYER_B_BASIC_SCORE - 9.63), theGame.visitor.score, 1);
	}
	
	@Test
	public void testCase2() {
		createBasicGame(1, 3);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
		
		
		assertEquals((PLAYER_A_BASIC_SCORE - 20.37), theGame.local.score, 1);
		assertEquals((PLAYER_B_BASIC_SCORE + 20.37), theGame.visitor.score, 1);
	}
	
	@Test
	public void testCase3() {
		createBasicGame(2, 2);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
		
		
		assertEquals((PLAYER_A_BASIC_SCORE - 3.58), theGame.local.score, 1);
		assertEquals((PLAYER_B_BASIC_SCORE + 3.58), theGame.visitor.score, 1);
	}
	
	private Game createBasicGame(int golsLocal, int golsVisitor) {
		Player local = new Player(PLAYER_A_BASIC_SCORE);
		Player visitor = new Player(PLAYER_B_BASIC_SCORE);
		game = new Game(local, visitor, golsLocal, golsVisitor);  
		game.golsLocal = golsLocal;
		game.golsVisitor = golsVisitor;
		return game;
	}
}
