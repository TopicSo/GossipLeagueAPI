package controllers;

import java.util.List;

import com.thoughtworks.xstream.alias.ClassMapper.Null;

import models.Player;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;

public class PlayerController extends Controller {

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