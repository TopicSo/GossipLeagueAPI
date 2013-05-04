package controllers;

import models.Player;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class PlayerControllerUnitTest extends UnitTest {

	@Before
	public void setupDatabaseAndResponse() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void resetAllPlayers() throws Exception {
		Player localPlayer = new Player("username", "mail");
		assertTrue(localPlayer != null);
		localPlayer.setCountConcededGoals(123);
		localPlayer.setCountDraws(123);
		localPlayer.setCountGames(123);
		localPlayer.setCountLosts(123);
		localPlayer.setCountScoredGoals(123);
		localPlayer.setCountWins(123);
		localPlayer.setScore(123);

		localPlayer.save();

		PlayerController.resetPlayersUtil();

		Player theLocalPlayer = Player.findById(localPlayer.getId());
		assertTrue(theLocalPlayer.hasDefaultParams());
	}
}
