package management;
import shape.Point2D;

public class Land {
	


	private Point2D point;
	private static double w;
	private static double h;
	 
	private boolean available;
	
	public Land(double x, double y, boolean available) {
		this.point = new Point2D(x, y);
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Point2D getPoint() {
		return point;
	}
	

}
