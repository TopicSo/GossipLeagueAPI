package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import play.data.validation.Required;
import play.db.jpa.GenericModel;

@Entity
public class Game extends GenericModel{

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
    private int golsLocal;
    
    @Required
    private int golsVisitor;

    public Game(Player localPlayer, Player visitorPlayer, int localGoals, int visitorGoals){
    	super();
    	
    	this.local = localPlayer;
    	this.visitor = visitorPlayer;
    	this.golsLocal = localGoals;
    	this.golsVisitor = visitorGoals;
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
}
