package controllers;

import org.junit.After;
import org.junit.Before;

import play.Logger;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

import com.google.gson.JsonParser;

public abstract class BaseControllerTest extends FunctionalTest {

	protected Response response;
	
	@Before
    public void setupDatabaseAndResponse() {

        Fixtures.deleteDatabase();
        response = null;
    }

    @After
    public void checkJsonContentType() {

        assertContentType("application/json", response);
    }

    @After
    public void checkValidJson() {
Logger.info("response " + response.out.toString());
        new JsonParser().parse(response.out.toString());
    }
}