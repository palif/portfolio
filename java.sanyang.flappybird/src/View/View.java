package View;

import java.util.ArrayList;
import java.util.Random;
import Model.Model;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class View extends Pane {

	private static final int HEIGHT = 600;
	private static final int WIDTH = 450;

	private Controller controller;
	
	private BirdView bird;
	private ArrayList<Rectangle> pipeList;
	private int fps; // frame per second
	private AnimationTimer timer;
	Label scoreLabel;
	
	
	public View(Model model) {
		fps = 0;
		controller = new Controller(model, this);
		pipeList = new ArrayList<Rectangle>();
		init();
	}
	
	public void init(){
		initBackground();
		createPipe();
		initBird();
		initScore();
		createStartLabel();
		initAnimationTimer();
	}
	
	private void initAnimationTimer() {
		timer = new AnimationTimer() {
			@Override 
			public void handle(long now) {
				updateScreen();
			}
		};
	}
	
	public void initBackground() {
		Image image = new Image("file:flappybird.background.jpg");
		BackgroundImage bi = new BackgroundImage(image,BackgroundRepeat.REPEAT,
		BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bg = new Background(bi);
		this.setBackground(bg);
		this.setPrefSize(WIDTH, HEIGHT);
	}
	
	
	public void createPipe() {

		Rectangle pipe1 = new Rectangle();
		Rectangle pipe2 = new Rectangle();
		Random random = new Random();

		double gap = 100;
		double width = 100;
		int height = (int) (HEIGHT * 0.7);

		double p1Height = random.nextInt(height);
		double p2Height = HEIGHT - (p1Height + gap);

		pipe1.setLayoutX(WIDTH);
		pipe1.setLayoutY(0);
		pipe1.setWidth(width);
		pipe1.setHeight(p1Height);
		pipe1.setFill(new ImagePattern(new Image("file:flappybird.pipe.png"),  40, 40, 80, 80, false));
		

		pipe2.setLayoutX(WIDTH);
		pipe2.setLayoutY(p1Height + gap);
		pipe2.setWidth(width);
		pipe2.setHeight(p2Height);
		pipe2.setFill(new ImagePattern(new Image("file:flappybird.pipe.png"),40, 40, 80, 80, false));
		
		this.getChildren().add(pipe1);
		this.getChildren().add(pipe2);
		
		
		
		this.pipeList.add(pipe2);
		this.pipeList.add(pipe1);

	}

	public void initBird() {
		
		double x = 100;
		double y = HEIGHT / 2;
		double width = 30;
		double height = 30;
		bird = new BirdView(x,y,height,width);
		bird.setDualImage(new Image("file:flappybird.kirby1.png"), new Image("file:flappybird.kirby2.png"));
		bird.setOnKeyPressed(e->{
			System.out.println("pressed!");
			if(e.getText().equals(" ")) {
				bird.upForce();
			}
		});
		this.getChildren().add(bird);
	}
	
	public void initScore() {
		scoreLabel = new Label();
		scoreLabel.setText("0");
		scoreLabel.setFont(Font.loadFont("file:arcadefont.ttf", 60));
		scoreLabel.setTextFill(Color.WHITE);
		scoreLabel.setTextAlignment(TextAlignment.RIGHT);
		scoreLabel.setAlignment(Pos.CENTER_RIGHT);

		double centerY = WIDTH - 120;
		scoreLabel.setLayoutX(centerY);
		scoreLabel.setLayoutY(HEIGHT - 80);
		
		this.getChildren().add(scoreLabel);

	}
	
	public void setScore(int score) {
		scoreLabel.setText(""+score);
	}
	public void resetFps() {
		fps = 0;
	}
	public BirdView getBird() { return this.bird; }
	public AnimationTimer getTimer() { return this.timer; }
	
	public void start() { timer.start(); }
	public void stop() { timer.stop(); }
	
	public void reset() {
		
		for(Rectangle p : pipeList) {
			getChildren().remove(p);
		}
		pipeList.clear();
		createPipe(); scoreLabel.toFront();
		this.getChildren().remove(bird);
		initBird();
		resetFps();
		start();
		setScore(0);
	}

	public void updateScreen() {
		
		this.fps++;
		
		if ( fps % 110 == 0 ) {
			createPipe();
			scoreLabel.toFront();
		}
		
		double pipeVelocity = -3.5;
		
		/****** 
		 ** Updating Bird, up and down
		 ******/
		
		bird.update();
		
		/*****
		 ** Updating pipes, removes if passed
		 *****/
		
		for(Rectangle p : pipeList) {
			p.setLayoutX(p.getLayoutX() + pipeVelocity);
		}
		
		for(int x = 0; x < pipeList.size(); x++ ) {
			Rectangle p = pipeList.get(x);
			if ( p.getLayoutX() + p.getWidth() < 0 ) {
				pipeList.remove(x);
				this.getChildren().remove(p);
			}
		}
		
		/*****
		 ** Check if the bird Hit, if yes then game stop
		 *****/
		
		 controller.handleCheckHit(bird, pipeList);
		 
		 /*****
		  ** Updating score
		  *****/
		if(fps % 120 == 0 ) {
			setScore(fps/120);
			scoreLabel.toFront();
		}
		 
	}
	
	public void createStartLabel() {
		
		Label l = new Label();
		
		l.setText("START");
		l.setFont(Font.loadFont("file:arcadefont.ttf", 80));
		l.setTextFill(Color.BLACK);
		l.setLayoutX((WIDTH / 2) - 100);
		l.setLayoutY(100);
		this.getChildren().add(l);
		
		l.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
			controller.start();;
			this.getChildren().remove(l);
		});
		
	}
	
	
	public void showGameOverLabel() {
		Label goLabel = new Label("GAME OVER");
		Label retryLabel = new Label("ReTRY");
		Label exitLabel = new Label("EXIT");
		
		goLabel.setFont(Font.loadFont("file:arcadefont.ttf", 80));
		retryLabel.setFont(Font.loadFont("file:arcadefont.ttf", 60));
		exitLabel.setFont(Font.loadFont("file:arcadefont.ttf", 60));
		
		goLabel.setTextFill(Color.WHITE);
		retryLabel.setTextFill(Color.WHITE);
		exitLabel.setTextFill(Color.WHITE);
		
		goLabel.setLayoutY(50);
		retryLabel.setLayoutY(180);
		exitLabel.setLayoutY(350);
		
		goLabel.setLayoutX(45);
		retryLabel.setLayoutX(150);
		exitLabel.setLayoutX(170);
		
		
		this.getChildren().addAll(goLabel,retryLabel,exitLabel);
		
		retryLabel.setOnMouseClicked(e->{
			controller.handleLost(Integer.parseInt(scoreLabel.getText()));
			reset();
			this.getChildren().removeAll(goLabel,retryLabel,exitLabel);
		});
		
		exitLabel.setOnMouseClicked(e->{
			controller.handleLost(Integer.parseInt(scoreLabel.getText()));
			controller.handleExit();
		});
	}
}
