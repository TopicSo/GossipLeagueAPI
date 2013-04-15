package managers;

import models.Game;

public class LeagueScoreEngine {
	
	public enum PlayerType {
	    LOCAL, VISITOR
	}
	
	public Game evaluateFriendshipGame(Game game) {		
		return updateGame(game, 20);
	}
	
	public Game evaluateLeagueGame(Game game) {
		return updateGame(game, 40);
	}
	
	private Game updateGame(Game game, int K) {
		if (game == null) {
			return null;
		}
		game.local.score += pointsChange(game, PlayerType.LOCAL, K);
		game.visitor.score += pointsChange(game, PlayerType.VISITOR, K);
		
		return game;
	}
	
	public double pointsChange(Game game, PlayerType playerType, int K) {
		return K * gScore(game) * (gameResult(game, playerType) - expectedGameResult(game, playerType));
	}
	
	private double gameResult(Game game, PlayerType playerType) {
		int golsLocal = game.golsLocal;
		int golsVisitor = game.golsVisitor;
		
		if (golsLocal == golsVisitor) {
			return 0.5;
		} else if (golsLocal > golsVisitor) {
			return (playerType == PlayerType.LOCAL) ? 1 : 0;
		} else {
			return (playerType == PlayerType.VISITOR) ? 1 : 0;
		}
	}
	
	public double expectedGameResult(Game game, PlayerType playerType) {
		double localExpectedGameResult = 1.0/(Math.pow(10, -(double)scoreDifference(game)/400) + 1);
		if (playerType == PlayerType.LOCAL) {
			return localExpectedGameResult;
		} else {
			return 1 - localExpectedGameResult;
		}
	}
	
	private double gScore(Game game) {
		int goalsDifference = golsDifference(game);
		if (goalsDifference == 0 || goalsDifference == 1) {
			return 1;
		}
		if (goalsDifference == 2) {
			return 1.5;
		}
		return (11 - goalsDifference)/8;
	}
	
	private int golsDifference(Game game) {
		return Math.abs(game.golsLocal - game.golsVisitor);
	}
	
	private double scoreDifference(Game game) {
		return Math.abs(game.local.score - game.visitor.score);
	}
}
