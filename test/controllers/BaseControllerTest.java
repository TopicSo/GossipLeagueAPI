package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonParser;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

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

        new JsonParser().parse(response.out.toString());
    }
}