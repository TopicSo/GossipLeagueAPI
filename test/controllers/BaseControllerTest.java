package controllers;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class BaseControllerTest extends FunctionalTest {

	protected Response response;
	
	@Before
    public void setupDatabaseAndResponse() {

        Fixtures.deleteDatabase();
        response = null;
    }
	
	@Test
    public void aVeryImportantThingToTest() {
        assertTrue(true);
    }
}