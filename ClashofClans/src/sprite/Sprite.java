package sprite;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import shape.Point2D;

public abstract class Sprite {

    private Pane layer; //noeud
    
    private Shape imageView;

    protected Point2D p;
    
    protected Color color;

    private boolean removable = false;

    private double w;
    private double h;

    public Sprite(Pane layer, Point2D point, Color c, double w, double h) {

        this.layer = layer;
        
        this.p = new Point2D(point);
        
        this.color = c;

        this.w = w; 
        this.h = h; 

        this.imageView = toShapeFX(this.color);
        this.imageView.relocate(p.getX(), p.getY());

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
    
    public void move() {
        p.setX(p.getX() + 0.1);
        p.setY(p.getY() + 0.1);
        //p.setY(p.getX() + 1);
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

    public boolean isRemovable() {
        return removable;
    }

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

	public Pane getLayer() {
		return layer;
	}

	public void setLayer(Pane layer) {
		this.layer = layer;
	}
	
	public Shape getImageView() {
		return imageView;
	}

	public void setImageView(Shape imageView) {
		this.imageView = imageView;
	}

	public Point2D getP() {
		return p;
	}
	
	public boolean inside(Point2D p) {
		double x = p.getX(),
				y = p.getY(),
				this_x = getX(),
				this_y = getY();
		double w = getWidth();
		return (x >= this_x && x <= this_x +w ) && (y >= this_y && y <= this_y +w ); 
	}
	
	public boolean inside(Sprite s) {
		double x = s.getX(),
				y = s.getY(),
				this_x = getX(),
				this_y = getY();
		double w = s.getWidth();
		return  (x + w >= this_x && x <= this_x + this.w ) && (y + w >= this_y && y <= this_y + this.w ); 
	}
	
	

}
