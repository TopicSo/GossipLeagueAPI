package controllers;

import models.Player;

import org.junit.Test;

import play.test.UnitTest;

public class PlayerControllerUnitTest extends UnitTest{
	@Test 
	public void resetAllPlayers() throws Exception{
		PlayerController.addPlayerUtil("local", "mail");
		Player localPlayer = Player.findByEmail("mail");
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
