package controllers;

import java.util.List;

import controllers.admin.Players;

import models.Game;
import models.Player;
import play.data.validation.Required;
import play.mvc.Controller;

public class PlayerController extends Controller {

	public static void players(){
		List<Player> players = Player.findPlayersSortedByUsername();
		render(players);
	}

	public static void playersRaw(){
		
		List<Player> players = Player.findAll();
		
		String output = "";
		for(Player player: players){
			output = output + "echo \"Adding player " + player.getUsername() + " " + player.getEmail() + "\"\n";
			output = output + "sh addPlayer.sh " + player.getUsername() + " " + player.getEmail() + "\n\n";
		}
		
		renderText(output);
	}
	
	public static void ranking(){
		List<Player> players = Player.findPlayersSortedByScore();
		render(players);
	}
	
	public static void addPlayer(@Required String username, @Required String email) throws Exception{
		
		boolean added = false; 
		Player player = null;
		
		if (username != null && email != null) {
			
			if(Player.findByUsername(username) == null && Player.findByEmail(email) == null){
				
				added = true;
				player = new Player(username, email);
				player.save();
			}
        }
		
		render(added, player);
	}
}