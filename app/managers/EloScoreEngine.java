package managers;

import models.Game;
import models.Player;
import utils.Utils;

public class EloScoreEngine {
	public static double pointsChange(Game game, Player.Type playerType, int K) {
		return Utils.twoDecimalDouble(K * gScore(game) * (gameResult(game, playerType) - expectedGameResult(game, playerType)));
	}
	
	public static double gameResult(Game game, Player.Type playerType) {
		int golsLocal = game.getGolsLocal();
		int golsVisitor = game.getGolsVisitor();
		
		if (golsLocal == golsVisitor) {
			return 0.5;
		} else if (golsLocal > golsVisitor) {
			return (playerType == Player.Type.LOCAL) ? 1 : 0;
		} else {
			return (playerType == Player.Type.VISITOR) ? 1 : 0;
		}
	}
	
	public static double expectedGameResult(Game game, Player.Type playerType) {
		double localExpectedGameResult = 1.0/(Math.pow(10, -(double)scoreDifference(game)/400) + 1);
		if (playerType == Player.Type.LOCAL) {
			return localExpectedGameResult;
		} else {
			return 1 - localExpectedGameResult;
		}
	}
	
	private static double gScore(Game game) {
		int goalsDifference = golsDifference(game);
		if (goalsDifference == 0 || goalsDifference == 1) {
			return 1;
		}
		if (goalsDifference == 2) {
			return 1.5;
		}
		return (11 - goalsDifference)/8;
	}
	
	private static int golsDifference(Game game) {
		return Math.abs(game.getGolsLocal() - game.getGolsVisitor());
	}
	
	private static double scoreDifference(Game game) {
		return Math.abs(game.getLocal().getScore() - game.getVisitor().getScore());
	}
}
