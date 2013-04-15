package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.GenericModel;

@Entity
public class Player extends GenericModel{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    
    @Required
    @Unique
    private String username;

    @Required
    @Unique
    private String email;
    
    private String avatar;
    
	private int countGames;

	private int countWins;

	private int countLosts;

	private int countDraws;

	public double score; // TODO: Should be private
	
	public Player(double score) {
		super();
		this.score = score;
	}
	
	public Player(String username, String email) {
		
		super();
		this.username = username;
		this.email = email;
	}
	
	/*
	 * Getters
	 */

	
}
