import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Soldier extends Sprite {
	
	private int production_cost;
    private int production_time;
    protected double speed;
    private int damage;
    
	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	
	public Soldier(Pane layer, Point2D point, int health, double w, double h) {
		super(layer, point, health, Color.RED, w, h);
		this.color = Color.YELLOW;
		this.production_cost = 100;
		this.production_time = 5;
		this.speed = 2;
		this.damage = 1;
		
		
			// TODO Auto-generated constructor stub
		}

    
    
   
    
    

}
