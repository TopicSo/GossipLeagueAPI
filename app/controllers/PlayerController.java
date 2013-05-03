package controllers;

import java.util.List;

import controllers.admin.Players;

import models.Game;
import models.Player;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.Util;

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
	
	public static void addPlayer(@Required String username, @Required String email) { 
		Player player = PlayerController.addPlayerUtil(username, email);
		boolean added = (player != null);
		
		render(added, player);
	}
	
	@Util
	public static Player addPlayerUtil(@Required String username, @Required String email) {
		Player player = null;
		
		if (username != null && email != null) {
			if(Player.findByUsername(username) == null && Player.findByEmail(email) == null){
				player = new Player(username, email);
				player.save();
			}
        }
		return player;
	}
	
	public static void resetPlayers(){
		boolean reset = PlayerController.resetPlayersUtil();
		render(reset);
	}
	
	@Util
	public static boolean resetPlayersUtil() {
		List<Player> players = Player.findPlayersSortedByUsername();
		
		if(players == null || players.size() == 0){
			return false;
		}
		
		for(Player player: players){
			player.resetParams();
			player.save();
		}
		return true;
	}

    public static void deleteAll(){
    
    	Game.deleteAll();
    	render();
    }
}