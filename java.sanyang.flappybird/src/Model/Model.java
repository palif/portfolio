package Model;

import java.util.ArrayList;

public class Model {
	
	private GameState state;
	private int nrOfHits;
	private int score;
	private int highscore;
	private ArrayList<Integer> listOfScore;
	
	public Model() {
		nrOfHits = score = highscore =  0;
		listOfScore = new ArrayList<>();
		setState(GameState.RUNNING);
	}
	
	public boolean isHit(Bird bird, ArrayList<Pipe> pipeList) {
	
		for(Pipe p : pipeList) {
			if(bird.getX() + bird.getWidth() > p.getX() && (bird.getX()) < (p.getX() + p.getWidth()) &&
					(bird.getY() + bird.getHeight()) > p.getY() && (bird.getY()) < (p.getY() + p.getHeight()) ) {
				nrOfHits++;
				return true;
			}
		}
		
		return false;
	}
	
	public int getScore() { return score; }
	public int getNrOfHits() { return nrOfHits; }
	public int getHighscore() { return highscore; }
	public GameState getState() { return state; }
	public ArrayList<Integer> getListOfScore() {
		return listOfScore;
	}
		
	public void setState(GameState state) {
		this.state = state;
	}
	
	public void resetNrOfHits() {
		nrOfHits = 0;
	}
	
	public void resetScore() {
		score = 0;
	}
	
	public void setScore(int score) {
		this.score = score;
		listOfScore.add(score);
		isHighscore(score);
	}
	
	private void isHighscore(int score) {
		int s = 0;
		for(Integer i : listOfScore) {
			if ( i < score ) {
				s = score;
			} else {
				s = i;
			}
		}
		if ( highscore > s && score >= s  ) {
			System.out.println("New highscore! : " + highscore);
		}
	}
	
	@Override
	public String toString() {
		String string = "Number of Hits: " + nrOfHits + "\n" +
			"Latest Score: " + score + "\n" + "Highscore: " + highscore;
 		return string;
	}
	
}
