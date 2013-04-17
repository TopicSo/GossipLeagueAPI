package controllers;

import java.util.List;

import models.Player;
import play.Logger;
import play.mvc.Controller;

public class PlayerController extends Controller {

	public static void ranking(){
		
		List<Player> players = Player.findPlayersSortedByScore();
		Logger.info("players-find " + players + players.size());
		render(players);
	}
}