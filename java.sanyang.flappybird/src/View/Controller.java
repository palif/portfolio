package View;

import java.util.ArrayList;

import Model.Bird;
import Model.GameState;
import Model.Model;
import Model.Pipe;
import javafx.scene.shape.Rectangle;

public class Controller {
	
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public void handleCheckHit(BirdView bird, ArrayList<Rectangle> pipeList) {
		
		ArrayList<Pipe> pList = new ArrayList<Pipe>();
		
		for(Rectangle p1 : pipeList) {
			Pipe p2 = new Pipe();
			p2.setHeight(p1.getHeight());
			p2.setWidth(p1.getWidth());
			p2.setX(p1.getLayoutX());
			p2.setY(p1.getLayoutY());
			pList.add(p2);
		}
		
		Bird b = new Bird();
		b.setX(bird.getLayoutX());
		b.setY(bird.getLayoutY());
		b.setHeight(bird.getHeight());
		b.setWidth(bird.getWidth());
		
		if ( this.model.isHit(b, pList) ) {
			System.out.println("Hit!");
			view.stop();
			model.setState(GameState.GAME_OVER);
			view.showGameOverLabel();
		}
	}
	
	public int getScore() {
		return model.getScore();
	}
	
	public void start() {
		this.model.setState(GameState.RUNNING);
		view.start();
	}
	
	public void handleLost(int score) {
		model.setScore(score);
		System.out.println(model);
	}
	
	public void handleExit() {
		System.exit(0);
	}
	
}
