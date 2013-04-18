package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.GenericModel;

@Entity
public class Player extends GenericModel{
	public enum Type {
	    LOCAL, VISITOR
	}
    public static final double DEFAULT_SCORE = 1000;

	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    
    @Required
    public long new_created;
    
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

	private double score;
	
	public Player(String username, String email, double score) { 
		this(username, email);
		this.score = score;
	}
	
	public Player(String username, String email) {
		super();
		
		this.username = username;
		this.email = email;
		this.score = DEFAULT_SCORE;
		
		this.new_created = new Date().getTime();
	}
	
	/*
	 * Getter
	 */
	
	public String getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}

    public long getCreationDateSeconds() {
        return (this.new_created / 1000l);
    }
    
    public double getScore() {
    	return score;
    }
    
	/*
	 * Setter 
	 */
	

	public void setScore(double score) {
		this.score = score;
	}

	/*
	 * Find
	 */
	
	public static List<Player> findPlayersSortedByScore() {
		return Player.find("order by score desc").fetch();
	}

	public static Player findByUsername(String username) {
		return Player.find("username = ?", username).first();
	}
	
	public static Player findByEmail(String email) {
		return Player.find("email = ?", email).first();
	}
}
