package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity
public class Game extends GenericModel{

	public class GameInvalidModelException extends Exception{
		
	}
	
    @Transient
    public static final int DEFAULT_RECS_PER_PAGE		= 20;
    
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    
    @Required
    @ManyToOne
    private Player local;
   
	@Required
    @ManyToOne
    private Player visitor;
	
	@Required
    public long new_created;
    
    @Required
    private int golsLocal;
    
    @Required
    private int golsVisitor;
    
    @Required
    private double localPointsChange;
    
	@Required
    private double visitorPointsChange;

	public Game(Player localPlayer, Player visitorPlayer, int localGoals, int visitorGoals) throws GameInvalidModelException{
    	super();
    	
    	if(localPlayer.equals(visitorPlayer)){
    		throw new GameInvalidModelException();
    	}
    	
    	this.local = localPlayer;
    	this.visitor = visitorPlayer;
    	this.golsLocal = localGoals;
    	this.golsVisitor = visitorGoals;
    	
		this.new_created = new Date().getTime();
    }
    
    /*
     * Getter
     */
    
    public String getId(){
    	return id;
    }
    
    public Player getLocal() {
		return local;
	}

    public Player getVisitor() {
    	return visitor;
    }

    public int getGolsLocal() {
    	return golsLocal;
    }
    
    public int getGolsVisitor() {
    	return golsVisitor;
    }

    public long getPlayedOnDateSeconds() {
        return (this.new_created / 1000l);
    }
    
    public double getLocalPointsChange() {
		return localPointsChange;
	}

    public double getVisitorPointsChange() {
		return visitorPointsChange;
	}
    
    /*
     * Setter
     */
    
	public void setLocal(Player local) {
		this.local = local;
	}

	public void setVisitor(Player visitor) {
		this.visitor = visitor;
	}

	public void setGolsLocal(int golsLocal) {
		this.golsLocal = golsLocal;
	}

	public void setGolsVisitor(int golsVisitor) {
		this.golsVisitor = golsVisitor;
	}

	public static List<Game> findGamesBetween(Player player1, Player player2,
			int page, int recsPerPage) {

		int start = recsPerPage * page;

		if(player1 == null && player2 == null){
			return Game.findAll();
		} else if(player1 == null){
			return Game.find("local = ? OR visitor = ? order by new_created desc", player2, player2).from(start).fetch(recsPerPage);
		} else if(player2 == null){
			return Game.find("local = ? OR visitor = ? order by new_created desc", player1, player1).from(start).fetch(recsPerPage);
		} else{
			return Game.find("(local = ? AND visitor = ?) OR (local = ? AND visitor = ?) order by new_created desc", player1, player2, player2, player1).from(start).fetch(recsPerPage);
		}
	}
	
	public void setLocalPointsChange(double localPointsChange) {
		this.localPointsChange = localPointsChange;
	}
	
	public void setVisitorPointsChange(double visitorPointsChange) {
		this.visitorPointsChange = visitorPointsChange;
	}

}
