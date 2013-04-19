package controllers;

import java.util.List;

import models.Player;
import play.mvc.Controller;
import play.test.Fixtures;

public class Application extends Controller {

    public static void index() {
    	List<Player> players = Player.findPlayersSortedByScore();
		render(players);
    }

    public static void deleteAll() {
    	Fixtures.deleteDatabase();
        renderText("deleted");
    }
}