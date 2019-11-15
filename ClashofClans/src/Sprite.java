
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Sprite {

    private Pane layer; //noeud
    
    private Shape imageView;

    protected Point2D p;

    private int health;
    
    protected Color color = Color.BLACK;

    private boolean removable = false;

    private double w;
    private double h;

    public Sprite(Pane layer, Point2D point, int health, double w, double h) {

        this.layer = layer;
        
        this.p = new Point2D(point);
        
        this.health = health;

        this.imageView = toShapeFX(this.color);
        this.imageView.relocate(p.getX(), p.getY());

        this.w = w; 
        this.h = h; 

        addToLayer();

    }

    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public double getX() {
        return p.getX();
    }

    public void setX(double x) {
        this.p.setX(x);
    }

    public double getY() {
        return p.getY();
    }

    public void setY(double y) {
        this.p.setY(y);;
    }

    public int getHealth() {
        return health;
    }

    public boolean isRemovable() {
        return removable;
    }

    public boolean isAlive() {
        return health > 0;
    }
    
    /*
    protected ImageView getView() {
        return imageView;
    }*/

    public void updateUI() {
        imageView.relocate(p.getX(), p.getY());
    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return p.getX() + w * 0.5;
    }

    public double getCenterY() {
        return p.getY() + h * 0.5;
    }

    // TODO: per-pixel-collision
    public boolean collidesWith(Sprite sprite) {
    	//return getView().getBoundsInParent().intersects(sprite.getView().getBoundsInParent());
    	return false;
    }

    public void remove() {
        this.removable = true;
    }

    public abstract void checkRemovability();
    
    public Shape toShapeFX(Color c){
    	Shape r = new javafx.scene.shape.Rectangle(getX(), getY(), this.w, this.h);
    	r.setFill(c);
    	return r;
    }

}
