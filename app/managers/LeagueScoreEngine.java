package managers;

import models.Game;
import models.Player;
import managers.EloScoreEngine;

public class LeagueScoreEngine {	
	public Game evaluateFriendshipGame(Game game) {		
		return updateGame(game, 20);
	}
	
	public Game evaluateLeagueGame(Game game) {
		return updateGame(game, 40);
	}
	
	public Game updateGame(Game game, int K) {
		if (game == null) {
			return null;
		}
		
		double localPointsChange = EloScoreEngine.pointsChange(game, Player.Type.LOCAL, K);
		double visitorPointsChange = EloScoreEngine.pointsChange(game, Player.Type.VISITOR, K);
		
		game.getLocal().setScore(game.getLocal().getScore() + localPointsChange);
		game.getVisitor().setScore(game.getVisitor().getScore() + visitorPointsChange);
		
		return game;
	}
}
