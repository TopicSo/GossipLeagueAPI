package managers;

import play.Logger;
import models.Game;
import models.Player;
import utils.Utils;

public class EloScoreEngine {
	public static double pointsChange(Game game, Player.Type playerType, int K) {
		double a = Utils.twoDecimalDouble(K
				* gScore(game)
				* (gameResult(game, playerType) - expectedGameResult(game,
						playerType)));
		// Logger.info((playerType == Player.Type.LOCAL) ? "LOCAL" : "VISITOR");
		// Logger.info("PointsChange = "+ a);
		// Logger.info("K = "+ K);
		// Logger.info("G = "+ gScore(game));
		// Logger.info("GameResult = "+ gameResult(game, playerType));
		// Logger.info("ExpectedGame = "+ expectedGameResult(game, playerType));
		return a;
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
		double expectedGameResult = 1.0 / (Math.pow(10,
				-(double) scoreDifference(game, playerType) / 400.0) + 1);

		if (isLocalPlayerStronger(game)) {
			if (playerType == Player.Type.LOCAL) {
				return Math.max(expectedGameResult, 1 - expectedGameResult);
			} else {
				return Math.min(expectedGameResult, 1 - expectedGameResult);
			}
		}

		if (isVisitorPlayerStronger(game)) {
			if (playerType == Player.Type.LOCAL) {
				return Math.min(expectedGameResult, 1 - expectedGameResult);
			} else {
				return Math.max(expectedGameResult, 1 - expectedGameResult);
			}
		}

		return expectedGameResult;
	}

	private static boolean isLocalPlayerStronger(Game game) {
		return game.getLocal().getScore() > game.getVisitor().getScore();
	}

	private static boolean isVisitorPlayerStronger(Game game) {
		return game.getLocal().getScore() < game.getVisitor().getScore();
	}

	public static double gScore(Game game) {
		int goalsDifference = golsDifference(game);
		if (goalsDifference == 0 || goalsDifference == 1) {
			return 1;
		}
		if (goalsDifference == 2) {
			return 1.5;
		}
		return (11 + goalsDifference) / 8.0;
	}

	private static int golsDifference(Game game) {
		return Math.abs(game.getGolsLocal() - game.getGolsVisitor());
	}

	private static double scoreDifference(Game game, Player.Type playerType) {
		if (playerType == Player.Type.LOCAL) {
			return Math.abs(game.getLocal().getScore()
					- game.getVisitor().getScore());
		} else {
			return Math.abs(game.getVisitor().getScore()
					- game.getLocal().getScore());
		}
	}
}
