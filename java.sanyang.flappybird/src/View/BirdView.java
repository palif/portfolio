package View;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BirdView extends Rectangle {

	double velocity, gravity, upForce, delay;
	Image dualImage[];
	boolean isDualImage, isUpLift;
	
	public BirdView(double x, double y, double height, double width) {
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.velocity = 0;
		this.gravity = 0.6;
		this.upForce = -13;
		this.dualImage = new Image[2];
		this.isDualImage = false;
		this.isUpLift = false;
		this.delay = 0;
		
//		setOnKeyPressed(e->{System.out.println("pressed!");
//			if(e.getText().equals(" ")) {
//				upForce();
//			}
//		});
	}
	
	public void update() {
		this.velocity = this.velocity + this.gravity;
		this.velocity = this.velocity * 0.925;
		this.setLayoutY(this.getLayoutY() + this.velocity);
		if(this.isDualImage) {
			if (!this.isUpLift) {
				this.setImage(dualImage[0]);
			} else {
				this.setImage(dualImage[1]);
				if(delay > 5) {
					this.isUpLift = false;
					delay = 0;
				}
				delay++;
			}
		}
	}
	
	public void upForce() {
		this.isUpLift = true;
		this.velocity = this.velocity + this.upForce;
		
	}
	
	public void setImage(Image image) {
		this.setFill(new ImagePattern(image));
	}
	
	public void setDualImage(Image img1, Image img2) {
		this.isDualImage = true;
		this.dualImage[0] = img1;
		this.dualImage[1] = img2;
	}
	
	public boolean isDualImage() {
		return this.isDualImage;
	}
	
	public void offDualImage() {
		this.isDualImage = false;
	}
	
	
}
