package managers;

import models.Game;
import models.Game.GameInvalidModelException;
import models.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
| Total (P)	| +9.64		-9.64	| -20.37	+20.37	| -3.58		+3.58	|
-------------------------------------------------------------------------

-------------------------------------------------------------------------
| 			| B			C 		| B			C		| B			C		|
-------------------------------------------------------------------------
| Score		| 3  		1		| 1 		3		| 2 		2		|
| K			| 20		20		| 20		20		| 20		20		|
| Total (P)	| +14.13	-14.13	| -15.87	+15.87	| -0.58		+0.58	|
-------------------------------------------------------------------------

*/
public class LeagueScoreEngineTest extends BaseScoreEngineTest{
	
	private LeagueScoreEngine scoreEngine;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		scoreEngine = new LeagueScoreEngine();
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
		
		assertTrue(theGame.getLocal() instanceof Player);
		assertTrue(theGame.getVisitor() instanceof Player);
	}
	
	
	
	@Test
	public void testCase1() {
		createBasicGameAB(3, 1);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
		
		assertDouble((PLAYER_A_BASIC_SCORE + 9.64), theGame.getLocal().getScore());
		assertDouble((PLAYER_B_BASIC_SCORE - 9.64), theGame.getVisitor().getScore());
	}
	
	@Test
	public void testCase2() {
		createBasicGameAB(1, 3);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
			
		assertDouble((PLAYER_A_BASIC_SCORE - 20.36), theGame.getLocal().getScore());
		assertDouble((PLAYER_B_BASIC_SCORE + 20.36), theGame.getVisitor().getScore());
	}
	
	@Test
	public void testCase3() {
		createBasicGameAB(2, 2);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
			
		assertDouble((PLAYER_A_BASIC_SCORE - 3.58), theGame.getLocal().getScore());
		assertDouble((PLAYER_B_BASIC_SCORE + 3.58), theGame.getVisitor().getScore());
	}
	
	@Test
	public void testCase4() {
		createBasicGameBC(3, 1);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
			
		assertDouble((PLAYER_B_BASIC_SCORE + 14.14), theGame.getLocal().getScore());
		assertDouble((PLAYER_C_BASIC_SCORE - 14.14), theGame.getVisitor().getScore());
	}
	
	@Test
	public void testCase5() {
		createBasicGameBC(1, 3);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
			
		assertDouble((PLAYER_B_BASIC_SCORE - 15.86), theGame.getLocal().getScore());
		assertDouble((PLAYER_C_BASIC_SCORE + 15.86), theGame.getVisitor().getScore());
	}
	
	@Test
	public void testCase5Reverse() {
		createBasicGameCB(3, 1);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
			
		assertDouble((PLAYER_C_BASIC_SCORE + 15.86), theGame.getLocal().getScore());
		assertDouble((PLAYER_B_BASIC_SCORE - 15.86), theGame.getVisitor().getScore());
	}
	
	@Test
	public void testCase6() {
		createBasicGameBC(2, 2);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
			
		assertDouble((PLAYER_B_BASIC_SCORE - 0.58), theGame.getLocal().getScore());
		assertDouble((PLAYER_C_BASIC_SCORE + 0.58), theGame.getVisitor().getScore());
	}
	
	@Test
	public void testCase6Reverse() {
		createBasicGameCB(2, 2);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
			
		assertDouble((PLAYER_C_BASIC_SCORE + 0.58), theGame.getLocal().getScore());
		assertDouble((PLAYER_B_BASIC_SCORE - 0.58), theGame.getVisitor().getScore());
	}
	
	@Test
	public void testPointsChange1() {
		createBasicGameAB(3, 1);
		Game theGame = scoreEngine.evaluateFriendshipGame(game);
		
		assertDouble(9.64, theGame.getLocalPointsChange());
		assertDouble(-9.64, theGame.getVisitorPointsChange());
	}
	
	@Test
	public void testUnglorious4_0() throws GameInvalidModelException {
		Player localPlayer = new Player(999);
		Player visitorPlayer = new Player(1036);
		
		Game theGame = new Game(localPlayer, visitorPlayer, 0, 4);
		scoreEngine.evaluateFriendshipGame(theGame);
		
		assertDouble(-7.82, theGame.getLocalPointsChange());
		assertDouble(+7.82, theGame.getVisitorPointsChange());
	}
}
