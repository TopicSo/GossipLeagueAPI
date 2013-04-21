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
	
	public Game updateGame(Game game, int K) {
		if (game == null) {
			return null;
		}
		
		updatePointsChange(game, K);
		updatePlayersScore(game, K);
		updatePlayersGameCount(game);
		updatePlayersGameWins(game);
		updatePlayersGameDraws(game);
		updatePlayersGameLosts(game);
		updatePlayersGolsScored(game);
		updatePlayersGolsConceded(game);
		
		return game;
	}

	private void updatePlayersScore(Game game, int K) {
		double localPointsChange = EloScoreEngine.pointsChange(game, Player.Type.LOCAL, K);
		double visitorPointsChange = EloScoreEngine.pointsChange(game, Player.Type.VISITOR, K);
		
		game.getLocal().setScore(game.getLocal().getScore() + localPointsChange);
		game.getVisitor().setScore(game.getVisitor().getScore() + visitorPointsChange);
	}
	
	private void updatePlayersGameCount(Game game) {
		game.getLocal().setCountGames(game.getLocal().getCountGames() + 1);
		game.getVisitor().setCountGames(game.getVisitor().getCountGames() + 1);
	}
	
	private void updatePlayersGameWins(Game game) {
		if (EloScoreEngine.gameResult(game, Player.Type.LOCAL) == 1) {
			game.getLocal().setCountWins(game.getLocal().getCountWins() + 1);
		}
		if (EloScoreEngine.gameResult(game, Player.Type.VISITOR) == 1) {
			game.getVisitor().setCountWins(game.getVisitor().getCountWins() + 1);
		}
	}
	
	private void updatePlayersGameDraws(Game game) {
		if (EloScoreEngine.gameResult(game, Player.Type.LOCAL) == 0.5) {
			game.getLocal().setCountDraws(game.getLocal().getCountDraws() + 1);
			game.getVisitor().setCountDraws(game.getVisitor().getCountDraws() + 1);
		}
	}
	
	private void updatePlayersGameLosts(Game game) {
		if (EloScoreEngine.gameResult(game, Player.Type.LOCAL) == 0) {
			game.getLocal().setCountLosts(game.getLocal().getCountLosts() + 1);
		}
		if (EloScoreEngine.gameResult(game, Player.Type.VISITOR) == 0) {
			game.getVisitor().setCountLosts(game.getVisitor().getCountLosts() + 1);
		}
	}
	
	private void updatePlayersGolsScored(Game game) {
		game.getLocal().setCountScoredGoals(game.getLocal().getCountScoredGoals() + game.getGolsLocal());
		game.getVisitor().setCountScoredGoals(game.getVisitor().getCountScoredGoals() + game.getGolsVisitor());
	}
	
	private void updatePlayersGolsConceded(Game game) {
		game.getLocal().setCountConcededGoals(game.getLocal().getCountConcededGoals() + game.getGolsVisitor());
		game.getVisitor().setCountConcededGoals(game.getVisitor().getCountConcededGoals() + game.getGolsLocal());
	}	
	
	private void updatePointsChange(Game game, int K) {
		double localPointsChange = EloScoreEngine.pointsChange(game, Player.Type.LOCAL, K);
		double visitorPointsChange = EloScoreEngine.pointsChange(game, Player.Type.VISITOR, K);
		
		game.setLocalPointsChange(localPointsChange);
		game.setVisitorPointsChange(visitorPointsChange);
	}
}
