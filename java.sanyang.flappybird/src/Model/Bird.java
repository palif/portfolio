package Model;

public class Bird {
	
	private double x, y, height, width, radius;
	
	public Bird() {
		x = y = height = width = radius = 0;
	}

	public double getX() { return x; }
	public double getY() { return y; }
	public double getWidth() { return width; }
	public double getHeight() { return height; }
	public double getRadius() { return radius; }
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	
}
