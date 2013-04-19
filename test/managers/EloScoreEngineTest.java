package managers;

import models.Player;

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
| G			| 1.5		1.5		| 1.5		1.5		| 1			1		|
| W			| 1			0		| 0			1		| 0.5		0.5		|
| We		| 0.68		0.32 	| 0.68		0.32	| 0.68		0.32	|
| Total (P)	| +9.64		-9.64	| -20.37	+20.37	| -3.58		+3.58	|
-------------------------------------------------------------------------
*/

public class EloScoreEngineTest extends BaseScoreEngineTest{
	private EloScoreEngine eloEngine;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		eloEngine = new EloScoreEngine();
	}
	@Test
	public void testExpectedGameResultIsCorrectForLocal() { 
		createBasicGameAB(3, 1);
		double expectedGameResult = eloEngine.expectedGameResult(game, Player.Type.LOCAL);
		
		assertEquals(0.68, expectedGameResult, 1e-2);
	}
	
	@Test
	public void testExpectedGameResultIsCorrectForVisitor() { 
		createBasicGameAB(3, 1);
		double expectedGameResult = eloEngine.expectedGameResult(game, Player.Type.VISITOR);
		
		assertEquals(0.32, expectedGameResult, 1e-2);
	}
	
	@Test
	public void testPointsChangeForLocalIsCorrect() { 
		createBasicGameAB(3, 1);
		double pointsChange = eloEngine.pointsChange(game, Player.Type.LOCAL, 20);
		
		assertEquals(9.64, pointsChange, 1e-2);
	}
	
	@Test
	public void testPointsChangeForVisitorlIsCorrect() { 
		createBasicGameAB(3, 1);
		double pointsChange = eloEngine.pointsChange(game, Player.Type.VISITOR, 20);
		
		assertEquals(-9.64, pointsChange, 1e-2);
	}
	
	@Test
	public void testPointsChangeForLocalIsCorrect2() { 
		createBasicGameAB(1, 3);
		double pointsChange = eloEngine.pointsChange(game, Player.Type.LOCAL, 20);
		
		assertEquals(-20.36, pointsChange, 1e-2);
	}
	
	@Test
	public void testPointsChangeForVisitorlIsCorrect2() { 
		createBasicGameAB(1, 3);
		double pointsChange = eloEngine.pointsChange(game, Player.Type.VISITOR, 20);
		
		assertEquals(20.36, pointsChange, 1e-2);
	}
	
	@Test
	public void testGameResultForWinner() {
		createBasicGameAB(3, 1);
		double gameResult = eloEngine.gameResult(game, Player.Type.LOCAL);
			
		assertDouble(1, gameResult);
	}
	
	@Test
	public void testGameResultForLoser() {
		createBasicGameAB(3, 1);
		double gameResult = eloEngine.gameResult(game, Player.Type.VISITOR);
			
		assertDouble(0, gameResult);
	}
	
	@Test
	public void testGameResultForDrawn() {
		createBasicGameAB(3, 3);
		double gameResult = eloEngine.gameResult(game, Player.Type.VISITOR);
			
		assertDouble(0.5, gameResult);
	}
}
