package models;
import models.Game.GameInvalidModelException;

import org.junit.Test;

import play.test.UnitTest;

public class GameTest extends UnitTest {

    @Test(expected=Game.GameInvalidModelException.class)
    public void gameAgaingSamePlayer() throws GameInvalidModelException {
        Player player = new Player("player", "player@mail.com");
        player.save();
        
        new Game(player, player, 0, 0);
    }

}
