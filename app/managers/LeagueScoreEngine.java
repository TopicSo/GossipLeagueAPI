package managers;

import models.Game;
import models.Player;

public class LeagueScoreEngine {	
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
		
		double localPointsChange = pointsChange(game, Player.Type.LOCAL, K);
		double visitorPointsChange = pointsChange(game, Player.Type.VISITOR, K);
		
		game.local.score += localPointsChange;
		game.visitor.score += visitorPointsChange;
		
		return game;
	}
	
	public double pointsChange(Game game, Player.Type playerType, int K) {
		return twoDecimalDouble(K * gScore(game) * (gameResult(game, playerType) - expectedGameResult(game, playerType)));
	}

	private double twoDecimalDouble(double value) {
		return Math.round(value*100)/100.0d;
	}
	
	public double gameResult(Game game, Player.Type playerType) {
		int golsLocal = game.golsLocal;
		int golsVisitor = game.golsVisitor;
		
		if (golsLocal == golsVisitor) {
			return 0.5;
		} else if (golsLocal > golsVisitor) {
			return (playerType == Player.Type.LOCAL) ? 1 : 0;
		} else {
			return (playerType == Player.Type.VISITOR) ? 1 : 0;
		}
	}
	
	public double expectedGameResult(Game game, Player.Type playerType) {
		double localExpectedGameResult = 1.0/(Math.pow(10, -(double)scoreDifference(game)/400) + 1);
		if (playerType == Player.Type.LOCAL) {
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
