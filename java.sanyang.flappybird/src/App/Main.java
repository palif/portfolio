package App;

import Model.Model;
import View.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Model model = new Model();
		View view = new View(model);
		
		Scene scene = new Scene(view);
		scene.onKeyPressedProperty().bind(view.getBird().onKeyPressedProperty());
		scene.onMouseClickedProperty().bind(view.onMouseClickedProperty());
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Flappy Bird Application");
		stage.show();

	}
	
	public static void main(String[] arr) {
		launch(arr);
	}

}
