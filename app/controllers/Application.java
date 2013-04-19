package controllers;

import play.mvc.Controller;
import play.test.Fixtures;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void deleteAll() {
    	Fixtures.deleteDatabase();
        renderText("deleted");
    }
}