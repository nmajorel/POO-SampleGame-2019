package shape;

public class Point2D {
	double x, y;

	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point2D(Point2D p) {
		this(p.x, p.y);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void translate(double dx, double dy) {
		x = x + dx;
		y = y + dy;
	}

	public double distance(Point2D p) {
		double d1 = p.x - x;
		double d2 = p.y - y;
		return Math.sqrt(d1 * d1 + d2 * d2);
	}

	public String toString() {
		return "Point2D toString (" + x + ", " + y + ")";
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o != null) {
			if(o instanceof Point2D) {
			if (((Point2D) o).getX() != this.x || ((Point2D) o).getY() != this.y){
				return false;
			}
			return true;
			}
}
		
		return false;
	}
}