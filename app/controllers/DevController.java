package controllers;

import java.util.List;

import models.Player;
import play.Logger;
import play.mvc.Controller;

public class DevController extends Controller {

	public static void updatePlayerAvatars() {

		List<Player> players = Player.findAll();

		for (Player player : players) {
			player.setAvatar(player.gratavarGenerate());
			player.save();
		}

		renderText("Ok");
	}

}